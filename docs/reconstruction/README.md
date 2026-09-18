# Mist 全栈逆向重构基线

本目录记录从现有 Mist 运行制品恢复出的可维护工程和新旧系统对照结果。

## 当前状态

- 后端已恢复为 Java 21 + Spring Boot 源码工程；990 个反编译 Java 源文件可全量编译。
- 原有资源、MyBatis XML、Flyway 迁移和依赖已纳入独立构建；不再把原始应用 JAR 作为运行时依赖。
- 已移除产品授权子系统、远程授权客户端和运行时授权门禁，部署后无需激活即可使用。
- 前端保留从原 Web 镜像提取的完整 Vue/Vite 静态 bundle，路由、页面懒加载和接口调用保持原行为。
- 前端已增加源码级重建层 `frontend/reconstructed-src`：先覆盖 HTTP 客户端、会话、登录和路由守卫，生产 bundle 仍作为行为 oracle。
- 新旧系统并行运行：旧系统 `8081`，重建系统 `8082`。两者共用现有 MySQL、Redis 和 `/data`，没有删除或重建数据卷。

## 构建与启动

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File backend\build.ps1
docker build -f Dockerfile.reconstructed -t mist-api-reconstructed:local .
docker compose -f docker-compose.mist.yml up -d --build
```

前端入口为 `http://127.0.0.1:8082`。旧系统对照环境仅用于本地验证，不属于新版部署内容。

## 对照验收

```powershell
node tools\contract-smoke.mjs
node tools\frontend-route-smoke.mjs
node tools\frontend-reconstruction-smoke.mjs
node tools\generate-reconstruction-inventory.mjs --write
```

`contract-smoke.mjs` 对照未登录用户接口、任务接口和参数校验响应；`frontend-route-smoke.mjs` 对照所有提取出的前端路由；`inventory.json` 记录后端模块、迁移、前端路由和接口调用清单。
`frontend-reconstruction-smoke.mjs` 使用 mock HTTP 验证重建的 `/api` 客户端、cookie credentials、登录 471 多服务器分支、会话持久化和路由守卫。

## 已知限制

没有原始前端 source map，因此前端仍以提取后的生产 bundle 作为行为基线；目前源码级替换只覆盖核心认证链路，业务页面仍需按 `frontend/recovered/CONTRACT.json` 逐页迁移。所有源级页面通过对照测试后再接入新的 Vite 构建，不应在替换完成前删除 `frontend/dist`。
