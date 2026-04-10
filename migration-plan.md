# URule -> RuoYi Vue3(JS) 迁移计划

分支： migration/ruoyi-vue3-js

目标：
- 将 urule-core（规则引擎 Java 核心）迁入 RuoYi 后端模块，并重构包名/模块整合。
- 使用 RuoYi 风格前端（Vue3 + JS + Element Plus）替换原 urule-console-js 前端，逐步迁移页面与编辑器。
- 采用短迭代（每提交含小功能），无需人工审核地持续推进到功能全覆盖。

阶段：
0. 初始化（当前）
   - 创建前端最小 Vite 应用（JS），包含 Remark 组件示例与 demo 页面。
   - 在后端添加 ruule-core 模块说明 / 起始结构。
1. 通用组件迁移
   - Tree Explorer（资源树）、规则列表、规则详情（含 Remark/注释）。
2. 编辑器/中等复杂度迁移
   - 决策表编辑（Code 编辑器集成）、脚本编辑、基本规则集编辑。
3. 流/复杂编辑器迁移
   - 决策流/流程编辑器、评分卡、知识包导入导出。
4. 后端整合/测试/部署
   - 所有后端 API 由 RuoYi 后端提供，集成权限、Swagger、CI。
5. 清理与替换
   - 确认所有功能完备后，彻底移除原 urule-console-js 的遗留代码。

交付物：
- frontend/ruoyi-frontend-js/
- backend/ruoyi-backend/ruule-core/
- docs/migration-plan.md、迁移日志

运行与回退：
- 所有更改先在 migration/ruoyi-vue3-js 分支，测试通过后合并。
- 若出现问题，可 revert 分支或 reset 到某 commit。
