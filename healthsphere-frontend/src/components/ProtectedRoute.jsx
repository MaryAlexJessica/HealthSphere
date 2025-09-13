// src/components/ProtectedRoute.jsx
import React from "react";
import { Navigate } from "react-router-dom";
import { useUser } from "../context/UserContext";

const ProtectedRoute = ({ children, allowedRoles }) => {
    const { user } = useUser();

    // ✅ No user → force login
    if (!user) {
        return <Navigate to="/login" replace />;
    }

    // ✅ Role check
    if (allowedRoles && !allowedRoles.includes(user.role)) {
        return <Navigate to="/unauthorized" replace />;
    }

    // ✅ User exists and role is valid → render
    return children;
};

export default ProtectedRoute;
