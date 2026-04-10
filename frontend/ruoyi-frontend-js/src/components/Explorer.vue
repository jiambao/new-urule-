<template>
  <div>
    <el-tree
      :data="treeData"
      :props="defaultProps"
      highlight-current
      node-key="id"
      @node-click="handleNodeClick"
      default-expand-all>
    </el-tree>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'

export default {
  name: 'Explorer',
  emits: ['node-click'],
  setup(props, { emit }) {
    const treeData = ref([])
    const defaultProps = { children: 'children', label: 'name' }

    async function loadTree() {
      try {
        const res = await fetch('/api/resources')
        if (res.ok) {
          const data = await res.json()
          treeData.value = data
        } else {
          console.error('failed load resources', res.status)
        }
      } catch (e) {
        console.error('error loading resources', e)
      }
    }

    function handleNodeClick(node) {
      emit('node-click', node)
    }

    onMounted(loadTree)

    return { treeData, defaultProps, handleNodeClick }
  }
}
</script>
