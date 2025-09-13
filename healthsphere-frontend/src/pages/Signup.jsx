import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Login.css";
import { signup, login } from "../api/auth";

const Signup = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState(""); // ✅ new email state
    const [role, setRole] = useState("");
    const navigate = useNavigate();
    const [errors, setErrors] = useState({});
    const [message, setMessage] = useState("");

    const handleSignup = async (e) => {
        e.preventDefault(); // 🚀 stop refresh
        console.log("inside signup");
        try {
            const result = await signup({ username, password, role, email });
            console.log(result);

            if (result.status === "ACTIVE") {
                // 👇 directly call login to reuse the same flow
                await handleLogin();
            } else if (result.status === "PENDING") {
                setMessage(result.message); // ✅ show info, not error
                // Optionally redirect them to login page
                //navigate("/login");
            } else {
                setErrors({ form: result.message });
            }
        } catch (err) {
            console.error(err);
            setErrors({ form: "Signup failed. Please try again." });
        }
    }

    const handleLogin = async () => {
        const result = await login({ username, password });

        if (result.status === "ACTIVE") {
            localStorage.setItem("token", result.token);
            localStorage.setItem("role", result.role);
            if (result.role === "ROLE_DOCTOR") {
                navigate("/doctor/dashboard");
            } else if (result.role === "ROLE_PATIENT") {
                navigate("/patient/dashboard");
            } else if (result.role === "ROLE_ADMIN") {
                navigate("/admin/dashboard");
            } else
                setMessage("Invalid Role");
        } else {
            setMessage(result.message); // e.g., PENDING account
        }
    };
    return (
        <div className="auth-container">
            <div className="login-container">
                {/* Left Side: Sign up */}

                <div className="login-left">
                    <h2>Create Account</h2>
                    <form onSubmit={handleSignup}>
                        <input
                            className="input-field"
                            type="text"
                            placeholder="Username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                        <input
                            className="input-field"
                            type="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                        <input
                            className="input-field"
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                        <select className="input-field" value={role} onChange={(e) => setRole(e.target.value)}>
                            <option value="">-- Select Role --</option>
                            <option value="PATIENT">Patient</option>
                            <option value="DOCTOR">Doctor</option>
                            <option value="ADMIN">Admin</option>
                        </select>
                        {errors.form && <p className="error-text">{errors.form}</p>}
                        {message && <p className="success-text">{message}</p>}
                        <button className="btn btn-signin" type="submit">Sign Up</button>
                    </form>
                </div>
                <div className="login-right">
                    <h2>“SignUp with confidence. Manage. Heal. Experience care.”</h2>
                </div>
            </div>
        </div>
    );
};

export default Signup;
