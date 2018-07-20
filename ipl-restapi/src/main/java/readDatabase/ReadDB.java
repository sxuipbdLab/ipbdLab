package readDatabase;

import ipl.restapi.controller.Analog_landing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URLEncoder;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package readDatabase
 * @date 2018/7/16 19:49
 * @since api1.0
 */
public class ReadDB {

    static String filePath = "H://result_Computer_BigData.txt";
    static String errorPath = "H://error_Computer_BigData.txt";
    static File file = new File(filePath);
    static File file_error = new File(errorPath);
    static PrintStream ps;
    static PrintStream ps_error;

    static public void begin(){
        try {
            ps = new PrintStream(new FileOutputStream(file));
            ps_error = new PrintStream(new FileOutputStream(file_error));
        } catch (FileNotFoundException e) {
            System.out.println("error!");
            e.printStackTrace();
        }
    }

    private static Analog_landing analog_landing;

    public ReadDB() throws FileNotFoundException {
    }

    public static String search(String searchStr, int dp, String pn){
        /**
         * 更换检索串编码
         */
        String f1 = "AN,PN,PD";
        try {
            searchStr = URLEncoder.encode(searchStr,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String dataUrl = "http://172.21.201.131:8200/search?dp=" + dp + "&pn=" + pn + "&fl=" + f1 + "&q=" +searchStr;
        return analog_landing.ConnectTheNet(dataUrl);
    }
    /**
     * 获取全文
     * @param keyword 检索全文所需要的关键字
     */
    public static void getFullText(String keyword[]) throws IOException {
        String fk = "TI,AB,CLM,FT,PA,IPC,LS,LSE,AN,PN,UPC,USPC,ECLA,JPC,DC,AU,AD,PD,PR,ADDR,PC,CNLX,CTN,AGC,AGT,LSD,CDN,CTD,CDD,PFD,PF,ZYFT,QWFT,LSO,LSR,YXZT,WXZT,ZSZT,PCTF,IAN,IPN,XGD,CLMD";
        String dataUrl = "http://172.21.201.131/search/pub/ApiDocinfo?fk=" + fk + "&dk=[{\"DCK\":\""+keyword[1]+"@"+keyword[0]+"@"+keyword[2]+"\",\"MID\":\""+keyword[3]+"\"}]";

//        System.out.println(analog_landing.ConnectTheNet(dataUrl));

        String result = analog_landing.ConnectTheNet(dataUrl);
        ps.append(result + "\n");
    }

    public static int readJson(String json_Str,int dp, String keyWordList[]) throws JSONException {
        int size = -1;
        JSONObject json_Data;
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(json_Str);
            size = jsonObject.getInt("FOUNDNUM");
            JSONArray object = jsonObject.getJSONArray("RESULT");
            for(int i = 0;i<object.length();i++){
                json_Data = object.getJSONObject(i);
                keyWordList[0] = json_Data.getString("PN");
                keyWordList[1] = json_Data.getString("AN");
                keyWordList[2] = json_Data.getString("PD");
                keyWordList[3] = json_Data.getString("mid");
            }
        }catch (JSONException e){
            ps_error.append("error: ");
            for(int i = 0;i < 4;i++){
                ps_error.append(keyWordList[i] + " ");
            }
            ps_error.append("\n");
        }
        return size;
    }

    public static void main(String a[]) throws JSONException, IOException {
        long startTime = System.currentTimeMillis();

        begin();

        String begintime = "20160101";
        String endtime = "20160131";
        String pn = "1";
        String[] KeyWordList = new String[4];
        String dataStr = "((AD>=" + begintime + ") AND (AD<=" + endtime + "))";
        String searchStr = "计算机 AND 大数据";
        int size = readJson(search(searchStr,1,pn),1,KeyWordList);
        System.out.println("size    " + size);
        for(int j = 1;j <= size;j++){
            if(size == -1){
                continue;
            }
            readJson(search(dataStr,j,pn),1,KeyWordList);
            getFullText(KeyWordList);
            System.out.print(j + " ");
            for(int i = 0;i < 4;i++){
                System.out.print(KeyWordList[i] + " ");
            }
            System.out.println();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

    }

}
