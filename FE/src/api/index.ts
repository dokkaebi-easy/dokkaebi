import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api/',
  headers: {
    'Content-Type': 'application/json',
  },
});

const fileApi = axios.create({
  baseURL: 'http://localhost:8080/api/',
  headers: {
    'Content-Type': 'multipart/form-data',
  },
});

export { api, fileApi };
