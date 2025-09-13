// summaryConfig.js
import { Stethoscope, Users, Calendar } from "lucide-react";

export const summaryConfig = [
    { key: "pendingDoctors", title: "Pending Doctors", icon: <Stethoscope />, path: "/manage/doctors" },
    { key: "activePatients", title: "Active Patients", icon: <Users />, path: "/patients" },
    { key: "appointmentsToday", title: "Appointments Today", icon: <Calendar />, path: "/appointments" },
];
