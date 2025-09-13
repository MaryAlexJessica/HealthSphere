// src/AppWrapper.jsx
import React from "react";
import { BrowserRouter as Router } from "react-router-dom";
import App from "./App";
import { UserProvider } from "./context/UserContext";

export default function AppWrapper() {
    return (
        <Router>
            <UserProvider>
                <App />
            </UserProvider>
        </Router>
    );
}
