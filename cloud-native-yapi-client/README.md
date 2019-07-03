# yapi-client

## quick start

- ### `pom.xml`文件添加依赖
```xml
     <dependency>
            <groupId>cn.exception-code</groupId>
            <artifactId>cloud-native-yapi-client</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

```
- ### `properties`文件添加 `yapi-server` 相关信息

```yaml

cn:
  exceptioncode:
    yapi:
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
- ### 使用示例
```java

@RestController
public class WebController {


    @PutMapping(value = "/test/javadoc/{foo}", name = "api-client调试使用接口")
    public
    BaseResponse<DogDTO>
//    Mono<BaseResponse<DogDTO>>
    javadoc(@RequestParam(name = "userName", required = false) String userName,
            @ParamDesc(example = "foo_example", desc = "foo_desc") @PathVariable(name = "foo") String foo,
            @ParamDesc(example = "bar_example", desc = "bar_desc") @RequestParam(name = "bar", required = false) String bar,
            String code,
            @RequestBody DogDTO dogDTO) {
        return BaseResponse.success(new DogDTO(foo, 1, bar));
//        return Mono.just(BaseResponse.success(new DogDTO()));
    }

}


@Data
public class BaseResponse<T> implements Serializable {

    @ParamDesc(desc = "响应码",example = "200")
    private int code;

    @ParamDesc(desc = "响应信息描述",example = "success")
    private String message;

    @ParamDesc(desc = "响应信息描述",example = "success",required = false)
    private T data;

 // ......
}

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class DogDTO {

    @ParamDesc(desc = "呢称",example = "jock")
    private String name;

    @ParamDesc(desc = "年龄",example = "1",required = false)
    private Integer age;

    String color;

}

```
- ### 对应注解属性描述 (扩展注解ParamDesc可用于方法参数以及类属性上)

| 注解           | 属性     | 描述     |
| -------- | -------- | -------- |
| RequestMapping | method   | 请求方法 |
|                | value    | 接口路径 |
|                | name     | 接口名称 |
|                |          |          |
| PathVariable   | name     | 参数名称 |
|                | required | 是否必填 |
|                |          |          |
| RequestParam   | name     | 参数名称 |
|                | required | 是否必填 |
|                |          |          |
| RequestBody    | required | 是否必填 |
|                |          |          |
| ParamDesc      | desc     | 备注     |
|                | example  | 示例     |
|                | required | 是否必填 |

