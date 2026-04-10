<template>
  <div>
    <el-card>
      <div style="display:flex;justify-content:space-between;align-items:center">
        <h3>规则编辑</h3>
        <div>
          <el-button type="primary" @click="saveRule">保存</el-button>
          <el-button @click="runRule">执行</el-button>
          <el-button @click="back">返回</el-button>
        </div>
      </div>

      <el-form :model="form" style="margin-top:12px">
        <el-form-item label="规则名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" />
        </el-form-item>
        <el-form-item label="规则 XML/脚本">
          <CodeEditor v-model="form.xml" mode="xml" :options="{lineNumbers:true}" />
        </el-form-item>
      </el-form>

      <el-divider />
      <div>
        <h4>执行结果</h4>
        <pre style="background:#f8f8f8;padding:8px;border-radius:4px">{{ runResult }}</pre>
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import CodeEditor from '../components/CodeEditor.vue'

export default {
  name: 'RuleEdit',
  components: { CodeEditor },
  setup() {
    const form = ref({ id: null, name: '', description: '', xml: '<rules></rules>' })
    const runResult = ref('')

    const fetchRule = async (id) => {
      const res = await fetch('/api/rules/' + id)
      if (res.ok) {
        const data = await res.json()
        form.value = { id: data.id, name: data.name, description: data.description, xml: data.xml || '<rules></rules>' }
      }
    }

    onMounted(() => {
      const id = new URLSearchParams(location.search).get('id')
      if (id) fetchRule(id)
    })

    const saveRule = async () => {
      if (form.value.id) {
        await fetch('/api/rules/' + form.value.id, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
        alert('已保存')
      } else {
        const res = await fetch('/api/rules', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
        if (res.ok) {
          const data = await res.json()
          form.value.id = data.id
          alert('已创建，ID=' + data.id)
        }
      }
    }

    const runRule = async () => {
      const res = await fetch('/api/rules/execute', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ xml: form.value.xml }) })
      if (res.ok) {
        const data = await res.json()
        runResult.value = JSON.stringify(data, null, 2)
      } else {
        runResult.value = '执行失败: ' + res.status
      }
    }

    const back = () => { history.back() }

    return { form, runResult, saveRule, runRule, back }
  }
}
</script>
