import React from "react";
import { sidebarLinks } from "../../config/Sidebarlinks";
import { useUser } from "../../context/UserContext";
import { NavLink } from "react-router-dom";
import "../../styles/Layout.css";

const Sidebar = ({ roleId }) => {
    const { user } = useUser();

    if (!roleId) return <div className="sidebar-loading">Loading...</div>;

    const links = sidebarLinks[roleId] || [];

    return (
        <div className="sidebar">
            <div className="sidebar-header">
                <img
                    src="/Healthsphere_logo.png"
                    alt="logo"
                    className="sidebar-logo"
                />
                <span>HealthSphere</span>
            </div>

            <nav className="sidebar-menu">
                {links.length === 0 && <div className="menu-item">No menu items available</div>}

                {links.map((link) => (
                    <NavLink
                        key={link.path}
                        to={link.path}
                        className={({ isActive }) =>
                            `menu-item ${isActive ? "active" : ""}`
                        }
                    >
                        {React.isValidElement(link.icon) && (
                            <span className="icon">{link.icon}</span>
                        )}
                        {link.title || "Untitled"}
                    </NavLink>
                ))}
            </nav>
        </div>
    );
};

export default Sidebar;
