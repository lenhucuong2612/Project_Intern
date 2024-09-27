// LoginForm.jsx
import React from 'react';
import './LoginForm.css';
import { FaUser, FaLock } from 'react-icons/fa';
import useLoginForm from './LoginFormHandle'; 

const LoginForm = () => {
    const {
        username,
        password,
        errorMessage,
        setUsername,
        setPassword,
        handleSubmit,
    } = useLoginForm();

    return (
        <div className='wrapper'>
            <form onSubmit={handleSubmit}>
                <h1>Login</h1>
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
                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            </form>
        </div>
    );
};

export default LoginForm;
