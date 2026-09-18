# 前端源码级重建（分阶段）

`frontend/recovered` 是原始生产 bundle 的完整提取和格式化版本；由于制品没有 source map，它不是原作者的 Vue SFC。`frontend/reconstructed-src` 是在 bundle 行为、路由元数据和接口调用契约基础上逐步恢复的可维护源码，当前优先覆盖会话、HTTP 客户端、登录接口和路由守卫。

## 已恢复的行为契约

- `session/session.js` 保留 `foam-web-session`、localStorage/sessionStorage 选择、管理员/分销商判断和用户资料更新规则。
- `api/client.js` 保留 `/api` 前缀、cookie credentials、JSON/FormData/URLSearchParams 序列化和 `{code,msg,data}` 响应封装。
- `api/auth.js` 保留登录 471 多服务器选择和 `embyuser-*` 会话 token 规则。
- `api/registration.js` 保留卡密注册、普通注册、邀请码注册及登录页支付下单/查单的参数映射。
- `router/routes.js` 与 `router/guard.js` 保留登录、业务页、管理员、主管理员、分销商和菜单权限规则；部署后无需激活即可使用。

## 交付边界

这些文件是源码重建层；部署目录中的 `frontend/dist` 已通过 `node tools/remove-license-from-frontend-dist.mjs` 同步移除产品授权 UI 和 API。每次补齐一组页面后，先用 `node tools/frontend-route-smoke.mjs` 和 API 对照测试验证，再接入新的 Vite 构建。
