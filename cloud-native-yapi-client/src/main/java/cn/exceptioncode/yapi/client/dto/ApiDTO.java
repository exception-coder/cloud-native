package cn.exceptioncode.yapi.client.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiDTO {


    private String token;
    /**
     *
     * 接口标题
     */
    private String title;
    /**
     *
     * 品类id
     */
    private String catid;
    /**
     *
     * 请求路径
     */
    private String path;
    /**
     *
     *
     * 接口状态
     */
    private String status = "undone";
    /**
     *
     * 返回数据类型 枚举：json，raw
     */
    private String res_body_type;
    /**
     *
     * 返回数据
     */
    private String res_body;
    /**
     *
     *
     */
    private boolean switch_notice = true;

    /**
     *
     * 如果为null yapi-server 异常
     * {
     *     "errcode": 400,
     *     "errmsg": "请求参数 data.message 不应少于 1 个字符",
     *     "data": null
     * }
     */
    private String message="default_message";
    /**
     *
     * 备注
     */
    private String desc;
    /**
     *
     * 请求 method
     */
    private String method;

    /**
     *
     * 响应参数是否json
     */
    private boolean res_body_is_json_schema = true;

    /**
     *
     * 请求参数是否json
     */
    private boolean req_body_is_json_schema = true;
    /**
     *
     * 请求体参数
     */
    private String req_body_other ;
    /**
     *
     * url请求参数
     *
     */
    private List<?> req_query = new ArrayList<>(3);
    /**
     *
     * 请求头参数
     */
    private List<?> req_headers = new ArrayList<>(10);
    /**
     *
     *
     * 请求 form 参数
     */
    private List<?> req_body_form = new ArrayList<>(3);
    /**
     *
     * GET 请求参数
     */
    private List<?> req_params = new ArrayList<>(3);

}
