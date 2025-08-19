# SpringBootSimpleCodeTemplate
SpringBoot + Redis + MyBatis-Plus + Swagger + 统一异常处理 + 统一返回 + 统一日志 + 阿里短信（SMS） +  用户登录注册 + JWT + 拦截器 + 阿里文件上传（OSS）
+ 微信小程序（授权登录获取openid + 获取微信手机号码）
+项目对接方案（对称加密+ 非对称加密）


【对称加密+非对称加密】
1、A服务生成一对非对称密钥，保留私钥，将公钥传给B服务
2、B服务拿到公钥，生成一个对称加密密钥
3、B服务将对称密钥用非对称密钥公钥加密，传输给A服务
4、A服务拿到后用私钥解密保存
5、这样双方就拥有了中间无法拦截的安全的对称密钥，即可传输数据


#### 介绍
    wx在线语音转换api，通过websocket连接，实时转换并返回音频数据。

#### 软件架构
    Springboot
    1、Microsoft API
    2、ALi API（#通义听悟）
    3、KeDaxunfei API
    4、Tencent API

	阿里云在线语音转换
        https://help.aliyun.com/zh/tingwu/getting-started-1?spm=a2c4g.11186623.0.0.784d14af2dgjxq	
	微软语音转换
	    https://learn.microsoft.com/zh-cn/azure/ai-services/speech-service/get-started-speech-to-text?tabs=windows%2Cterminal&pivots=programming-language-java	
	腾讯语音转换
        https://cloud.tencent.com/product/asr	
	科大讯飞开发平台
        https://console.xfyun.cn/services/rta

    通过修改application.yml的 wxtype值 来打包微软云 还是 阿里云
    目前：
        微软云是提供给海外版使用
        阿里云是提供给政企版和教育版使用


#### 项目结构概览
stt-proxy-server/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.stt.proxy/
│   │   │       ├── adapter/
│   │   │       │   ├── SpeechToTextService.java
│   │   │       │   └── AliTingwuRealtimeAdapter.java
│   │   │       ├── websocket/
│   │   │       │   ├── DeviceWebSocketHandler.java
│   │   │       │   └── WebSocketConfig.java
│   │   │       ├── service/
│   │   │       │   └── SttTaskManager.java
│   │   │       ├── SttApplication.java
│   │   │       └── config/
│   │   │           └── AppConfig.java
│   │   ├── resources/
│   │   │   ├── application.yml
│   │   │   └── logback-spring.xml
│   │   └── test/
│   └── README.md