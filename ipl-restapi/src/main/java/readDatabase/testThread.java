package readDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package readDatabase
 * @date 2018/7/17 17:25
 * @since api1.0
 */
public class testThread {
    public static void main(String[] args) throws JSONException {
        String s = "{\"name\":\"arno\"}";
        JSONObject jsonObject = new JSONObject(s);
        String out = "out==" + jsonObject;
        System.out.println(out);
    }
}

