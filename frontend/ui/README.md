# Mist UI

运行 `node tools/install-mist-ui.mjs` 将此目录的样式和交互安装到 `frontend/dist`。然后按项目现有方式构建 Web 镜像。

- 表单、导航、表格：沿用现有 Vuetify 组件。
- 当前入口：`mist-editorial.js` 和 `mist-editorial.css`；早期 `mist-ui.*` 不再加载。
- 卡片拖动：Embla Carousel 8.6.0，来自 npm `embla-carousel@8.6.0`，本地加载。章节采用原生滚动。
- 开场动画：3.55 秒的标记、交错网格、页面骨架和正文揭幕动画，使用原生 CSS 与 `clip-path`，动画结构依据 `ljj.world` 的实际前端实现重新设计。
- 字体：Geist Variable 与 Manrope Variable 5.3.0，来自 Fontsource，本地加载并保留 OFL 许可证。
- 每个浏览器标签页首次进入登录或邀请注册页面时展示介绍；退出后当前会话不重复展示，可以通过页面右上角重新查看。
- 滚动、章节按钮和键盘均可操作；Escape 返回表单。系统设置减少动态效果时取消缓动。
- `mist-editorial.js` 只增加展示内容，不读取账户字段或修改认证接口。

参考：[ljj.world](https://ljj.world/)、[Vuetify](https://vuetifyjs.com/)、[Embla](https://www.embla-carousel.com/)。

动物素材：`media/mist-character.png`，使用内置 imagegen 生成并做抠图处理。提示词摘要：原创炭灰色短毛猫，米白胸口与爪尖、砖红眼睛与细砖红项圈，坐姿四分之三侧面微微仰头，细腻线稿与柔和绘画阴影，首屏构图沿用原肖像（主体撑满画面、底部贴边），透明背景，无文字和标志。原人物肖像保留在 `media/mist-character.backup.png`。
