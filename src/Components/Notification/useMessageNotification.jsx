
import { useEffect } from "react"
import { toast } from "react-toastify"
import { useNavigate } from "react-router-dom"
export const useMessageNotification=(message, setMessage)=>{

    const navigate=useNavigate();
    useEffect(() => {
        if (message) {
            const timer = setTimeout(() => {
                setMessage(null);
                navigate("/home", {
                    replace: true,
                    state: {}
                });
            }, 2000); 
            return () => clearTimeout(timer);
        }
    }, [message, navigate, setMessage]);
    useEffect(()=>{
        console.log(32222,message)
        if(message){
            toast.success(message, {
                position: "top-right"
            })
        }
    },[message])


}