## 简介
**[paopao](https://paopao.godfunc.fun:444)** 是一个使用Java开发的开源的微信消息通知服务。支持一对对消息推送和一对多消息推送。

[![build](https://github.com/Godfunc/paopao/workflows/build/badge.svg)](https://github.com/Godfunc/paopao/actions)
[![release](https://img.shields.io/github/release/Godfunc/paopao.svg)](https://github.com/Godfunc/paopao/releases)
[![downloads](https://img.shields.io/github/downloads/Godfunc/paopao/total.svg)](https://github.com/Godfunc/paopao)
[![license](https://img.shields.io/github/license/Godfunc/paopao)](https://github.com/Godfunc/paopao/blob/master/LICENSE)

## 开发环境
* JDK1.8
* MySql
* Gradle

## 使用的框架
* SpringBoot
* MyBatis-Plus（数据库操作）
* Thymeleaf（HTML模版引擎）
* Websocket（用来做微信扫码登陆）

## API接口
* [`https://paopao.godfunc.fun:444/{token}/send?msg={message}`](https://paopao.godfunc.fun:444/message) 一对一给自己推送消息
* [`https://paopao.godfunc.fun:444/{token}/sendToAlias/{alias}?msg={message}`](https://paopao.godfunc.fun:444/message) 一对一给他人推送消息
* [`https://paopao.godfunc.fun:444/{token}/sendToGroup/{groupUid}?msg={message}`](https://paopao.godfunc.fun:444/message) 一对多给组内成员推送消息

## 快速开始
### 下载代码到本地
执行命令`git clone https://github.com/Godfunc/paopao.git`
### 微信公众号信息配置
1. 前往 [微信公众平台接口测试平台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index) 获取`appID`和`appsecret`
2. 修改`JS接口安全域名`为你的服务所在域名。
3. 获取`测试号二维码`图片中的链接（可以使用在线二维码识别获取）。 
4. `模板消息接口`处`新增测试模板`，`模板标题`填`消息通知`，`模板内容`填`消息： {{msg.DATA}}`，提交或获取到`模板ID`。
5. 修改`网页授权获取用户基本信息`中的`授权回调页面域名`为你服务所在域名。
### 项目配置文件配置`application-{dev}.yml`
1. 使用上面获取到的`appid`和`appsecret`配置 `wx.mp.app-id`和`wx.mp.secret`。
2. 将服务所有域名配置在`host`上。
3. 将识别出来的`测试号二维码`配置在`mpQrCode`上。
4. 将新增的`模板ID`配置在`templateId`上。
### 配置数据库（这里使用的是MySql）
1. 创建一个名为`paopao`的数据库。
2. 执行项目下`db/mysql.sql`创建表。
3. 在配置文件中设置`spring.datasource.url`、`spring.datasource.username`和`spring.datasource.password`。
### 设置生效的配置文件
1. 修改`application.yml`中的`spring.profiles.active`为你刚配置的配置文件。以`dev`为例，如果你刚才的配置信息写到`application-dev.yml`中，那么`spring.profiles.active`就是应该写`dev`。
### 打包
1. `cd paopao`
2. `./gradlew build`
3. jar包在 `build/libs/paopao-{version}.jar`
### 项目启动
1.  `nohup java -jar paopao-{version}.jar >> app.log &`

## 使用releases中的jar包
### 下载项目
1. 前往 [releases](https://github.com/Godfunc/paopao/releases) 下载最新的jar包（paopao-{version}.jar）。
### 项目启动
1. 修改配置信息，***删除换行***，最后得到一条命令
```shell script
nohup java -jar paopao-{version}.jar 
    --spring.datasource.url="jdbc:mysql://localhost:3306/paopao?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai" 
    --spring.datasource.username="paopao" 
    --spring.datasource.password="123456" 
    --host="https://xxx.com" 
    --mpQrCode="http://mpqrxx.com" 
    --templateId="3XMYYC4jpz3nSWWVRehUK0oLBo7WN4A_6L56FlDVIUM" 
    --wx.mp.app-id="wx1234" 
    --wx.mp.secret="123455" 
>>app.log &
```
2. 最终得到的应该是一条这样的命令
`nohup java -jar paopao-{version}.jar --spring.datasource.url="jdbc:mysql://localhost:3306/paopao?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai" --spring.datasource.username="paopao" --spring.datasource.password="123456" --host="https://xxx.com" --mpQrCode="https://mpqr.com" --templateId="3XMYYC4jpz3nSWWVRehUK0oLBo7WN4A_6L56FlDVIUM" --wx.mp.app-id="wx1234" --wx.mp.secret="123455" >> app.log &`