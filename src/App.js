import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginForm from './Components/UI/LoginForm/LoginForm.jsx';
import Home from './Components/UI/Home/Home.jsx'; 
import ProtectedRoute from './Components/Route/ProtectedRoute.jsx';
import AddForm from './Components/UI/Add/AddForm.jsx';
import UpdateForm from './Components/UI/Update/UpdateForm.jsx';
import UpdateFormUsername from './Components/UI/Update/UpdateFormUsername.jsx';
function App() {
  return (
    <Router>
    <Routes>
      <Route path="/" element={<LoginForm />} /> 
      <Route 
        path="/home" 
        element={<ProtectedRoute element={<Home />} />} 
    />
      <Route 
        path="/edit" 
        element={<ProtectedRoute element={<UpdateForm />} />} 
      />
       <Route 
        path="/edit/username" 
        element={<ProtectedRoute element={<UpdateFormUsername />} />} 
      />
       <Route path = "/add" element={<ProtectedRoute element={<AddForm />} />}  ></Route>
    </Routes>
  </Router>
  );
}

export default App;
