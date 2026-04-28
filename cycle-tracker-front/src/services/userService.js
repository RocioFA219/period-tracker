import axios from 'axios';

const API_URL = "http://localhost:8080/api/user";

export const registerUser = (userData) => {
    return axios.post(API_URL, userData);
};

export const getUserByEmail = (email) => {
    return axios.get(`${API_URL}/search?email=${email}`);
};

export const loginWithPassword = (password) => {
    return axios.post(`${API_URL}/password`, { password: password });
};