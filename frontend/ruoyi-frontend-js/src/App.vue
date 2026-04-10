<template>
  <el-container style="height:100vh">
    <el-header style="background:#f5f7fa;display:flex;align-items:center;justify-content:space-between">
      <div style="display:flex;align-items:center">
        <h3 style="margin:0;padding-right:12px">URule — Migration Demo</h3>
        <el-button type="text" @click="$router.push('/')">Home</el-button>
        <el-button type="text" @click="$router.push('/rules')">规则</el-button>
        <el-button type="text" @click="$router.push('/decision-table')">决策表</el-button>
      </div>
    </el-header>
    <el-main style="padding:16px">
      <router-view />
    </el-main>
  </el-container>
</template>

<script>
export default {}
</script>
