<template>
  <div class="flow-canvas">
    <div class="toolbar" style="margin-bottom:8px">
      <el-button size="mini" @click="addNode">新增节点</el-button>
    </div>
    <div class="canvas" ref="canvasEl" @dblclick="onDblClick" style="position:relative">
      <svg class="edge-layer" :width="width" :height="height" style="position:absolute;left:0;top:0;pointer-events:none">
        <line v-for="e in edges" :key="e.id" :x1="getNode(e.from).x+80" :y1="getNode(e.from).y+20" :x2="getNode(e.to).x+0" :y2="getNode(e.to).y+20" stroke="#444" stroke-width="2" />
      </svg>
      <div v-for="node in nodes" :key="node.id" class="node" :style="nodeStyle(node)" @mousedown.stop.prevent="startDrag(node,$event)">
        <div class="node-title">{{ node.label }}</div>
        <div class="node-body">ID: {{ node.id }}</div>
        <div class="actions" style="margin-top:6px">
          <el-button size="mini" @click.stop.prevent="connect(node)">连线</el-button>
          <el-button size="mini" type="danger" @click.stop.prevent="removeNode(node)">删除</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue'
let idSeq = 1
export default {
  name: 'FlowCanvas',
  setup(_, { expose }) {
    const nodes = reactive([])
    const edges = reactive([])
    const width = ref(1200)
    const height = ref(600)
    const dragging = { node: null, offsetX: 0, offsetY: 0 }
    const canvasEl = ref(null)
    const connectFrom = ref(null)

    const addNode = () => {
      const n = { id: idSeq++, label: '节点' + idSeq, x: 40 + Math.random()*400, y: 40 + Math.random()*200 }
      nodes.push(n)
    }
    const removeNode = (node) => {
      const idx = nodes.indexOf(node)
      if (idx>=0) nodes.splice(idx,1)
      for (let i=edges.length-1;i>=0;i--) {
        if (edges[i].from===node.id||edges[i].to===node.id) edges.splice(i,1)
      }
    }
    const getNode = (id) => nodes.find(n=>n.id===id) || {x:0,y:0}
    const nodeStyle = (node) => ({ position:'absolute', left: node.x+'px', top: node.y+'px', width:'120px', padding:'8px', border:'1px solid #ccc', borderRadius:'4px', background:'#fff' })

    const startDrag = (node, e) => {
      dragging.node = node
      dragging.offsetX = e.clientX - node.x
      dragging.offsetY = e.clientY - node.y
      window.addEventListener('mousemove', onDrag)
      window.addEventListener('mouseup', stopDrag)
    }
    const onDrag = (e) => {
      if (!dragging.node) return
      dragging.node.x = e.clientX - dragging.offsetX
      dragging.node.y = e.clientY - dragging.offsetY
    }
    const stopDrag = () => {
      dragging.node = null
      window.removeEventListener('mousemove', onDrag)
      window.removeEventListener('mouseup', stopDrag)
    }

    const connect = (node) => {
      if (!connectFrom.value) {
        connectFrom.value = node
      } else if (connectFrom.value.id === node.id) {
        connectFrom.value = null
      } else {
        edges.push({ id: 'e' + Date.now(), from: connectFrom.value.id, to: node.id })
        connectFrom.value = null
      }
    }

    const exportFlow = () => ({ nodes: nodes.map(n=>({ id:n.id, label:n.label, x:n.x, y:n.y })), edges: edges.map(e=>({ id:e.id, from:e.from, to:e.to })) })

    const onDblClick = (e) => {}

    onMounted(() => {})
    onBeforeUnmount(() => { stopDrag() })

    expose({ exportFlow, nodes, edges })
    return { nodes, edges, addNode, removeNode, getNode, nodeStyle, startDrag, connect, width, height, canvasEl }
  }
}
</script>

<style scoped>
.flow-canvas .canvas { position: relative; height: 600px; border: 1px dashed #ddd; overflow: auto; background: #fafafa }
.node { box-shadow: 0 1px 2px rgba(0,0,0,0.05) }
</style>
