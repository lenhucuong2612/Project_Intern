// LoginFormLogic.jsx
import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from "react-router-dom";
import  axiosInstance from '../../Axios/axios.jsx'

import { useErrorNotification } from '../../Notification/useErrorNotification.jsx';
import { useMessageNotification } from '../../Notification/useMessageNotification.jsx';
 
const useLoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate=useNavigate();
    const location = useLocation();
    const [message, setMessage] = useState("");


    useEffect(() => {
        if (location.state?.message) {
          setMessage(location.state?.message);
        }
      }, [location.state?.message]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axiosInstance.post('/login', {
                username,
                password,
            });
            const data = await response.data;
            
            if (data.error_cd==='000') {
                localStorage.setItem('token', data.token); 
                localStorage.setItem('tokenType', data.tokenType)
                localStorage.setItem('name',username)
                navigate('/home', { state: { message: data.error_msg} });
            } else {
              
                setErrorMessage(data.error_msg || 'Login failed');
            }
        } catch (error) {
            console.error('Error:', error);
            setErrorMessage('Something went wrong. Please try again');
        }
        console.log(localStorage.getItem('token'))
    };
    
    useErrorNotification(errorMessage,setErrorMessage)
    useMessageNotification(message,setMessage)
    return {
        username,
        password,
        setUsername,
        setPassword,
        handleSubmit,
    };
};

export default useLoginForm;
