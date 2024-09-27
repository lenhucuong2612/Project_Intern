import React, {useState, useEffect} from 'react'
import {Link, useHistory, useParams,useLocation  } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { findUserByName,updateUser } from '../../Service/Service.jsx';
import { useNavigate } from 'react-router-dom';

const UpdateForm=()=>{
    const[id, setId]=useState('');
    const[username,setUserName]=useState('');
    const[password,setPassword]=useState('');
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const paramUserName = params.get('username');
    const navigate=useNavigate();
    const [errorMessage, setErrorMessage] = useState('');

    const update=(e)=>{
        e.preventDefault();
        const user={username,password}
        const userPost={id,username,password};
        console.log('User post data:', userPost);
        updateUser(userPost)
        .then((response)=>{
            console.log(response)
            if(response.error_cd==='000'){
                navigate('/home', { state: { message: response.error_msg} });
            }else if(response.error_cd==='003'){
                setErrorMessage(response.error_msg)
            }
        })
        .catch((err) => {
            console.error("Error updating user", err);
            setErrorMessage('Failed to update user');
            if (err.response) {
                console.error('Response data:', err.response.data);
                console.error('Response status:', err.response.status);
                console.error('Response headers:', err.response.headers);
            }
        });
    }
    useEffect(() => {
        findUserByName(paramUserName)
            .then((response) => {
                console.log(response);
                setId(response.id);
                setUserName(response.username);
            })
            .catch((error) => {
                console.error('Find user failed:', error);
            });
    }, [paramUserName]);
    
    return (
        <div>
           <br /><br />
           <div className = "container " style = {{width:"700px"}}>
                <div className = "row">
                    <div className = "card col-md-6 offset-md-3 offset-md-3"> 
                        <div className = "card-body">
                        <h1>User Edit</h1>
                            <form onSubmit={update}>
                            <div className = "form-group mb-2">
                                    <label className = "form-label"> Id :</label>
                                    <input
                                        type = "number"
                                        name = "id"
                                        value={id}
                                        onChange = {(e) => setId(e.target.value)}
                                        className = "form-control"
                                        readOnly
                                    >
                                    </input>
                                </div>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> UserName :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter username"
                                        name = "username"
                                        className = "form-control"
                                        value={username}
                                        onChange = {(e) => setUserName(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Password :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter password"
                                        name = "password"
                                        className = "form-control"
                                        onChange = {(e) => setPassword(e.target.value)}
                                        autoComplete="off"  
                                    >
                                    </input>
                                </div>
                                <button className = "btn btn-success"  type="submit">Submit </button>
                                <Link to="/home" className="btn btn-danger" style = {{margin:"0 0 0 20px"}}> Cancel </Link>
                            </form>
                            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
                        </div>
                    </div>
                </div>

           </div>

        </div>
    ) 
}
export default UpdateForm