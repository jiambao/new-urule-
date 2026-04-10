import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import RuleList from '../views/RuleList.vue'
import RuleEdit from '../views/RuleEdit.vue'
import DecisionTable from '../views/DecisionTable.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/rules', name: 'RuleList', component: RuleList },
  { path: '/rules/edit', name: 'RuleEdit', component: RuleEdit },
  { path: '/decision-table', name: 'DecisionTable', component: DecisionTable }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
