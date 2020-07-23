## 简介
**[paopao](https://paopao.godfunc.fun)** 是一个使用Java开发的开源的微信消息通知服务。支持一对对消息推送和一对多消息推送。

<img alt="build" src="https://github.com/Godfunc/paopao/workflows/build/badge.svg">
<img alt="release" src="https://img.shields.io/github/release/Godfunc/paopao.svg">
<img alt="downloads" src="https://img.shields.io/github/downloads/Godfunc/paopao/total.svg">
<img alt="license" src="https://img.shields.io/github/license/Godfunc/paopao">

## 快速开始
### 微信公众号信息配置
1. 前往[微信公众平台接口测试平台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)获取`appID`和`appsecret`
2. 修改`JS接口安全域名`为你的服务所在域名。
3. 获取`测试号二维码`图片中的链接（可以使用在线二维码识别获取）。 
4. 修改`网页授权获取用户基本信息`中的`授权回调页面域名`为你服务所在域名。
### 项目配置文件配置`application-{dev}.yml`
1. 使用上面获取到的`appid`和`appsecret`配置 `wx.mp.app-id`和`wx.mp.secret`。
2. 将服务所有域名配置在`host`上。
3. 将识别出来的`测试号二维码`配置在`mpQrCode`上。
### 配置数据库（这里使用的是MySql）
1. 创建一个名为`paopao`的数据库。
2. 执行项目下`db/mysql.sql`创建表。
3. 在配置文件中设置`spring.datasource.url`、`spring.datasource.username`和`spring.datasource.password`。
### 设置生效的配置文件
1. 修改`application.yml`中的`spring.profiles.active`为你刚配置的配置文件。以`dev`为例，如果你刚才的配置信息写到`application-dev.yml`中，那么`spring.profiles.active`就是应该写`dev`。
### 项目启动
1.  `nohup java -jar {jar包文件名} >> app.log &`

## API接口
* [`https://paopao.godfunc.fun/{token}/send?msg={message}`](https://paopao.godfunc.fun/message) 一对一给自己推送消息
* [`https://paopao.godfunc.fun/{token}/sendToAlias/{alias}?msg={message}`](https://paopao.godfunc.fun/message-alias) 一对一给他人推送消息
* [`https://paopao.godfunc.fun/{token}/sendToGroup/{groupUid}?msg={message}`](https://paopao.godfunc.fun/message-group) 一对多给组内成员推送消息

## 开发环境
* JDK1.8
* MySql
* Gradle

## 使用的框架
* SpringBoot
* MyBatis-Plus（数据库操作）
* Thymeleaf（HTML模版引擎）
* Websocket（用来做微信扫码登陆）