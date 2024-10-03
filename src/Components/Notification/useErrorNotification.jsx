import { useEffect } from "react"
import { toast } from "react-toastify";

export const useErrorNotification = (errorMessage, setErrorMessage) => {
    useEffect(() => {
      if (errorMessage) {
        const timer = setTimeout(() => {
          setErrorMessage("");
        }, 200);
        return () => clearTimeout(timer);
      }
    }, [errorMessage, setErrorMessage]);
  
    useEffect(() => {
      if (errorMessage) {
        toast.error(errorMessage, {
          position: "top-right",
          color: "red",
        });
      }
    }, [errorMessage]);
  };
