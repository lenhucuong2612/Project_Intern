// LoginFormLogic.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import  axiosInstance from '../Axios/axios.jsx'
const useLoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate=useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axiosInstance.post('/login', {
                username,
                password,
            });
            const data = await response.data;
            if (data.error_cd==='000') {
                console.log('Login successful', data);
                localStorage.setItem('token', data.token); // Giả sử bạn nhận được token trong data
                navigate('/home')
            } else {
                setErrorMessage(data.message || 'Login failed');
            }
        } catch (error) {
            console.error('Error:', error);
            setErrorMessage('Something went wrong. Please try again');
        }
        console.log(localStorage.getItem('token'))
    };

    return {
        username,
        password,
        errorMessage,
        setUsername,
        setPassword,
        handleSubmit,
    };
};

export default useLoginForm;
