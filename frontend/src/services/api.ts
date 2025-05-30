import axios, { AxiosError, AxiosResponse } from 'axios';
import { userStore } from '../stores/user'
import { storeToRefs } from 'pinia';
import pinia from '../stores/index';
import { ref } from 'vue';
import ChangePassword from 'src/pages/Account/change-password.vue';
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
  businessId: number
}
export interface refreshTokenDTO {
  token: string
}

export interface Customer {
  id: number;
  name: string;
  phone: string;
  dob: Date;
  address: string;
  customerType: string;
  mst: string;
  cmnd: string;
  email: string;
  note: string;
  companyName: string;
  businessId: number;
}

export interface CustomerRequest {
  name: string;
  phone: string;
  dob: Date;
  address: string;
  customerType: string;
  mst: string;
  cmnd: string;
  email: string;
  note: string;
  companyName: string;
  businessId: number;
}

export interface Supplier {
  id: number;
  name: string;
  phone: string;
  address: string;
  branch: string;
  mst: string;
  email: string;
  note: string;
  company: string;
  businessId: number;
}

export interface SupplierRequest {
  name: string;
  phone: string;
  address: string;
  branch: string;
  mst: string;
  email: string;
  note: string;
  company: string;
  businessId: number;
}
export interface Category {
  id: number;
  name: string;
  businessId: number;
}

export interface CategoryRequest {
  name: string;
  businessId: number;
}

export interface BranchProduct {
  id: number;
  name: string;
  businessId: number;
}

export interface BranchProductRequest {
  name: string;
  businessId: number;
}

export interface Product {
  id: number;
  name: string;
  capitalPrice: number;
  salePrice: number;
  stockQuantity: number;
  category: Category;
  branchProduct: BranchProduct;
  businessId: number;
}

export interface ProductRequest {
  name: string;
  capitalPrice: number;
  salePrice: number;
  stockQuantity: number;
  categoryId: number;
  branchProductId: number;
  businessId: number;
}

export interface Guarantee {
  id: number;
  name: string;
  guaranteeTime: number;
  productId: number;
}

export interface GuaranteeRequest {
  name: string;
  guaranteeTime: number;
  productId: number;
}
export interface PurchaseOrderItem {
  id: number;
  productId: number;
  purchaseOrdersId: number;
  quantity: number;
  unitPrice: number;
  note: string;
}

export interface PurchaseOrderItemRequest {
  productId: number;
  purchaseOrdersId: number;
  quantity: number;
  unitPrice: number;
  note: string;
}
export interface PurchaseOrder {
  id: number;
  totalAmount: number;
  totalAmountPaid: number;
  supplierId: number;
  subStatus: string;
  status: string;
  purchaseOrderItemsRequestDTO: PurchaseOrderItem[];
  businessId: number;
}

export interface PurchaseOrderRequest {
  totalAmount: number;
  totalAmountPaid: number;
  supplierId: number;
  subStatus: string;
  status: string;
  purchaseOrderItemsRequestDTO: PurchaseOrderItem[];
  businessId: number;
}

export interface SaleOrderItem {
  id: number;
  productId: number;
  SaleOrdersId: number;
  quantity: number;
  unitPrice: number;
  note: string;
}

export interface SaleOrderItemRequest {
  productId: number;
  SaleOrdersId: number;
  quantity: number;
  unitPrice: number;
  note: string;
}

export interface SaleOrder {
  id: number;
  totalAmount: number;
  totalAmountPaid: number;
  customerId: number;
  subStatus: string;
  status: string;
  saleOrderItemsRequestDTO: SaleOrderItem[];
  businessId: number;

}

export interface SaleOrderRequest {
  totalAmount: number;
  totalAmountPaid: number;
  customerId: number;
  subStatus: string;
  status: string;
  saleOrderItemsRequestDTO: SaleOrderItem[];
  businessId: number;
}

export interface Business {
  id: number
  name: string;
  mst: string;
  email: string;
  phone: string;
}

export interface BusinessRequest {
  name: string;
  mst: string;
  email: string;
  phone: string;
}

export interface ChangePassword {
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
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

// const todos = {
//   list: () => request.get<Todo[]>('/todos'),
//   details: (id: string) => request.get<Todo>(`/todos/${id}`),
//   create: (data: Todo) => request.post<void>('/todos', data),
// };

const auth = {
  login: (data: Login) => request.post<string>('/auth/login', data),
  home: () => request.get<string>('/random'),
  logout: (data: invalidTokenRequest) => request.post<void>('/auth/logout', data),
  checkToken: (token: string) => request.get<string>(`/auth/check_token?token=${encodeURIComponent(token)}`),
  refreshToken: (data: refreshTokenDTO) => request.post<string>('/auth/refresh-token', data),
}

const user = {
  getUsers: () => request.get<User[]>('/user/'),
  getUser: (id: number) => request.get<User>(`/user/${id}`),
  createUser: (data: User) => request.post<void>('/user/', data),
  updateUser: (id: number, data: User) => request.put<void>(`/user/${id}/update`, data),
  deleteUser: (id: string) => request.delete<void>(`/user/${id}/delete`),
  getCurrentUser: () => request.get<string>('/user/current_user'),
  ChangePassword: (data: ChangePassword) => request.put<void>('/user/changePassword', data)
}

const role = {
  getRoles: () => request.get<Role[]>('/role/'),
  getRole: (id: string) => request.get<Role>(`/role/${id}`),
  createRole: (data: RoleRequest) => request.post<Role>('/role/', data),
  updateRole: (id: string, data: RoleRequest) => request.put<Role>(`/role/${id}/update`, data),
  deleteRole: (id: string) => request.delete<void>(`/role/${id}/delete`),

}

const customer = {
  getCustomers: (businessId: number) => request.get<Customer[]>(`/customer?businessId=${businessId}`),
  getCustomer: (id: string) => request.get<Customer>(`/customer/${id}`),
  createCustomer: (data: CustomerRequest) => request.post<number>('/customer/', data),
  updateCustomer: (id: string, data: CustomerRequest) => request.put<void>(`/customer/${id}/update`, data),
  deleteCustomer: (id: string) => request.delete<void>(`/customer/${id}/delete`),

}

const supplier = {
  getSuppiers: (businessId: number) => request.get<Supplier[]>(`/supplier?businessId=${businessId}`),
  getSupplier: (id: string) => request.get<Supplier>(`/supplier/${id}`),
  createSupplier: (data: SupplierRequest) => request.post<number>('/supplier/', data),
  updateSupplier: (id: string, data: SupplierRequest) => request.put<void>(`/supplier/${id}/update`, data),
  deleteSupplier: (id: string) => request.delete<void>(`/supplier/${id}/delete`),

}

const category = {
  getCategories: (businessId: number) => request.get<Category[]>(`/category?businessId=${businessId}`),
  getCategory: (id: string) => request.get<Category>(`/category/${id}`),
  createCategory: (data: CategoryRequest) => request.post<number>('/category/', data),
  deleteCategory: (id: string) => request.delete<void>(`/category/${id}/delete`),
}

const branchProduct = {
  getBranchProducts: (businessId: number) => request.get<BranchProduct[]>(`/branchProduct?businessId=${businessId}`),
  getBranchProduct: (id: string) => request.get<BranchProduct>(`/branchProduct/${id}`),
  createBranchProduct: (data: BranchProductRequest) => request.post<number>('/branchProduct/', data),
  deleteBranchProduct: (id: string) => request.delete<void>(`/branchProduct/${id}/delete`),
}

const product = {
  getProducts: (businessId: number) => request.get<Product[]>(`/product?businessId=${businessId}`),
  getProduct: (id: string) => request.get<Product>(`/product/${id}`),
  createProduct: (data: ProductRequest) => request.post<number>('/product/', data),
  updateProduct: (id: string, data: ProductRequest) => request.put<void>(`/product/${id}/update`, data),
  deleteProduct: (id: string) => request.delete<void>(`/product/${id}/delete`),

}


const guarantee = {
  getGuaranteeProduct: (product_id: string) => request.get<Guarantee[]>(`/guarantee/${product_id}/product`),
  createGuarantee: (data: GuaranteeRequest) => request.post<number>('/guarantee/', data),
  deleteGuarantee: (id: string) => request.delete<void>(`/guarantee/${id}/delete`),
}

const purchaseOrder = {
  getPurchaseOrders: (businessId: number) => request.get<PurchaseOrder[]>(`/purchaseOrders?businessId=${businessId}`),
  getPurchaseOrder: (id: string) => request.get<PurchaseOrder>(`/purchaseOrders/${id}`),
  createPurchaseOrder: (data: PurchaseOrderRequest) => request.post<number>('/purchaseOrders/', data),
  deletePurchaseOrder: (id: string) => request.delete<void>(`/purchaseOrders/${id}/delete`),
  updatePurchaseOrder: (id: string, data: PurchaseOrderRequest) => request.put<void>(`/purchaseOrders/${id}/update`, data),

}

const purchaseOrderItem = {
  getPurchaseOrderItemsbyPurchaseOrder: (purchase_order_id: string) => request.get<PurchaseOrderItem[]>(`/purchaseOrderItems/${purchase_order_id}/purchaseOrder`),
  createPurchaseOrderItems: (data: PurchaseOrderItemRequest) => request.post<number>('/purchaseOrderItems/', data),
  updatePurchaseOrderItems: (id: string, data: PurchaseOrderItemRequest) => request.put<void>(`/purchaseOrderItems/${id}/update`, data),
  deletePurchaseOrderItems: (id: string) => request.delete<void>(`/purchaseOrderItems/${id}/delete`),

}

const saleOrderItem = {
  getSaleItemsbySaleOrder: (sale_order_id: string) => request.get<SaleOrderItem[]>(`/saleOrderItems/${sale_order_id}/saleOrder`),
  createSaleOrderItems: (data: SaleOrderItemRequest) => request.post<number>('/saleOrderItems/', data),
  updateSaleOrderItems: (id: string, data: SaleOrderItemRequest) => request.put<void>(`/saleOrderItems/${id}/update`, data),
  deleteSaleOrderItems: (id: string) => request.delete<void>(`/saleOrderItems/${id}/delete`),

}

const saleOrder = {
  getSaleOrders: (businessId: number) => request.get<SaleOrder[]>(`/saleOrders?businessId=${businessId}`),
  getSaleOrder: (id: string) => request.get<SaleOrder>(`/saleOrders/${id}`),
  createSaleOrder: (data: SaleOrderRequest) => request.post<number>('/saleOrders/', data),
  deleteSaleOrder: (id: string) => request.delete<void>(`/saleOrders/${id}/delete`),
  updateSaleOrder: (id: string, data: SaleOrderRequest) => request.put<void>(`/saleOrders/${id}/update`, data),

}

const business = {
  getBusinesses: () => request.get<Business[]>('/businesses/'),
  getBusiness: (id: number) => request.get<Business>(`/businesses/${id}`),
  createBusiness: (data: BusinessRequest) => request.post<number>('/businesses/', data),
  updateBusiness: (id: number, data: BusinessRequest) => request.put<void>(`/businesses/${id}/update`, data),
  blockBusiness: (id: string) => request.put<void>(`/businesses/${id}/block`, {}),
  openBlockBusiness: (id: string) => request.put<void>(`/businesses/${id}/open`, {}),
}
const api = {
  auth,
  user,
  role,
  customer,
  supplier,
  category,
  branchProduct,
  product,
  guarantee,
  purchaseOrder,
  purchaseOrderItem,
  saleOrderItem,
  saleOrder,
  business
};

export default { api };
