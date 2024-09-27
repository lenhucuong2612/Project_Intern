import React, { useEffect, useState } from 'react';
import './Home.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-router-dom';
import { fetchUsers, exitUser,findUser } from '../../Service/Service.jsx';
import { useLocation,useNavigate  } from 'react-router-dom';
const UserList = () => {
    const [users, setUsers] = useState([]);  
    const [filteredUsers, setFilteredUsers] = useState([]); 
    const [errorMessage, setErrorMessage] = useState('');
    const [username, setUsername] = useState(''); 
    const [createTime, setCreateTime] = useState('');
    const location = useLocation();
    const navigate = useNavigate();
    const [message, setMessage] = useState(location.state?.message);
    const loadUsers = async (username = '', createTime = '') => {
        try {
            let data;
            if (username || createTime) {
                data = await findUser(username, createTime);
            } else {
                data = await fetchUsers();
            }
            console.log('Received user data:', data);
            setUsers(data);
            setFilteredUsers(data); 
        } catch (error) {
            console.error('Error fetching user list:', error);
            setErrorMessage('Failed to fetch user list.');
        }
    };
    const formatDate = (dateString) => {
        const parts = dateString.split('-'); 
        if (parts.length === 3) {
            const day = parts[2]; 
            const month = parts[1]; 
            const year = parts[0]; 
            return `${day}-${month}-${year}`; 
        }
        return dateString; 
    };
    
    const handleSearch = (e) => {
        e.preventDefault();
        const formattedDate = formatDate(createTime); 
        loadUsers(username, formattedDate).then(() => {
            console.log('Filtered Users:', users);
            console.log('Filtered date:', formattedDate);
        });
    };
    

    const deleteUser = async (username) => {
        if (window.confirm(`Are you sure want to delete ${username}?`)) {
            try {
                await exitUser(username).then((response)=>{
                    console.log(response)
                    setErrorMessage(response.error_msg);
                    if(response.error_cd==='000'){
                        setUsers(users.filter(user => user.username !== username));
                        setFilteredUsers(filteredUsers.filter(user => user.username !== username));
                    }
                })
            } catch (error) {
                if (error.response) {
                    if (error.response.status === 401) {
                    } else {
                        setErrorMessage('Failed to delete user.');
                    }
                }
            }
        }
    };

   
    useEffect(() => {
        loadUsers();
    }, []);
    useEffect(() => {
        if (message) {
            const timer = setTimeout(() => {
                setMessage(null);
                navigate('/home', { replace: true, state: {} });
            }, 2000); 
            return () => clearTimeout(timer);
        }
    }, [message, navigate]);
    useEffect(() => {
        if (errorMessage) {
            const timer = setTimeout(() => {
                setErrorMessage(''); 
            }, 5000); 

            return () => clearTimeout(timer);
        }
    }, [errorMessage]);
    return (
        <div>
            <h1>User List</h1>
            {message && <div className="alert alert-success">{message}</div>}
            <Link to="/add" className="btn btn-primary mb-2">Add Employee</Link>
            <form onSubmit={handleSearch}>
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        className="form-control"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder="Nhập tên người dùng"
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="createTime">Create Time:</label>
                    <input
                        type="date"
                        id="createTime"
                        className="form-control"
                        value={createTime}
                        onChange={(e) => setCreateTime(e.target.value)}
                    />
                </div>
                <div style={{width:'130px', display:'flex',justifyContent:'space-between', marginTop:'10px'}}>
                <button type="submit" className="btn btn-primary btn-sm">Search</button>
                <Link className="btn btn-info btn-sm" onClick={() => window.location.reload()}>Restart</Link>
                </div>
            </form>
            <table className="table table-striped custom-table">
                <thead>
                    <tr >
                        <th>Stt</th>
                        <th scope="col">Id</th>
                        <th scope="col">Username</th>
                        <th scope="col">Create Time</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredUsers.map((user,index) => (
                        <tr key={user.id}>
                            <td>{index+1}</td>
                            <td>{user.id}</td>
                            <td>{user.username}</td>
                            <td>{user.create_time}</td>
                            <td>
                                <Link className="btn btn-info" to={`/edit?username=${user.username}`}>Update</Link>
                                <button className="btn btn-danger" style={{ marginLeft: "10px" }} onClick={() => deleteUser(user.username)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        </div>
    );
};

export default UserList;
