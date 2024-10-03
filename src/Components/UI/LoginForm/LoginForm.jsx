// LoginForm.jsx
import React from 'react';
import './LoginForm.css';
import { FaUser, FaLock } from 'react-icons/fa';
import useLoginForm from './LoginFormHandle';  
import { useErrorNotification } from '../../Notification/useErrorNotification';


const LoginForm = () => {
    const {
        username,
        password,
        setUsername,
        setPassword,
        handleSubmit
    } = useLoginForm();
    return (
        <div className='wrapper'>
            
            <form onSubmit={handleSubmit}>
                <h1>Login</h1>
                <div>
                    
                </div>
                <div className="input-box">
                    <input
                        type="text"
                        placeholder='Username'
                        required
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <FaUser className='icon' />
                </div>
                <div className="input-box">
                    <input
                        type="password"
                        placeholder='Password'
                        required
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <FaLock className='icon' />
                </div>
                <button type='submit'>Login</button>
            </form>
        </div>
    );
};

export default LoginForm;
