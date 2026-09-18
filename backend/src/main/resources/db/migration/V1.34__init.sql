UPDATE notify_template
SET template_content = '* 🔐 登录失败
👤 用户：${userName}
🕒 时间：${loginTime}
🖥 客户端：${client}
📱 设备：${device}
🌍 IP：${ipAddress}
🌐 服务器：${serverUrl} *',
    update_datetime = NOW()
WHERE template_code = 'auth_failed'
  AND channel_type = 'common';

UPDATE notify_template
SET template_content = '* 🔓 登录成功
👤 用户：${userName}
🕒 时间：${loginTime}
🖥 客户端：${client}
📱 设备：${device}
🌍 IP：${ipAddress}
🌐 服务器：${serverUrl} *',
    update_datetime = NOW()
WHERE template_code = 'auth_success'
  AND channel_type = 'common';