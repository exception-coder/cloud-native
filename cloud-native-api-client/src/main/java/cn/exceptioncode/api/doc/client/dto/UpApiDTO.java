package cn.exceptioncode.api.doc.client.dto;

import java.util.List;

/**
 *
 * @author zhangkai
 *
 */
public class UpApiDTO {

    /**
     * req_query : [{"name":"parameter1","required":"1","example":"1","desc":"请求参数1"},{"name":"paramete2","required":"0","example":"2","desc":"请求参数2"}]
     * req_headers : []
     * title : yapi测试接口
     * catid : 11
     * path : /yapi/test
     * tag : []
     * status : undone
     * req_body_is_json_schema : true
     * res_body_is_json_schema : true
     * res_body_type : json
     * res_body : {"$schema":"http://json-schema.org/draft-04/schema#","type":"object","properties":{"code":{"type":"number","description":"响应码"},"message":{"type":"string","description":"响应信息"}},"description":"响应对象"}
     * switch_notice : true
     * api_opened : false
     * req_body_form : []
     * desc :
     * markdown :
     * method : GET
     * req_params : []
     * id : 11
     */

    private String title;
    private String catid;
    private String path;
    private String status;
    private boolean req_body_is_json_schema;
    private boolean res_body_is_json_schema;
    private String res_body_type;
    private String res_body;
    private boolean switch_notice;
    private boolean api_opened;
    private String desc;
    private String markdown;
    private String method;
    private String id;
    private List<ReqQueryBean> req_query;
    private List<?> req_headers;
    private List<?> tag;
    private List<?> req_body_form;
    private List<?> req_params;

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

    public boolean isReq_body_is_json_schema() {
        return req_body_is_json_schema;
    }

    public void setReq_body_is_json_schema(boolean req_body_is_json_schema) {
        this.req_body_is_json_schema = req_body_is_json_schema;
    }

    public boolean isRes_body_is_json_schema() {
        return res_body_is_json_schema;
    }

    public void setRes_body_is_json_schema(boolean res_body_is_json_schema) {
        this.res_body_is_json_schema = res_body_is_json_schema;
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

    public boolean isApi_opened() {
        return api_opened;
    }

    public void setApi_opened(boolean api_opened) {
        this.api_opened = api_opened;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ReqQueryBean> getReq_query() {
        return req_query;
    }

    public void setReq_query(List<ReqQueryBean> req_query) {
        this.req_query = req_query;
    }

    public List<?> getReq_headers() {
        return req_headers;
    }

    public void setReq_headers(List<?> req_headers) {
        this.req_headers = req_headers;
    }

    public List<?> getTag() {
        return tag;
    }

    public void setTag(List<?> tag) {
        this.tag = tag;
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

    public static class ReqQueryBean {
        /**
         * name : parameter1
         * required : 1
         * example : 1
         * desc : 请求参数1
         */

        private String name;
        private String required;
        private String example;
        private String desc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRequired() {
            return required;
        }

        public void setRequired(String required) {
            this.required = required;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
