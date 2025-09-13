import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/auth";
import { useUser } from "../context/UserContext";
import "../styles/Login.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { handleLogin } = useUser();

  // Form Validation
  const validateForm = () => {
    const newErrors = {};
    if (!username.trim()) newErrors.username = "Username is required";
    if (!password.trim()) newErrors.password = "Password is required";
    else if (password.length < 6)
      newErrors.password = "Password must be at least 6 characters";

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    if (!validateForm()) return;

    setLoading(true);
    try {
      const result = await login({ username, password });

      if (result.status === "ACTIVE") {
        // Decode JWT to extract username
        const decoded = JSON.parse(atob(result.token.split(".")[1]));

        const normalizedUser = {
          username: decoded.sub,
          role: result.role,
          role_id: result.role,
          token: result.token,
        };

        // Save in context + localStorage
        handleLogin(normalizedUser);
        // ✅ Save token in localStorage for API calls
        localStorage.setItem("token", normalizedUser.token);

        // Navigate based on role
        switch (normalizedUser.role) {
          case "ROLE_DOCTOR":
            navigate("/doctor/dashboard");
            break;
          case "ROLE_PATIENT":
            navigate("/patient/dashboard");
            break;
          case "ROLE_ADMIN":
            console.log("ADMIN:");
            navigate("/admin/dashboard");
            break;
          default:
            setErrors({ form: "Invalid role" });
        }
      } else {
        setErrors({ form: result.message });
      }
    } catch (err) {
      console.error(err);
      setErrors({ form: "Login failed. Please try again." });
    } finally {
      setLoading(false);
    }
  };

  const handleForgotPassword = () => {
    const emailInput = prompt("Enter your registered email to reset password:");
    if (!emailInput) return;

    // Replace with secure backend call later
    alert(`If registered, password reset link sent to ${emailInput}`);
  };

  return (
    <div className="auth-container">
      <div className="login-container">
        <div className="login-left">
          <h2>Sign in</h2>
          <form onSubmit={handleLoginSubmit}>
            <input
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            {errors.username && <p className="error-text">{errors.username}</p>}

            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            {errors.password && <p className="error-text">{errors.password}</p>}

            <span className="forgot-link" onClick={handleForgotPassword}>
              Forgot your password?
            </span>

            {errors.form && <p className="error-text">{errors.form}</p>}

            <button type="submit" className="btn btn-signin">Sign In
            </button>
          </form>
        </div>

        <div className="login-right">
          <h2>Create Account!</h2>
          <p>Sign up if you still don't have an account...</p>
          <button className="btn btn-signup" onClick={() => navigate("/signup")}>
            Sign Up
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
