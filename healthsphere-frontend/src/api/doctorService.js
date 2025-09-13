// src/services/doctorService.js
import axios from "axios";

const API_BASE = "http://localhost:8080/admin"; // adjust if needed

export const getPendingDoctors = async () => {
  const res = await axios.get(`${API_BASE}/doctors/pending`);
  return res.data;
};

export const approveDoctor = async (id) => {
  const res = await axios.post(`${API_BASE}/approve/${id}`);
  return res.data;
};

export const rejectDoctor = async (id) => {
  const res = await axios.post(`${API_BASE}/reject/${id}`);
  return res.data;
};
