// src/pages/Unauthorized.jsx
import React from "react";
import { useNavigate } from "react-router-dom";

const Unauthorized = () => {
    const navigate = useNavigate();

    return (
        <div
            style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                height: "80vh",
                textAlign: "center",
                gap: "20px",
            }}
        >
            <h1 style={{ fontSize: "3rem", color: "#dc2626" }}>⚠️ Unauthorized</h1>
            <p style={{ fontSize: "1.2rem" }}>
                You do not have permission to access this page.
            </p>
            <button
                onClick={() => navigate("/login")}
                style={{
                    padding: "10px 20px",
                    backgroundColor: "#244034",
                    color: "#fff",
                    border: "none",
                    borderRadius: "6px",
                    cursor: "pointer",
                    fontSize: "1rem",
                }}
            >
                Go to Login
            </button>
        </div>
    );
};

export default Unauthorized;
