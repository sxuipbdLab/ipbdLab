package ipl.common.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.common.utils
 * @date 2018/3/16 22:35
 * @since api1.0
 */
public class JacksonUtil {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串
     *
     * @param obj 需要转换的对象
     * @return 该对象对应的Json格式数据
     * @throws IOException
     */
    public static String bean2Json(Object obj) {
        StringWriter sw = null;
        try {
            sw = new StringWriter();
            JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
            MAPPER.writeValue(gen, obj);
            gen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonStr  json结果集
     * @param objClass 要转换的对象
     * @param <T>
     * @return 对应的对象
     * @throws IOException
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass)
            throws IOException {
        T t = MAPPER.readValue(jsonStr, objClass);
        return t;
    }
}
