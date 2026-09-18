# Mist 部署教程

[返回项目首页](../README.md)

本文使用 `docker-compose.mist.yml` 部署 Mist，包含 Web、API、MySQL 和 Redis 四个服务。

## 1. 准备环境与部署文件

- 安装 Docker Engine 和 Docker Compose v2。
- 建议至少 2 核 CPU、4 GB 内存，并预留数据库和备份空间。
- 准备可访问的 Emby 服务器地址和 API Key。

获取项目：

```bash
git clone https://github.com/LLL198/Mist.git
cd Mist
```

构建镜像前，请将同一版本的部署产物放入以下位置：

| 路径 | 内容 |
| --- | --- |
| `backend/target/mist-api-reconstructed.jar` | API 应用 |
| `backend/lib/` | API 依赖 JAR |
| `frontend/dist/` | Web 静态文件，包含 index.html 和页面资源 |

Git 源码目录不包含以上部署产物。部署已有安装时，可使用该版本保存的构建产物。`Dockerfile.reconstructed` 和 `frontend/Dockerfile` 会分别将它们打包为本地镜像。

## 2. 配置环境变量

在项目根目录创建 `.env`：

```dotenv
MYSQL_ROOT_PASSWORD=replace_with_a_strong_mysql_password
REDIS_PASSWORD=replace_with_a_strong_redis_password
MIST_WEB_PORT=8082
MIST_CORS_ALLOWED_ORIGINS=http://localhost:8082,http://127.0.0.1:8082
TZ=Asia/Shanghai
TMDB_APITOKEN=
TMDB_APIKEY=
HTTP_PROXY_ENABLED=false
HTTP_PROXY=
HTTPS_PROXY=
NO_PROXY=localhost,127.0.0.1,db,redis,mist-api
EMBY_HUB_SEARCH_URL=
```

将 MySQL 和 Redis 密码替换为自己的强密码。保管好 `.env`，不要公开上传。

| 配置项 | 说明 |
| --- | --- |
| `MYSQL_ROOT_PASSWORD`、`REDIS_PASSWORD` | 数据库及 Redis 密码，必填 |
| `MIST_WEB_PORT` | Web 端口，默认 8082 |
| `MIST_CORS_ALLOWED_ORIGINS` | 允许访问 API 的网页来源，多个值用逗号分隔 |
| `TZ` | 时区，默认 Asia/Shanghai |
| `TMDB_APITOKEN`、`TMDB_APIKEY` | 按 TMDB 接入方式填写对应凭据 |
| `HTTP_PROXY_ENABLED` | 是否启用 HTTP 代理 |
| `HTTP_PROXY`、`HTTPS_PROXY` | 代理地址 |
| `NO_PROXY` | 不经过代理的主机，保留容器内部服务名 |

容器中的 `127.0.0.1` 指向容器自身，代理地址需填写容器能够访问的主机地址。

## 3. 配置 Web 转发

打开 `frontend/nginx/default.conf`，将两个 `proxy_pass` 目标设置为 Compose 中的 API 服务名 `mist-api`：

```nginx
# location /api/ 内
proxy_pass http://mist-api:8080/;

# location /avatars/ 内
proxy_pass http://mist-api:8080/avatars/;
```

保留其他配置，通过下一步构建镜像使修改生效。

## 4. 启动与首次使用

```bash
docker compose -f docker-compose.mist.yml config --quiet
docker compose -f docker-compose.mist.yml up -d --build
docker compose -f docker-compose.mist.yml ps
```

首次启动需要等待数据库初始化。查看启动日志：

```bash
docker compose -f docker-compose.mist.yml logs -f mist-api mist-web
```

在部署机器上打开 `http://127.0.0.1:8082`，按页面提示初始化管理员。登录后：

1. 添加 Emby 服务器，填写地址与 API Key。
2. 配置用户访问地址、线路和媒体库权限。
3. 按需开启注册、邀请、积分、求片及通知功能。
4. 使用 Telegram、TMDB 或支付功能时，填写对应服务凭据。

Mist 无需产品激活。账号登录和管理员权限用于保护站点数据。

## 5. 域名、HTTPS 与远程访问

默认 Web 端口仅绑定部署机器的 `127.0.0.1`。使用域名时，可在宿主机配置反向代理，将 HTTPS 请求转发到 `http://127.0.0.1:8082`。

将 `.env` 中的网页来源改为实际地址，例如：

```dotenv
MIST_CORS_ALLOWED_ORIGINS=https://mist.example.com
```

来源必须包含协议和非默认端口，末尾不加 `/`。多个来源用逗号分隔。外层反向代理需支持流式响应，并为实时日志等长连接设置合适的超时。

局域网直接访问时，将 Compose 的 Web 端口映射改为：

```yaml
ports:
  - "${MIST_WEB_PORT:-8082}:80"
```

把实际来源（例如 `http://192.168.1.10:8082`）加入允许列表，并在防火墙放行相应端口。公网访问建议使用 HTTPS。

修改环境变量或端口映射后执行：

```bash
docker compose -f docker-compose.mist.yml up -d
```

## 6. 更新与日常维护

更新前先备份，再更新项目文件和同版本部署产物：

```bash
git pull --ff-only
docker compose -f docker-compose.mist.yml up -d --build
docker compose -f docker-compose.mist.yml ps
```

更新时保留自己的 Nginx 转发、端口与环境设置。页面更新后可按 `Ctrl + F5` 刷新资源。

```bash
# 查看最近日志
docker compose -f docker-compose.mist.yml logs --tail=200 mist-api mist-web db redis

# 重启应用
docker compose -f docker-compose.mist.yml restart mist-api mist-web

# 停止服务
docker compose -f docker-compose.mist.yml down
```

不要删除数据目录或使用 `down -v` 作为日常故障处理手段。

## 7. 备份与恢复

| 路径 | 内容 |
| --- | --- |
| `data/` | 应用持久化文件 |
| `mysql-data/` | MySQL 数据 |
| `redis-data/` | Redis 持久化数据 |
| `.env` | 密码、访问来源和服务参数 |

完整目录备份应在服务停止后进行，以保持数据库文件一致：

```bash
docker compose -f docker-compose.mist.yml stop
# 复制上述目录、.env，以及当前部署配置和部署产物到备份位置。
docker compose -f docker-compose.mist.yml start
```

恢复时先停止目标实例，另行保存其现有数据，再将备份放回对应路径。保留文件权限，使用与备份匹配的数据库版本、密码和应用版本启动。

数据库名称默认是 `foam-api-v2`，保持 Compose 与数据库连接配置一致。使用后台数据迁移功能时，按页面提示选择备份类型和目标服务器，完成后核对用户、配置和业务记录。

## 8. 常见问题

### 浏览器无法连接

检查 Web 服务状态和端口占用。默认绑定 `127.0.0.1` 时，其他机器无法直接连接，远程访问按第 5 节配置。

### 页面白屏

先按 `Ctrl + F5` 强制刷新。确认 `frontend/dist/` 包含同一版本的完整资源，重新构建 Web 镜像。浏览器控制台出现模块导出或脚本加载错误时，检查资源是否缺失或混用了多个版本。

### 出现 Invalid CORS request

检查允许来源是否与浏览器地址的协议、域名和端口一致。修改 `.env` 后执行 `docker compose -f docker-compose.mist.yml up -d`。

### Web 返回 502 或找不到上游主机

检查 Nginx 转发目标是否为 `mist-api:8080`，确认 API 已启动。修改 Nginx 文件后重新构建 Web 镜像。

### API 容器反复重启

查看 API、MySQL 和 Redis 日志，检查密码、目录权限和数据库连接。已有 MySQL 数据目录需使用初始化时的数据库密码；仅修改 `.env` 不会重置数据库内的密码。

### 构建提示找不到 JAR 或静态文件

按第 1 节检查部署产物是否齐全，并在项目根目录执行构建。

### TMDB、Telegram 或支付功能不可用

检查模块是否启用、凭据是否有效，以及容器是否能够访问目标服务。Telegram 群管理还需为机器人分配相应权限。
