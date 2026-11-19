import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import PerformanceReports from '../components/PerformanceReports.vue';
import PerformanceReportDetail from '../components/PerformanceReportDetail.vue';
import PerformanceMetrics from '../components/PerformanceMetrics.vue';
import PerformanceMetricDetail from '../components/PerformanceMetricDetail.vue';
import PerformancePeriods from '../components/PerformancePeriods.vue';
import PerformancePeriodDetail from '../components/PerformancePeriodDetail.vue';
import PerformanceAttributions from '../components/PerformanceAttributions.vue';
import PerformanceAttributionDetail from '../components/PerformanceAttributionDetail.vue';
import DrawdownPeriods from '../components/DrawdownPeriods.vue';
import DrawdownPeriodDetail from '../components/DrawdownPeriodDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/performanceReports',
    name: 'PerformanceReports',
    component: PerformanceReports
  },
  {
    path: '/performanceReport/:id',
    name: 'PerformanceReportDetail',
    component: PerformanceReportDetail,
    props: true
  },
  {
    path: '/performanceMetrics',
    name: 'PerformanceMetrics',
    component: PerformanceMetrics
  },
  {
    path: '/performanceMetric/:id',
    name: 'PerformanceMetricDetail',
    component: PerformanceMetricDetail,
    props: true
  },
  {
    path: '/performancePeriods',
    name: 'PerformancePeriods',
    component: PerformancePeriods
  },
  {
    path: '/performancePeriod/:id',
    name: 'PerformancePeriodDetail',
    component: PerformancePeriodDetail,
    props: true
  },
  {
    path: '/performanceAttributions',
    name: 'PerformanceAttributions',
    component: PerformanceAttributions
  },
  {
    path: '/performanceAttribution/:id',
    name: 'PerformanceAttributionDetail',
    component: PerformanceAttributionDetail,
    props: true
  },
  {
    path: '/drawdownPeriods',
    name: 'DrawdownPeriods',
    component: DrawdownPeriods
  },
  {
    path: '/drawdownPeriod/:id',
    name: 'DrawdownPeriodDetail',
    component: DrawdownPeriodDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
