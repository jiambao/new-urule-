ruule-core module (迁移说明)

目标：
- 将原 youseries/urule 的 urule-core 源代码合并到 RuoYi 后端作为一个独立模块 ruule-core，
  并统一包名（避免冲突）、构建成可被 SpringBoot 项目引用的子模块。

建议步骤：
1. 在 RuoYi 后端的 modules 目录下创建 modules/ruule-core（或在 backend/ 下创建 ruule-core）。
2. 将原仓库 urule-core/src/main/java 下的 com.* 包按需复制到该模块，并对 package 进行重命名，例如：
   原: com.bstek.urule.*  -> 新: com.jiambao.ruule.core.*
3. 在 ruule-core 的 pom.xml 中声明依赖（Spring Boot 版本与父工程版本保持一致）。
4. 在 RuoYi 后端主工程的 pom.xml 中添加模块引用，或在 Spring Boot 启动类使用 @Import 指向该模块的配置。
5. 为前端提供一组 REST Controller（建议放在 ruule-web 模块），接口示例：
   - GET /api/rules             -> 列表
   - GET /api/rules/{id}        -> 详情（包括规则 XML/元数据）
   - POST /api/rules            -> 创建/导入
   - PUT /api/rules/{id}        -> 更新
   - DELETE /api/rules/{id}     -> 删除
   - POST /api/knowledgepack/import
   - GET /api/knowledgepack/download/{id}
6. 保持知识包导出/导入格式与原 urule 兼容（XML / ZIP），并编写兼容层（若原有格式存在差异）。
7. 添加 Swagger/OpenAPI 注释，便于前端开发人员知道接口契约。
