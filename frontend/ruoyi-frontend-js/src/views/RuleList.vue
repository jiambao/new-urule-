<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;align-items:center">
        <h3>规则列表</h3>
        <div>
          <el-input v-model="q" placeholder="搜索规则名" size="small" style="width:220px" />
          <el-button size="small" type="primary" @click="fetchRules">搜索</el-button>
          <el-button size="small" @click="toCreate">新建规则</el-button>
        </div>
      </div>

      <el-table :data="rules" style="width:100%;margin-top:12px">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="规则名" />
        <el-table-column prop="description" label="描述" />
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button type="primary" size="mini" @click="editRule(row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="deleteRule(row.id)">删除</el-button>
            <el-button size="mini" @click="viewXml(row.id)">XML</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        style="margin-top:12px;text-align:right"
        :current-page="page"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        @current-change="onPageChange" />
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'

export default {
  name: 'RuleList',
  setup() {
    const rules = ref([])
    const q = ref('')
    const page = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    async function fetchRules() {
      const params = new URLSearchParams()
      params.append('q', q.value)
      params.append('page', page.value)
      params.append('size', pageSize.value)
      const res = await fetch('/api/rules?' + params.toString())
      if (res.ok) {
        const data = await res.json()
        rules.value = data.items || data
        total.value = data.total || (data.items ? data.items.length : data.length)
      } else {
        console.error('fetch rules failed', res.status)
      }
    }

    function editRule(row) {
      const url = '/rules/edit?id=' + encodeURIComponent(row.id)
      window.location.href = url
    }
    async function deleteRule(id) {
      if (!confirm('确定删除规则 ' + id + ' ?')) return
      const res = await fetch('/api/rules/' + id, { method: 'DELETE' })
      if (res.ok) {
        fetchRules()
      } else {
        alert('删除失败 ' + res.status)
      }
    }
    function toCreate() {
      window.location.href = '/rules/edit'
    }
    async function viewXml(id) {
      const res = await fetch('/api/rules/' + id)
      if (res.ok) {
        const data = await res.json()
        alert('Rule XML:\n' + (data.xml || '<empty/>'))
      }
    }

    function onPageChange(p) {
      page.value = p
      fetchRules()
    }

    onMounted(fetchRules)

    return { rules, q, page, pageSize, total, fetchRules, editRule, deleteRule, toCreate, viewXml, onPageChange }
  }
}
</script>
