// src/config/sidebarLinks.js
import {
    LayoutDashboard,
    Users,
    Calendar,
    FileText,
    Stethoscope,
    Activity,
    Settings,
} from "lucide-react";

export const sidebarLinks = {
    ROLE_ADMIN: [
        { title: "Dashboard", icon: LayoutDashboard, path: "/admin/dashboard" },
        { title: "Doctors", icon: Stethoscope, path: "/admin/doctors" },
        { title: "Patients", icon: Users, path: "/admin/patients" },
        { title: "Reports", icon: FileText, path: "/admin/reports" },
        { title: "Analytics", icon: Activity, path: "/admin/analytics" },
        { title: "Settings", icon: Settings, path: "/admin/settings" },
    ],
    ROLE_DOCTOR: [
        { title: "Dashboard", icon: LayoutDashboard, path: "/doctor/dashboard" },
        { title: "Appointments", icon: Calendar, path: "/doctor/appointments" },
    ],
    ROLE_PATIENT: [
        { title: "Dashboard", icon: LayoutDashboard, path: "/patient/dashboard" },
        { title: "Appointments", icon: Calendar, path: "/patient/appointments" },
    ],
};
