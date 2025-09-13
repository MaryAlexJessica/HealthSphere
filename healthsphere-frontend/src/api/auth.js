import axios from "axios";

const API = axios.create({
    baseURL: "http://localhost:8080/auth",
    headers: {
        "Content-Type": "application/json",
    },
});

export const signup = async (data) => {
    const res = await API.post("/signup", data);
    return res.data;
};

export const login = async (data) => {
    const res = await API.post("/login", data); // sends JSON
    return res.data;
};
export default API;
