import { createRouter, createWebHistory } from 'vue-router'
import { authService } from '../services/api'

const routes = [
  {
    path: '/',
    redirect: '/turniri'
  },
  {
    path: '/login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/turniri',
    component: () => import('../views/TurniriView.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/turniri/:id',
    component: () => import('../views/TurnirDetailView.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !authService.isLoggedIn()) {
    next('/login')
  } else {
    next()
  }
})

export default router