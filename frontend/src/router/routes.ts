import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('layouts/BareLayout.vue'),
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
        path: '/register',
        component: () => import('pages/RegisterPage.vue')
      },
      {
        path: '/forgot_password',
        component: () => import('pages/forgotPassword.vue')
      },
      {
        path: '/reset_password',
        component: () => import('pages/resetPassword.vue')
      },
      {
        path: '/account',
        meta: { requiresAuth: true },
        children: [
          {
            path: '',
            component: () => import('pages/Account/account-setting.vue')
          },
          {
            path: 'password',
            component: () => import('pages/Account/change-password.vue')
          }
        ]

      },
      {
        path: '/owner',
        meta: { requiresAuth: true },
        children: [
          {
            path: 'categories',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/Category/category-list.vue')
              }
            ]
          },
          {
            path: 'branchProduct',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/BranchProduct/branchProduct-list.vue')
              }
            ]
          },
          {
            path: 'suppliers',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/Supplier/supplier-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Owner/Supplier/supplier-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Owner/Supplier/supplier-edit.vue')
              }
            ]
          },
          {
            path: 'customers',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/Customer/customer-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Owner/Customer/customer-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Owner/Customer/customer-edit.vue')
              }
            ]
          },
          {
            path: 'products',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/Product/product-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Owner/Product/product-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Owner/Product/product-edit.vue')
              }
            ]
          },
          {
            path: 'saleOrders',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/SaleOrder/saleOrder-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Owner/SaleOrder/saleOrder-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Owner/SaleOrder/saleOrder-edit.vue')
              }
            ]
          },
          {
            path: 'purchaseOrders',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/PurchaseOrder/purchaseOrder-list.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Owner/PurchaseOrder/purchaseOrder-create.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Owner/PurchaseOrder/purchaseOrder-edit.vue')
              }
            ]
          },
          {
            path: 'staffs',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/Staff/staff-list.vue')
              },
              {
                path: ':id/edit',
                component: () => import('pages/Owner/Staff/staff-edit.vue')
              },
              {
                path: 'create',
                component: () => import('pages/Owner/Staff/staff-create.vue')
              }
            ]

          },
          {
            path: 'dashboard',
            children: [
              {
                path: '',
                component: () => import('pages/Owner/Dashboard/dashboard-view.vue')
              }
            ]
          },
          {
            path: 'ai-service',
            children: [
              {
                path: '',
                component: () => import('pages/AiService.vue')
              }
            ]
          }
        ]
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
