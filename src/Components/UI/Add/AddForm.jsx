import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useState } from 'react';
import { addUser } from '../../Service/Service.jsx';
import { useNavigate } from 'react-router-dom';
const AddUser = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [formData, setFormData]=useState('')
    const navigate=useNavigate();

    const handleChange=(e)=>{
        const {name, value}=e.target;
        setFormData({...formData,[name]:value})
    }
    const validate=()=>{
        const newErrors={};
        if(!formData.username){
            newErrors.username='Username is required';
        }
        if(!formData.password){
            newErrors.password='Password is required';
        }else if(formData.password.length<5){
            newErrors.password='Password must be at least 6 characters long';
        }
        return newErrors;
    }
    const handleSubmit=(e)=>{
        e.preventDefault();
        
    }
    const insertUser = (e) => {
        e.preventDefault();
        const user = { username, password, role };

        addUser(user)
            .then((response) => {
                console.log(response);
                if(response.error_cd==='000'){
                    navigate('/home', { state: { message: response.error_msg} });
                }else{
                    setErrorMessage(response.error_msg)
                }
            })
            .catch((err) => {
                console.log("Error fetching insert user", err);
                setErrorMessage('Failed to insert user');
            });
    };

    return (
        <div>
            <br /><br />
            <div className="container" style={{ width: "700px" }}>
                <div className="row">
                    <div className="card col-md-6 offset-md-3 offset-md-3">
                        <div className="card-body">
                            <h1>User Add</h1>
                            <form onSubmit={insertUser}>  {/* Gọi hàm insertUser khi gửi form */}
                                {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
                                <div className="form-group mb-2">
                                    <label className="form-label"> UserName :</label>
                                    <input
                                        type="text"
                                        placeholder="Enter username"
                                        name="username"
                                        value={formData.username}
                                        className="form-control"
                                        onChange={(e) => setUsername(e.target.value)}
                                    />
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label"> Password :</label>
                                    <input
                                        type="password"
                                        placeholder="Enter password"
                                        name="password"
                                        value={password}
                                        className="form-control"
                                        onChange={(e) => setPassword(e.target.value)}
                                    />
                                </div>
                                <div className="form-group mb-2">
                                    <label className="form-label"> Role:</label>
                                    <select
                                        className="form-select"
                                        aria-label=".form-select-lg example"
                                        name="role"
                                        value={role} // Sử dụng thuộc tính value cho thẻ <select>
                                        onChange={(e) => setRole(e.target.value)}
                                    >
                                        <option value="" disabled>Open this select menu</option> {/* Không cần sử dụng selected ở đây */}
                                        <option value="ROOT">ROOT</option>
                                        <option value="ADMIN">ADMIN</option>
                                        <option value="USER">USER</option>
                                        <option value="GUEST">GUEST</option>
                                    </select>
                                </div>

                                <button className="btn btn-success">Submit</button>
                                <Link to="/home" className="btn btn-danger" style={{ margin: "0 0 0 20px" }}> Cancel </Link>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddUser;
