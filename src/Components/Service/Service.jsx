import axiosInstance from "../Axios/axios";

export async function fetchUsers(page,size){
    const response=await axiosInstance.get(`/listUser?page=${page} & size=${size}`)
    return response.data
}
export async function exitUser(username){
    const response=await axiosInstance.delete('/delete',{
        params: { username: username }
    })
    return response.data
}
export async function addUser(user){
    const response=await axiosInstance.post('/create',user)
    return response.data
}    

export async function findUserByName(username) {
    const response=await axiosInstance.get(`/findByName?username=${username}`)
    return response.data
    
}
export async function findUser(username,start_time,end_time, page,size){
    const response=await axiosInstance.post(`/findUser`,{
           username,
           start_time,
           end_time
    },{
        params:{
            page: page,
            size: size
        }
    })
    console.log('Response data from findUser:', response.data); 
    return response.data
}
export async function updateUser(user) {
    const response=await axiosInstance.put('/update',user)
    return response.data
}
export async function updateUserName(user) {
    const response=await axiosInstance.put('/update/username',user)
    return response.data
}
export async function logoutUser(token){
    const response=await axiosInstance.get('/logout',token)
    return response.data
}