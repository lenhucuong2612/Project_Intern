import React from "react";
import { Link } from "react-router-dom";
import "./Home.css";
const Table = ({deleteUser,dataGet}) => {
  return (
    <table className="table table-striped custom-table">
      <thead>
        <tr>
          <th>Stt</th>
          <th scope="col">Id</th>
          <th scope="col">Username</th>
          <th scope="col">Create Time</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {dataGet &&
          dataGet.map((user) => (
            <tr key={user.id}>
              <td>{user.index}</td>
              <td>{user.id}</td>
              <td>{user.username}</td>
              <td>{user.create_time}</td>
              <td>
                <Link
                  className="btn btn-info"
                  to={`/edit?username=${user.username}`}
                >
                  Update
                </Link>
                

                <button
                  className="btn btn-danger"
                  style={{ marginLeft: "10px" }}
                  onClick={() => deleteUser(user.username)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
      </tbody>
    </table>
  );
};

export default Table;
