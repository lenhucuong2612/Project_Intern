
import React from 'react';
import { Navigate } from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';
// const ProtectedRoute = ({ element }) => {
//     const token = localStorage.getItem('token');

//     return token ? element : <Navigate to="/" />; 
// };

// export default ProtectedRoute;

const ProtectedRoute=({element})=>{
    const token=localStorage.getItem('token');
    if(!token){
        return <Navigate to="/" />
    }

    try{
        const decodedToken=jwtDecode(token);
        const currentTime=Date.now()/1000;
        if(decodedToken.exp<currentTime){
            localStorage.removeItem('token')
            return <Navigate to="/" />
        }
    }catch(error){
        localStorage.removeItem('token');
        return <Navigate to="/" />
    }
    return element;
}

export default ProtectedRoute;