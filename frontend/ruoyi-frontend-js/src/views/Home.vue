<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <el-card>
        <h4>资源树</h4>
        <Explorer @node-click="onNodeClick" />
      </el-card>
    </el-col>
    <el-col :span="18">
      <el-card>
        <h4>欢迎 / 快速导航</h4>
        <el-button type="primary" @click="$router.push('/rules')">规则管理</el-button>
        <el-button type="primary" @click="$router.push('/decision-table')">决策表</el-button>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import Explorer from '../components/Explorer.vue'

export default {
  components: { Explorer },
  setup() {
    const onNodeClick = (node) => {
      console.log('node clicked', node)
    }
    return { onNodeClick }
  }
}
</script>
