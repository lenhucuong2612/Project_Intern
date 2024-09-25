
import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ element }) => {
    const token = localStorage.getItem('token'); // Kiểm tra token

    return token ? element : <Navigate to="/" />; // Chuyển hướng nếu không có token
};

export default ProtectedRoute;
