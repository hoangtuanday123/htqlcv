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
