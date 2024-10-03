import React, { useEffect, useState } from "react";
import "./Home.css";
import { Link } from "react-router-dom";
import { exitUser, logoutUser } from "../../Service/Service.jsx";
import { useLocation, useNavigate } from "react-router-dom";
import Table from "./Table.jsx";
import { useLoadUsers } from "../../Hook/useLoadUser.js";
import { useMessageNotification } from "../../Notification/useMessageNotification.jsx";
import { useErrorNotification } from "../../Notification/useErrorNotification.jsx";
const UserList = () => {
  const { data, getUser, errorMessage: errData } = useLoadUsers();

  const [dataGet, setDataGet] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [username, setUsername] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setLastTime] = useState("");
  const location = useLocation();
  const navigate = useNavigate();
  const [message, setMessage] = useState();
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(8);
  const [totalPages, setTotalPages] = useState(0);
  const [totalRecords, setTotalRecords] = useState(0);

  useEffect(() => {
    if (location.state?.message) {
      setMessage(location.state?.message);
    }
  }, [location.state?.message]);
  useEffect(() => {
    if (data) {
      setDataGet(data.object);

      setTotalRecords(data.record);
      const calculatedTotalPages = Math.ceil(data.record / pageSize);
      setTotalPages(calculatedTotalPages);
    }
  }, [data]);
  const formatDate = (dateString) => {
    const parts = dateString.split("-");
    if (parts.length === 3) {
      const day = parts[2];
      const month = parts[1];
      const year = parts[0];
      return `${day}-${month}-${year}`;
    }
    return dateString;
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      const nextPage = currentPage + 1;
      setCurrentPage(nextPage);
      getUser(
        username,
        formatDate(startTime),
        formatDate(endTime),
        nextPage,
        pageSize
      );
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 1) {
      const prevPage = currentPage - 1;
      setCurrentPage(prevPage);
      getUser(
        username,
        formatDate(startTime),
        formatDate(endTime),
        prevPage,
        pageSize
      );
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();

    const formattedDate_start = formatDate(startTime);
    const formattedDate_last = endTime ? formatDate(endTime) : null;

    const pageLoad = 1;

    try {
      const response = await getUser(
        username,
        formattedDate_start,
        formattedDate_last,
        pageLoad,
        pageSize
      );
    } catch (error) {
      console.error("Fail of users:", error);
    }
  };

  const deleteUser = async (username) => {
    if (window.confirm(`Are you sure you want to delete ${username}?`)) {
      if (username === localStorage.getItem("name")) {
        setErrorMessage("Cannot be deleted, account in use");
      } else {
        try {
          const response = await exitUser(username);
          console.log(response);

          if (response.error_cd === "000") {
            getUser();
            setMessage(response.error_msg);
          } else {
            setErrorMessage(response.error_msg);
          }
        } catch (error) {
          if (error.response) {
            if (error.response.status === 401) {
            } else {
              setErrorMessage("Failed to delete user.");
            }
          }
        }
      }
    }
  };

  const handleLogoutUser = async () => {
    try {
      await logoutUser(localStorage.getItem("token")).then((response) => {
        console.log(response);
        if (response.error_cd === "000") {
          localStorage.removeItem("token");
          localStorage.removeItem("name");
          navigate("/", { state: { message: response.error_msg } });
        }
      });
    } catch (error) {
      if (error.response) {
        if (error.response.status === "001") {
        } else {
          setErrorMessage("Failed to delete user.");
        }
      }
    }
  };
  useMessageNotification(message, setMessage);
  useErrorNotification(errorMessage, setErrorMessage);
  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="">
                  Home
                </a>
              </li>
              <li className="nav-item">
                <span className="nav-link">{localStorage.getItem("name")}</span>
              </li>
              <li className="nav-item">
                <button
                  className="btn btn-danger"
                  style={{ marginLeft: "10px" }}
                  onClick={handleLogoutUser}
                >
                  Logout
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <div>
        <h1>User List</h1>
        <div></div>
        <Link to="/add" className="btn btn-primary mb-2">
          Add Employee
        </Link>
        <form onSubmit={handleSearch}>
          <div className="form-group">
            <label htmlFor="username">Username:</label>
            <input
              type="text"
              id="username"
              className="form-control"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Nhập tên người dùng"
            />
          </div>

          <div className="form-group">
            <label htmlFor="startTime">Start Time:</label>
            <input
              type="date"
              id="startTime"
              className="form-control"
              value={startTime}
              onChange={(e) => setStartTime(e.target.value)}
            />
          </div>
          <div className="form-group">
            <label htmlFor="endTime">Last Time:</label>
            <input
              type="date"
              id="endTime"
              className="form-control"
              value={endTime}
              onChange={(e) => setLastTime(e.target.value)}
            />
          </div>
          <div
            style={{
              width: "130px",
              display: "flex",
              justifyContent: "space-between",
              marginTop: "10px",
            }}
          >
            <button type="submit" className="btn btn-primary btn-sm">
              Search
            </button>
            <Link
              className="btn btn-info btn-sm"
              onClick={() => window.location.reload()}
            >
              Restart
            </Link>
          </div>
        </form>
        <Table dataGet={dataGet} deleteUser={deleteUser} />
        <div style={{ paddingBottom: "20px", textAlign: "center" }}>
          <button
            type="button"
            className="btn btn-primary"
            onClick={handlePreviousPage}
            disabled={currentPage === 1}
            style={{ marginRight: "10px" }}
          >
            Previous
          </button>
          <button type="button" className="btn btn-success">
            Page {currentPage} of {totalPages} -- Record {totalRecords}
          </button>
          <button
            type="button"
            className="btn btn-primary"
            onClick={handleNextPage}
            disabled={currentPage === totalPages}
            style={{ marginLeft: "10px" }}
          >
            Next
          </button>
        </div>
      </div>
    </>
  );
};

export default UserList;
