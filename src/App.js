import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginForm from './Components/LoginForm/LoginForm.jsx';
import Home from './Components/Home/Home.jsx'; 
import ProtectedRoute from './Components/Route/ProtectedRoute.jsx';
function App() {
  return (
    <Router>
    <Routes>
      <Route path="/" element={<LoginForm />} /> 
      <Route 
          path="/home" 
          element={<ProtectedRoute element={<Home />} />} 
      />
    </Routes>
  </Router>
  );
}

export default App;
