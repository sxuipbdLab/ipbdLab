package readDatabase;

import ipl.restapi.controller.Analog_landing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package readDatabase
 * @date 2018/7/7 11:34
 * @since api1.0
 */
public class readUrl {

    private static Analog_landing analog_landing;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    static int beginData[] = new int[]{2004,12,1};
    static int endData[] = new int[]{2004,12,1};

    String first = beginData[0] + "-" + beginData[1] + "-" + beginData[2];
    String second = endData[0] + "-" + endData[1] + "-" + endData[2];

    static Date seconddate;
    static Date firstdate;
    {
        try {
            firstdate = format.parse(first);
            seconddate = format.parse(second);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //static int arraysize = daysBetween(firstdate,seconddate);

    /**
     * 用来存放PN，AN，PD，MID
     */
    static String[][] keyWordList = new String[100000][4];

    /**
     * 计算两个日期之间的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1,Date date2){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 检索
     * @param searchStr 检索串
     * @param dp 页码
     * @param pn 每页显示条目数
     * @param f1 返回结果字段
     */
    public static String search(String searchStr, int dp, String pn, String f1){

        /**
         * 更换检索串编码
         */
        try {
            searchStr = URLEncoder.encode(searchStr,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String dataUrl = "http://172.21.201.131:8200/search?dp=" + dp + "&pn=" + pn + "&fl=" + f1 + "&q=" +searchStr;
        return analog_landing.ConnectTheNet(dataUrl);
    }

    /**
     * 解析JSON串
     */
    public static int readJson(String json_Str,int dp) throws JSONException {
        int size;
        int m = ( dp - 1 ) * 10;
        String keyWord[] = new String[4];
        JSONObject json_Data;
        JSONObject jsonObject = new JSONObject(json_Str);
        size = jsonObject.getInt("FOUNDNUM");
        JSONArray object = jsonObject.getJSONArray("RESULT");
        for(int i = 0;i<object.length();i++){
            json_Data = object.getJSONObject(i);
            keyWordList[m][0] = json_Data.getString("PN");
            keyWordList[m][1] = json_Data.getString("AN");
            keyWordList[m][2] = json_Data.getString("PD");
            keyWordList[m][3] = json_Data.getString("mid");
            m++;
        }

        return size;
    }
    /**
     * 遍历日期范围内的所有数据
     */
    public static int createDate(int dp) throws JSONException {

        int size = 0;
        String f1 = "TI,AB,PA,LS,AN,PN,AD,PD,ZYFT";
        String pn = "10";
        String dataStr = "";

        Calendar start = Calendar.getInstance();
        start.set(beginData[0], beginData[1], beginData[2]);
        Long startTIme = start.getTimeInMillis();

        Calendar end = Calendar.getInstance();
        end.set(endData[0], endData[1], endData[2]);
        Long endTime = end.getTimeInMillis();

        Long oneDay = 1000 * 60 * 60 * 24l;

        Long time = startTIme;
        while (time <= endTime) {
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            dataStr = "((AD>=" + df.format(d) + ") AND (AD<=" + df.format(d) + "))";
            size = readJson(search(dataStr, dp, pn, f1),dp);
            time += oneDay;
        }


        return size;
    }

    /**
     * 获取全文
     * @param keyword 检索全文所需要的关键字
     */
    public static void getFullText(String keyword[]){
        String fk = "TI,AB,CLM,FT,PA,IPC,AN,PN,AU,AD,PD,PR,ADDR,PC,AGC,AGT,QWFT,PCTF,IAN,IPN";
        String dataUrl = "http://172.21.201.131/search/pub/ApiDocinfo?fk=" + fk + "&dk=[{\"DCK\":\""+keyword[1]+"@"+keyword[0]+"@"+keyword[2]+"\",\"MID\":\""+keyword[3]+"\"}]";
        System.out.println(analog_landing.ConnectTheNet(dataUrl));
    }

    public static void main(String[] args) throws JSONException {
        //获得总条目数
        int FOUNDNUM = createDate(1);
        System.out.println("size  " + FOUNDNUM);
        for(int j = 1;j < 1;j++){
            createDate(j+1);
        }

        System.out.println("keywordLength  " + keyWordList.length);

        for(int i = 0; i < 1;i++){
            getFullText(keyWordList[i]);
        }
    }
}
