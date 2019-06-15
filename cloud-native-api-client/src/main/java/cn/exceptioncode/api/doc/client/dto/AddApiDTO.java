package cn.exceptioncode.api.doc.client.dto;

import java.util.List;

public class AddApiDTO {


    private String token = "b03d88c3cb8c688fdd647a7983de5ed6e6b6d375982633c0cead6fb7f8e8f6dc";
    /**
     *
     * 接口标题
     */
    private String title;
    /**
     *
     * 品类id
     */
    private String catid = "21";
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
    private String status;
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
    private boolean switch_notice;
    private String message;
    private String desc;
    /**
     *
     * 请求 method
     */
    private String method;
    private List<?> req_query;
    /**
     *
     * 请求头
     */
    private List<ReqHeadersBean> req_headers;
    /**
     *
     *
     * 请求 form 参数
     */
    private List<?> req_body_form;
    /**
     *
     * GET 请求参数
     */
    private List<?> req_params;

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

    public List<ReqHeadersBean> getReq_headers() {
        return req_headers;
    }

    public void setReq_headers(List<ReqHeadersBean> req_headers) {
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

    public static class ReqHeadersBean {
        /**
         * name : Content-Type
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
