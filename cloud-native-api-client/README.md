# yapi-client

## quick start

- `pom.xml`文件添加依赖
```xml
     <dependency>
            <groupId>cn.exception-code</groupId>
            <artifactId>cloud-native-api-client</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

```
- `properties`文件添加 `yapi-server` 相关信息

```yaml

cn:
  iotechina:
    api:
      code:
        doc:
        # 组别
          api-catid: 11
        # yapi 客户端地址
          api-server: 192.168.0.104:3000
        # 工程 token yapi-server 创建的每个工程都有其对应的 token
          api-token: 94677a30c3e3fa0a19609c9629b13aa0f46b91a8b9bfd45e482235751d615b22
        # yapi 用户名
          api-user-name: 425485346@qq.com
        # yapi 密码
          api-user-password: ymfe.org
        # 是否启动的那个 yapi-client 默认 true
          enable: true

```