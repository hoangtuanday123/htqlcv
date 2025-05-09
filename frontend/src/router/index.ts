// import { route } from 'quasar/wrappers';
import {
  // createMemoryHistory,
  createRouter,
  // createWebHashHistory,
  createWebHistory,
} from 'vue-router';

import routes from './routes';
import { userStore } from '../stores/user'
import { storeToRefs } from 'pinia'




/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

// export default route(function (/* { store, ssrContext } */) {
//   // const createHistory = process.env.SERVER
//   //   ? createMemoryHistory
//   //   : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory);

//   const Router = createRouter({
//     scrollBehavior: () => ({ left: 0, top: 0 }),
//     routes,
//     history: createWebHistory(),
//     // Leave this as is and make changes in quasar.conf.js instead!
//     // quasar.conf.js -> build -> vueRouterMode
//     // quasar.conf.js -> build -> publicPath
//     // history: createHistory(process.env.VUE_ROUTER_BASE),
//   });
// //   Router.beforeEach(() => {
// //     const store = userStore()
// //     console.log(store)
// // })

//   return Router;
// });

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
    const _userStore = userStore();
    const { authToken } = storeToRefs(_userStore);
    if (to.path === '/') next()
    if (to.path !== '/login' && !authToken.value) next({ path: '/login', query: { return_url: encodeURIComponent(to.fullPath) }})
    else next()
    next()
})

export default router