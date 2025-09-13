import React, { useEffect, useState } from "react";
import Layout from "../pages/Layout";
import Table from "../pages/common/Table";
import { fetchPendingDoctors, approveDoctor, rejectDoctor } from "../api/admin";
import "../styles/ManageDoctorRequests.css";

const ManageDoctorRequests = () => {
    const [doctors, setDoctors] = useState([]);

    useEffect(() => {
        loadDoctors();
    }, []);

    const loadDoctors = async () => {
        try {
            const data = await fetchPendingDoctors();
            setDoctors(data);
        } catch (err) {
            console.error("Failed to fetch pending doctors:", err);
        }
    };

    const handleApprove = async (id) => {
        try {
            await approveDoctor(id);
            loadDoctors(); // refresh table
        } catch (err) {
            console.error("Approve failed:", err);
        }
    };

    const handleReject = async (id) => {
        try {
            await rejectDoctor(id);
            loadDoctors(); // refresh table
        } catch (err) {
            console.error("Reject failed:", err);
        }
    };

    // Map data for the Table component
    const tableData = doctors.map((doc) => ({
        ID: doc.id,
        Username: doc.username,
        Status: doc.status,
        Actions: (
            <div>
                <button
                    className="table-btn approve-btn"
                    onClick={() => handleApprove(doc.id)}
                >
                    Approve
                </button>
                <button
                    className="table-btn reject-btn"
                    onClick={() => handleReject(doc.id)}
                >
                    Reject
                </button>
            </div>
        ),
    }));

    return (
        <Layout>
            <div className="manage-doctors">
                <h2>Manage Pending Doctors</h2>
                <Table data={tableData} />
            </div>
        </Layout>
    );
};

export default ManageDoctorRequests;
