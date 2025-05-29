import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('layouts/barelayout.vue'),
    children: [
      {
        path: '',
        component: () => import('pages/IndexPage.vue')
      },
      {
        path: '/login',
        component: () => import('pages/LoginPage.vue')
      },
      {
        path: '/home',
        component: () => import('pages/homepage.vue')
      },
      {
        path:'/register',
        component: () => import('pages/RegisterPage.vue')
      },
      {
        path: '/admin',
        meta: { requiresAuth: true },
        children: [
          {
            path: 'roles',
            children: [
              {
                path: '',
                component: () => import('pages/Admin/Role/role-list.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Admin/Role/role-edit.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Admin/Role/role-create.vue')
              }
            ]

          },
          {
            path: 'users',
            children: [
              {
                path: '',
                component: () => import('pages/Admin/User/user-list.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Admin/User/user-edit.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Admin/User/user-create.vue')
              }
            ]

          },
          {
            path: "customers",
            children: [
              {
                path: '',
                component: () => import('pages/Admin/Customer/customer-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Admin/Customer/customer-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Admin/Customer/customer-edit.vue')
              }
            ]
          },
          {
            path: 'suppliers',
            children: [
              {
                path: '',
                component: () => import('pages/Admin/Supplier/supplier-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Admin/Supplier/supplier-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Admin/Supplier/supplier-edit.vue')
              }
            ]
          },
          {
            path: 'products',
            children: [
              {
                path: '',
                component: () => import('pages/Admin/Product/product-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Admin/Product/product-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Admin/Product/product-edit.vue')
              }
            ]
          },
          {
            path: "purchaseOrders",
            children: [
              {
                path: '',
                component: () => import('pages/Admin/PurchaseOrder/purchaseOrder-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Admin/PurchaseOrder/purchaseOrder-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Admin/PurchaseOrder/purchaseOrder-edit.vue')
              }
            ]
          },
          {
            path:"saleOrders",
            children:[
              {
                path:'',
                component:()=> import('pages/Admin/SaleOrder/saleOrder-list.vue')
              },
              {
                path:'create',
                component:()=>import('pages/Admin/SaleOrder/saleOrder-create.vue')
              },
              {
                path:':id/edit',
                component:()=>import('pages/Admin/SaleOrder/saleOrder-edit.vue')
              }
            ]
          },
          {
            path:"branchProduct",
            children:[
              {
                path:'',
                component:()=>import('pages/Admin/BranchProduct/branchProduct-list.vue')
              }
            ]
          },
          {
            path:"categories",
            children:[
              {
                path:'',
                component:()=>import('pages/Admin/Category/category-list.vue')
              }
            ]
          }
        ]
      }
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
