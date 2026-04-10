<template>
  <el-card>
    <div style="display:flex;justify-content:space-between;align-items:center">
      <h3>决策流编辑器（PoC）</h3>
      <div>
        <el-button type="primary" size="small" @click="saveFlow">保存</el-button>
        <el-button size="small" @click="$router.push('/rules')">返回规则</el-button>
      </div>
    </div>
    <div style="margin-top:12px">
      <flow-canvas ref="canvas" />
    </div>
  </el-card>
</template>

<script>
import FlowCanvas from '../components/FlowCanvas.vue'
import { ref } from 'vue'

export default {
  name: 'FlowEditor',
  components: { FlowCanvas },
  setup() {
    const canvas = ref(null)
    const saveFlow = async () => {
      const payload = canvas.value ? canvas.value.exportFlow() : { nodes: [], edges: [] }
      const res = await fetch('/api/flows', { method: 'POST', headers: { 'Content-Type':'application/json' }, body: JSON.stringify(payload) })
      if (res.ok) {
        const data = await res.json()
        alert('保存成功 id=' + data.id)
      } else alert('保存失败')
    }
    return { canvas, saveFlow }
  }
}
</script>
