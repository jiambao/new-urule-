<template>
  <div>
    <textarea ref="editorEl"></textarea>
  </div>
</template>

<script>
import { onMounted, ref, onBeforeUnmount, watch } from 'vue'
import CodeMirror from 'codemirror'
import 'codemirror/lib/codemirror.css'
import 'codemirror/mode/xml/xml.js'
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/theme/eclipse.css'

export default {
  name: 'CodeEditor',
  props: {
    modelValue: { type: String, default: '' },
    mode: { type: String, default: 'xml' },
    options: { type: Object, default: () => ({ lineNumbers: true, theme: 'eclipse' }) }
  },
  emits: ['update:modelValue', 'change'],
  setup(props, { emit }) {
    const editorEl = ref(null)
    let cm = null

    onMounted(() => {
      cm = CodeMirror.fromTextArea(editorEl.value, Object.assign({ mode: props.mode }, props.options))
      cm.setValue(props.modelValue || '')
      cm.on('change', () => {
        const val = cm.getValue()
        emit('update:modelValue', val)
        emit('change', val)
      })
    })

    watch(() => props.modelValue, (v) => {
      if (!cm) return
      if (cm.getValue() !== (v || '')) cm.setValue(v || '')
    })

    onBeforeUnmount(() => {
      if (cm) cm.toTextArea()
    })

    return { editorEl }
  }
}
</script>

<style scoped>
textarea { width: 100%; height: 300px; display: none; }
</style>
