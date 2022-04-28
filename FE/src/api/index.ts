import axios from 'axios';

const api = axios.create({
  baseURL: 'http://k6s205.p.ssafy.io:8482/api/',
  headers: {
    'Content-Type': 'application/json',
  },
});

const fileApi = axios.create({
  baseURL: 'http://k6s205.p.ssafy.io:8482/api/',
  headers: {
    'Content-Type': 'multipart/form-data',
  },
});

export { api, fileApi };
