package readDatabase;

import ipl.restapi.controller.Analog_landing;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> Desciption
 * 程序运行顺序：
 * 1、structureDataRange()接受6个时间参数，构造数据的日期范围条件，用于2；structureDataRange()只运行一次
 * 2、在search()中，获取当前页所有数据，我们需要"多调用"一次该方法，以获得该时间段的总数据量，方便求出pageSize；
 * 3、对pageSize+时间段进行请求，获得包含各种字段的Json数据，需要解析该数据，之后放入HDFS；
 * 4、根据parseJsonAndCount()中填充好的keyWordList，获取全文数据，之后放入HDFS
 * 5、分了多少页，就执行多少次2、3、4。
 * </P>
 *
 * @author 原之安，王海
 * @version V1.0
 * @package readDatabase
 * @date 2018/7/7 11:34
 * @date 2018/7/8 19:50
 * @since api1.0
 */
public class GetAllData {
    private static final String I_WANT_THIS_FIELDS = "TI,AB,PA,LS,AN,PN,AD,PD,ZYFT";
    private static final int PER_PAGE_NUM = 50;
    /**
     * 用来存放PN，AN，PD，MID,在获取全文数据时使用
     * TODO：换数据结构(无限扩容的),避免溢出
     */
    static String[][] keyWordList = new String[100000][4];
    // List<String[]> list = new ArrayList<>();

    /**
     * 计算两个日期之间的天数
     *
     * @param date1 开始时间
     * @param date2 结束时间
     * @return 相差天数
     */
    public int daysBetween(LocalDate date1, LocalDate date2) {
        Period period = new Period(date1, date2, PeriodType.days());
        return period.getDays();
    }

    /**
     * 检索，获取当前页所有数据
     *
     * @param dataRange 检索数据的时间段
     * @param dp        页码
     */
    public String search(String dataRange, int dp) {

        /*
         * 更换检索串编码
         */
        try {
            dataRange = URLEncoder.encode(dataRange, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String searchUrl = "http://172.21.201.131:8200/search?dp=" + dp + "&pn=" + PER_PAGE_NUM + "&fl=" + I_WANT_THIS_FIELDS + "&q=" + dataRange;
        System.out.println(searchUrl);
        return Analog_landing.ConnectTheNet(searchUrl);
    }

    /**
     * 解析JSON串
     */
    public void parseJsonPrepareForFullText(String jsonStr, int dp) throws JSONException {
        // jsonStr中，第m条数据
        int m = (dp - 1) * GetAllData.PER_PAGE_NUM;
        JSONObject jsonData;
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray object = jsonObject.getJSONArray("RESULT");
        for (int i = 0; i < object.length(); i++) {
            System.out.println("====" + object.length());
            m += i;
            jsonData = object.getJSONObject(i);
            keyWordList[m][0] = jsonData.getString("PN");
            keyWordList[m][1] = jsonData.getString("AN");
            keyWordList[m][2] = jsonData.getString("PD");
            keyWordList[m][3] = jsonData.getString("mid");
        }
    }

    public int count(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.getInt("FOUNDNUM");
    }

    /**
     * 为查询接口构造数据范围条件
     * 为了避免直接输入参数20120115这样的日期串时输错，故采用6个参数的形式，先生成合法日期，再转换
     */
    public String structureDataRange(int... args) throws JSONException {
        // 需要配置JVM参数：-ea
        assert (args.length == 6) : "期望获得6个时间参数，实际获得：\t" + args.length;

        LocalDate start = new LocalDate(args[0], args[1], args[2]);
        LocalDate end = new LocalDate(args[3], args[4], args[5]);

        return "((AD>=" + start.toString().replaceAll("-", "") + ") AND (AD<=" + end.toString().replaceAll("-", "") + "))";
    }

    public Object getFullText(String keyword[]) {
        String fk = "TI,AB,CLM,FT,PA,IPC,AN,PN,AU,AD,PD,PR,ADDR,PC,AGC,AGT,QWFT,PCTF,IAN,IPN";
        String dataUrl = "http://172.21.201.131/search/pub/ApiDocinfo?fk=" + fk + "&dk=[{\"DCK\":\"" + keyword[1] + "@" + keyword[0] + "@" + keyword[2] + "\",\"MID\":\"" + keyword[3] + "\"}]";
        System.out.println(dataUrl);
        // TODO:拒绝参数为null
        return Analog_landing.ConnectTheNet(dataUrl);
    }

    public static void main(String[] args) throws JSONException {
        GetAllData gAlDt = new GetAllData();
        String dataRange = gAlDt.structureDataRange(2017, 12, 14, 2017, 12, 15);
        int FOUNDNUM = gAlDt.count(gAlDt.search(dataRange, 1));
        System.out.println("size  " + FOUNDNUM);
        // 分了多少页，就执行多少次2、3、4
        // TODO:是否需要向上取整
        for (int j = 0; j < FOUNDNUM / GetAllData.PER_PAGE_NUM; j++) {
            String jsonStr = gAlDt.search(dataRange, j);
            // TODO:该jsonStr需要被解析传入kafka，然后导入HDFS
            gAlDt.parseJsonPrepareForFullText(jsonStr,j);
            System.out.println(keyWordList.length);
//            System.out.println(gAlDt.getFullText(keyWordList[10]));
        }
        System.out.println(gAlDt.getFullText(keyWordList[10]));
    }
}
