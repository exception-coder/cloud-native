package cn.exceptioncode.yapi.doc.client.dto;

import java.util.ArrayList;
import java.util.List;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRes_body_type() {
        return res_body_type;
    }

    public void setRes_body_type(String res_body_type) {
        this.res_body_type = res_body_type;
    }

    public String getRes_body() {
        return res_body;
    }

    public void setRes_body(String res_body) {
        this.res_body = res_body;
    }

    public boolean isSwitch_notice() {
        return switch_notice;
    }

    public void setSwitch_notice(boolean switch_notice) {
        this.switch_notice = switch_notice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<?> getReq_query() {
        return req_query;
    }

    public void setReq_query(List<?> req_query) {
        this.req_query = req_query;
    }

    public List<?> getReq_headers() {
        return req_headers;
    }

    public void setReq_headers(List<?> req_headers) {
        this.req_headers = req_headers;
    }

    public List<?> getReq_body_form() {
        return req_body_form;
    }

    public void setReq_body_form(List<?> req_body_form) {
        this.req_body_form = req_body_form;
    }

    public List<?> getReq_params() {
        return req_params;
    }

    public void setReq_params(List<?> req_params) {
        this.req_params = req_params;
    }

    public boolean getReq_body_is_json_schema() {
        return req_body_is_json_schema;
    }

    public void setReq_body_is_json_schema(Boolean req_body_is_json_schema) {
        this.req_body_is_json_schema = req_body_is_json_schema;
    }

    public String getReq_body_other() {
        return req_body_other;
    }

    public void setReq_body_other(String req_body_other) {
        this.req_body_other = req_body_other;
    }

    public boolean isRes_body_is_json_schema() {
        return res_body_is_json_schema;
    }

    public void setRes_body_is_json_schema(boolean res_body_is_json_schema) {
        this.res_body_is_json_schema = res_body_is_json_schema;
    }

    public boolean isReq_body_is_json_schema() {
        return req_body_is_json_schema;
    }

    public void setReq_body_is_json_schema(boolean req_body_is_json_schema) {
        this.req_body_is_json_schema = req_body_is_json_schema;
    }
}
