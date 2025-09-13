import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import PatientDashboard from "./pages/PatientDashboard";
import DoctorDashboard from "./pages/DoctorDashboard";
import ProtectedRoute from "./components/ProtectedRoute";
import AdminDashboard from "./pages/AdminDashboard";
import ManageDoctorRequests from "./pages/ManageDoctorRequests";
import Layout from "./pages/Layout";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />

      <Route
        path="/patient-dashboard"
        element={
          <ProtectedRoute allowedRoles={["ROLE_PATIENT"]}>
            <PatientDashboard />
          </ProtectedRoute>
        }
      />

      <Route
        path="/doctor-dashboard"
        element={
          <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
            <DoctorDashboard />
          </ProtectedRoute>
        }
      />

      {/* Admin (no nesting) */}
      <Route
        path="/admin/dashboard"
        element={
          <ProtectedRoute allowedRoles={["ROLE_ADMIN"]}>
            <AdminDashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin/manage/doctors"
        element={
          <ProtectedRoute allowedRoles={["ROLE_ADMIN"]}>
            <ManageDoctorRequests />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}

export default App;
