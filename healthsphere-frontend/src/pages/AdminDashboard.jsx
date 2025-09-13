import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchPendingDoctors } from "../api/admin";
import { Users, Calendar, Stethoscope } from "lucide-react";
import Layout from "../pages/Layout";
import "../styles/Dashboard.css";

const AdminDashboard = () => {
    const navigate = useNavigate();
    const [pendingDoctors, setPendingDoctors] = useState([]);

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        const doctors = await fetchPendingDoctors();
        setPendingDoctors(doctors);
    };

    const summaryData = [
        {
            title: "Pending Doctors",
            count: pendingDoctors.length, // ✅ directly from existing API
            icon: <Stethoscope />,
            path: "/admin/manage/doctors",
        },
        {
            title: "Active Patients",
            count: 58, // later replace with API
            icon: <Users />,
            path: "/admin/patients",
        },
        {
            title: "Appointments Today",
            count: 24, // later replace with API
            icon: <Calendar />,
            path: "/admin/appointments",
        },
    ];

    return (
        <Layout>
            <div className="kpi-container">
                {summaryData.map((item, index) => (
                    <div
                        key={index}
                        className="kpi-card"
                        onClick={() => navigate(item.path)}
                    >
                        <div className="kpi-icon">{item.icon}</div>
                        <div className="details">
                            <h3>{item.title}</h3>
                            <p>{item.count}</p>
                        </div>
                    </div>
                ))}
            </div>
        </Layout>
    );
};

export default AdminDashboard;
