<template>
  <el-card>
    <div style="display:flex;justify-content:space-between;align-items:center">
      <h3>决策表编辑器（PoC）</h3>
      <div>
        <input ref="fileInput" type="file" accept=".csv" style="display:none" @change="onFileChange" />
        <el-button size="small" @click="triggerFile">导入 CSV</el-button>
        <el-button size="small" @click="exportCsv">导出 CSV</el-button>
        <el-button type="primary" size="small" @click="saveTable">保存</el-button>
        <el-button @click="$router.push('/rules')">返回规则</el-button>
      </div>
    </div>

    <el-table :data="rows" style="width:100%;margin-top:12px" border>
      <el-table-column v-for="(col, idx) in columns" :key="idx" :label="col" :prop="'c' + idx">
        <template #default="{ row }">
          <el-input v-model="row['c' + idx]" size="small" @change="markDirty" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row, $index }">
          <el-button type="danger" size="mini" @click="removeRow($index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top:12px;display:flex;gap:8px">
      <el-button size="small" @click="addRow">新增行</el-button>
      <el-button size="small" @click="addColumn">新增列</el-button>
    </div>

    <el-divider />
    <div>
      <h4>调试信息</h4>
      <pre style="background:#f8f8f8;padding:8px;border-radius:4px">{{ debug }}</pre>
    </div>
  </el-card>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'

export default {
  name: 'DecisionTable',
  setup() {
    const columns = ref(['条件A', '条件B', '结论'])
    const rows = ref([
      { c0: 'x>10', c1: 'y==1', c2: 'PASS' },
      { c0: 'x<=10', c1: 'y==0', c2: 'FAIL' }
    ])
    const dirty = ref(false)
    const debug = ref('')

    const fileInput = ref(null)

    function markDirty() { dirty.value = true }

    function addRow() {
      const r = {}
      for (let i = 0; i < columns.value.length; i++) r['c' + i] = ''
      rows.value.push(r)
      markDirty()
    }
    function removeRow(idx) {
      rows.value.splice(idx, 1)
      markDirty()
    }
    function addColumn() {
      columns.value.push('列' + (columns.value.length + 1))
      rows.value.forEach(r => r['c' + (columns.value.length - 1)] = '')
      markDirty()
    }

    function triggerFile() { fileInput.value && fileInput.value.click() }

    function onFileChange(e) {
      const f = e.target.files && e.target.files[0]
      if (!f) return
      const reader = new FileReader()
      reader.onload = (ev) => {
        const text = ev.target.result
        parseCsv(text)
      }
      reader.readAsText(f, 'utf-8')
      e.target.value = ''
    }

    function parseCsv(text) {
      const lines = text.split(/\r?\n/).filter(l => l.trim() !== '')
      if (lines.length === 0) return
      const hdr = lines[0].split(',')
      columns.value = hdr.map(h => h.trim() || '列')
      const newRows = []
      for (let i = 1; i < lines.length; i++) {
        const cols = lines[i].split(',')
        const r = {}
        for (let j = 0; j < columns.value.length; j++) {
          r['c' + j] = (cols[j] || '').trim()
        }
        newRows.push(r)
      }
      rows.value = newRows
      markDirty()
    }

    function exportCsv() {
      const hdr = columns.value.join(',')
      const lines = rows.value.map(r => columns.value.map((c, idx) => {
        const v = r['c' + idx] || ''
        return v.includes(',') ? '"' + v.replace(/"/g, '""') + '"' : v
      }).join(','))
      const csv = [hdr].concat(lines).join('\n')
      const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' })
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = 'decision-table.csv'
      document.body.appendChild(a)
      a.click()
      a.remove()
      URL.revokeObjectURL(url)
    }

    async function saveTable() {
      const payload = { columns: columns.value, rows: rows.value }
      const res = await fetch('/api/decision-tables', { method: 'POST', headers: { 'Content-Type':'application/json' }, body: JSON.stringify(payload) })
      if (res.ok) {
        const data = await res.json()
        debug.value = '保存成功: id=' + data.id
        dirty.value = false
      } else {
        debug.value = '保存失败: ' + res.status
      }
    }

    onMounted(() => {
      debug.value = 'Ready'
    })

    return { columns, rows, addRow, addColumn, removeRow, triggerFile, fileInput, onFileChange, exportCsv, saveTable, debug, markDirty }
  }
}
</script>
