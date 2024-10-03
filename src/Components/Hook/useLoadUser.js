import { useEffect, useState } from "react";
import { findUser } from "../Service/Service";

export const useLoadUsers = () => {
  const [data, setData] = useState({});
  const [errorMessage, setErrorMessage] = useState("");
  useEffect(() => {
    getUser();
  }, []);
  const getUser = async (
    username = "",
    startTime = "",
    endTime = "",
    page,
    size=8
  ) => {
    try {
      const data = await findUser(username, startTime, endTime, page, size);
      console.log("Received user2 data:", data);

      setData(data);
    } catch (error) {
      console.error("Error fetching user list:", error);
      setErrorMessage("Failed to fetch user list.");
    }
  };
  return {
    data,
    getUser,
    errorMessage,
  };
};
