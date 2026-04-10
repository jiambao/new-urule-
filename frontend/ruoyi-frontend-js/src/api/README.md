本项目使用浏览器 fetch 调用后端 REST API。
期望后端实现下列接口（PoC 与未来正式实现）：
GET  /api/resources            -> 资源树 JSON (array)
GET  /api/rules?[q&page&size]  -> 规则列表，支持分页返回 { items: [], total: N }
GET  /api/rules/{id}           -> 规则详情, 返回 { id, name, xml, description }
POST /api/rules                -> 创建规则（body JSON）
PUT  /api/rules/{id}           -> 更新规则
DELETE /api/rules/{id}         -> 删除规则
POST /api/rules/execute        -> 执行规则（PoC）
POST /api/decision-tables     -> 保存决策表
GET  /api/decision-tables     -> 列表
GET  /api/decision-tables/{id}/export -> 导出 CSV
