import axios, { AxiosError, AxiosResponse } from 'axios';
import { userStore } from '../stores/user'
import { storeToRefs } from 'pinia';
import pinia from '../stores/index';
const _userStore = userStore(pinia())

const { authToken } = storeToRefs(_userStore);
interface Todo {
  id: string;
  title: string;
}

interface Login {
  username: string;
  password: string;
}

interface invalidTokenRequest {
  token: string;
}

export interface Role {
  id: string;
  name: string;
}

export interface RoleRequest {
  name: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  phoneNumber: string;
  roles: string[];
  password: string;
}
axios.defaults.baseURL = 'http://localhost:8080';

axios.interceptors.request.use((config) => {
  config.headers['Content-Type'] = 'application/json'

  if (authToken.value) {
    config.headers.Authorization = `Bearer ${authToken.value}`;
  }
  return config;
});

axios.interceptors.response.use(
  (res) => res,
  (error: AxiosError) => {
    const { data, status, config } = error.response!;
    switch (status) {
      case 400:
        console.error(data);
        break;

      case 401:
        console.error('unauthorised');
        break;

      case 404:
        console.error('/not-found');
        break;

      case 500:
        console.error('/server-error');
        break;
    }
    return Promise.reject(error);
  }
);

const responseBody = <T>(response: AxiosResponse<T>) => response.data;

const request = {
  get: <T>(url: string) => axios.get<T>(url).then(responseBody),
  post: <T>(url: string, body: {}) =>
    axios.post<T>(url, body).then(responseBody),
  put: <T>(url: string, body: {}) => axios.put<T>(url, body).then(responseBody),
  delete: <T>(url: string) => axios.delete<T>(url).then(responseBody),
};

const todos = {
  list: () => request.get<Todo[]>('/todos'),
  details: (id: string) => request.get<Todo>(`/todos/${id}`),
  create: (data: Todo) => request.post<void>('/todos', data),
};

const auth = {
  login: (data: Login) => request.post<string>('/auth/login', data),
  home: () => request.get<string>('/random'),
  logout: (data: invalidTokenRequest) => request.post<void>('/auth/logout', data),
}

const user = {
  getUsers: () => request.get<User[]>('/user/'),
  getUser: (id: string) => request.get<User>(`/user/${id}`),
  createUser: (data: User) => request.post<User>('/user/', data),
  updateUser: (id: string, data: User) => request.put<void>(`/user/${id}/update`, data),
  deleteUser: (id: string) => request.delete<void>(`/user/${id}/delete`),
  getCurrentUser: () => request.get<string>('/user/current_user'),
}

const role = {
  getRoles: () => request.get<Role[]>('/role/'),
  getRole: (id: string) => request.get<Role>(`/role/${id}`),
  createRole: (data: RoleRequest) => request.post<Role>('/role/', data),
  updateRole: (id: string, data: RoleRequest) => request.put<Role>(`/role/${id}/update`, data),
  deleteRole: (id: string) => request.delete<void>(`/role/${id}/delete`),

}



const api = {
  auth,
  user,
  role
};

export default { api };