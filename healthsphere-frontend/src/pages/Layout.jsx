import React from "react";
import Sidebar from "./common/Sidebar";
import { LogOut } from "lucide-react";
import { useUser } from "../context/UserContext";
import { Outlet } from "react-router-dom";
import "../styles/Layout.css";

const Layout = ({ children }) => {
    const { user, handleLogout } = useUser();
    console.log("user:", user);

    return (
        <div className="layout-container">
            <Sidebar roleId={user?.role || "GUEST"} />

            <div className="main-content">
                {/* Header stays visible */}
                <header className="header">
                    <h2>{user ? `${user.username} Dashboard` : "Dashboard"}</h2>
                    <div className="header-profile">
                        <span>{user?.username || "Guest"}</span>
                        {user && (
                            <button className="logout-btn" onClick={handleLogout}>
                                <LogOut className="icon" /> Logout
                            </button>
                        )}
                    </div>
                </header>

                {/* Main content */}
                <main className="content">
                    {user ? children : <div>Loading...</div>}
                </main>

                {/* Footer stays visible */}
                <footer className="footer">
                    <p>© {new Date().getFullYear()} HealthSphere. All rights reserved.</p>
                </footer>
            </div>
        </div>
    );
};

export default Layout;
