import axios from 'axios';

const API_URL = "http://localhost:8081/api/cycles";

// Esta función llamará a tu GET de Java
export const getCyclesByUserId = (userId) => {
    return axios.get(`${API_URL}/${userId}`);
};

// Esta función llamará a tu POST de registro
export const registerPeriod = (userId, startDate) => {
    return axios.post(`${API_URL}/register`, { userId, startDate });
};