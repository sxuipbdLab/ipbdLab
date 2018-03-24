package ipl.common.utils;

/**
 * <p>Descirption:自定义的http响应结构</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.common.utils
 * @date 2018/3/20 11:10
 * @since api1.0
 */
public class LabIplResultNorm {
    /**
     * 响应业务状态
     */
    private String status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Boolean backinfo;

    /**
     *   请求数据
     */
    private String data;

    public static LabIplResultNorm build(String status, String msg, Boolean backinfo, String data) {
        return new LabIplResultNorm(status, msg, backinfo, data);
    }

    public LabIplResultNorm() {
    }

    public LabIplResultNorm(String status, String msg, Boolean backinfo, String data) {
        this.status = status;
        this.msg = msg;
        this.backinfo = backinfo;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getBackinfo() {
        return backinfo;
    }

    public void setBackinfo(Boolean backinfo) {
        this.backinfo = backinfo;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}
