package ipl.common.utils;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.common.utils
 * @date 2018/3/31 21:27
 * @since api1.0
 */
public class ResultObjectFromApi {
    private int code;
    private int count;
    private Object data;

    public static ResultObjectFromApi build(int code,int count,Object data) {
        return new ResultObjectFromApi(code,count,data);
    }

    public ResultObjectFromApi() {

    }

    public ResultObjectFromApi(int code,int count,Object data) {
        this.code = code;
        this.count = count;
        this.data = data;

    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
