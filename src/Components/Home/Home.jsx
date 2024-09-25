import React, { useEffect, useState } from 'react';
import  axiosInstance from '../Axios/axios.jsx'
import './Home.css';
import 'bootstrap/dist/css/bootstrap.min.css';

const UserList = () => {
    const [users, setUsers] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');

    const fetchUsers = async () => {
        try {
            const response = await axiosInstance.get('/listUser');
            console.log(response.data);
            setUsers(response.data);
        } catch (error) {
            console.error('Error fetching user list:', error);
            setErrorMessage('Failed to fetch user list.');
        }
    };
    const token=localStorage.getItem('token');
    useEffect(() => {
        fetchUsers(); 
    }, []);

    return (
        <div>
            <h1>User List</h1>
            {/* {errorMessage && <div>{errorMessage}</div>}
            <ul>
                {users.map(user => (
                    <li key={user.id}>{user.username}</li>
                ))}
            </ul> */}
            <table className="table table-striped custom-table">
                <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Username</th>
                        <th scope="col">Create Time</th>
                    </tr>
                </thead>
                <tbody>
                    {
                         users.map(
                            user =>
                            <tr key = {user.id}> 
                                <td> {user.id} </td>
                                <td> {user.username} </td>
                                <td>{user.create_time}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    );
};

export default UserList;
