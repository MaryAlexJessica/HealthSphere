// service/adminService.js
import axios from "axios";

const API_URL = "http://localhost:8080/admin";

export const fetchPendingDoctors = async () => {
    const token = localStorage.getItem("token");
    console.log("token:", token);
    const res = await axios.get(`${API_URL}/doctors/pending`, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return res.data;
};

export const approveDoctor = async (id) => {
    const token = localStorage.getItem("token");
    const res = await axios.post(`${API_URL}/approve/${id}`, {}, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return res.data;
};

export const rejectDoctor = async (id) => {
    const token = localStorage.getItem("token");
    const res = await axios.post(`${API_URL}/reject/${id}`, {}, {
        headers: { Authorization: `Bearer ${token}` },
    });
    return res.data;
};
