import axios from "axios";
const axiosInstance =axios.create({
    baseURL:'http://localhost:8080/api/user'
});
axiosInstance.interceptors.request.use(config=>{
    const token=localStorage.getItem('token');
    const tokenType=localStorage.getItem('tokenType')
    if(token){
        config.headers['Authorization']=`${tokenType} ${token}`;
    }
    return config;
}, error =>{
    return Promise.reject(error);
})

export default axiosInstance;

