import axiosInstance from "../Axios/axios";

export async function fetchUsers(){
    const response=await axiosInstance.get('/listUser')
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
export async function findUser(username,create_time){
    const response=await axiosInstance.post(`/findUser`,{
            username:username,
            create_time:create_time
    })
    return response.data
}
export async function updateUser(user) {
    const response=await axiosInstance.put('/update',user)
    return response.data
}