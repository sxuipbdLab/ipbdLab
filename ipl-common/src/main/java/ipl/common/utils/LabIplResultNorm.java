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
//    private HashMap<String, String> backinfo = new HashMap<>();
    private String backinfo;

    public static LabIplResultNorm build(String status, String msg, String backinfo) {
        return new LabIplResultNorm(status, msg, backinfo);
    }

    public static LabIplResultNorm build(String status, String msg) {
        return new LabIplResultNorm(status, msg, null);
    }

    /**
     * 请求成功时（200）
     *
     * @param backinfo false代表被验证的数据不可使用，等，true代表被验证的数据可以使用，等。
     * @return
     */
    public static LabIplResultNorm ok(String backinfo) {
        return new LabIplResultNorm(backinfo);
    }

    public static LabIplResultNorm ok() {
        return new LabIplResultNorm(null);
    }

    public LabIplResultNorm() {
    }

    public LabIplResultNorm(String backinfo) {
        this.status = "200";
        this.msg = "OK";
        this.backinfo = backinfo;
    }

    public LabIplResultNorm(String status, String msg, String backinfo) {
        this.status = status;
        this.msg = msg;
        this.backinfo = backinfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
