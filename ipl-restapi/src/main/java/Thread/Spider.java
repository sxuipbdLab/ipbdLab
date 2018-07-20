package Thread;

import com.jcraft.jsch.JSchException;
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
 * @package Thread
 * @date 2018/7/17 8:56
 * @since api1.0
 */
public class Spider {
    public String file_Path;
    public String error_Path;
    public PrintStream ps;
    public PrintStream ps_error;
    public Analog_landing analog_landing;
    public Spider(String file_Path, String error_Path){
        this.file_Path = file_Path;
        this.error_Path = error_Path;
    }
    public void begin(){
        File file = new File(this.file_Path);
        File file_error = new File(this.error_Path);
        try{
            this.ps = new PrintStream(new FileOutputStream(file));
            this.ps_error = new PrintStream(new FileOutputStream(file_error));
        } catch (FileNotFoundException e) {
            System.out.println("error!");
            e.printStackTrace();
        }
    }

    public String search(String searchStr, int dp, String pn){
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
        return this.analog_landing.ConnectTheNet(dataUrl);
    }
    /**
     * 获取全文
     * @param keyword 检索全文所需要的关键字
     */
    public void getFullText(String keyword[]) throws IOException {
        String fk = "TI,AB,CLM,FT,PA,IPC,LS,LSE,AN,PN,UPC,USPC,ECLA,JPC,DC,AU,AD,PD,PR,ADDR,PC,CNLX,CTN,AGC,AGT,LSD,CDN,CTD,CDD,PFD,PF,ZYFT,QWFT,LSO,LSR,YXZT,WXZT,ZSZT,PCTF,IAN,IPN,XGD,CLMD";
        String dataUrl = "http://172.21.201.131/search/pub/ApiDocinfo?fk=" + fk + "&dk=[{\"DCK\":\""+keyword[1]+"@"+keyword[0]+"@"+keyword[2]+"\",\"MID\":\""+keyword[3]+"\"}]";

        String shell;
//        System.out.println(analog_landing.ConnectTheNet(dataUrl));

        String result = this.analog_landing.ConnectTheNet(dataUrl);
        JSONObject jsonObject;
        JSONObject newJson;
        String dockey = "default";
        try {
            jsonObject = new JSONObject(result);
            newJson = jsonObject.getJSONObject("mesg").getJSONObject("result");
            dockey = jsonObject.getJSONObject("mesg").getString("dockey");
            newJson.put("dockey",dockey);
            shell = "curl -XPUT '172.21.201.141:9200/intell_property/text_info/" + dockey + "?pretty&pretty' -d'" + newJson + "' -H \"Content-Type: application/json\"";
            analog_landing.exeCommand("172.21.201.141","sxdx2018",shell);
            this.ps.append(newJson + "\n");
        } catch (JSONException e) {
            this.ps_error.append("error: " + result + "\n");
            e.printStackTrace();
        } catch (JSchException e) {
            System.out.println("******** Shell error ********" + dockey);
            e.printStackTrace();
        }
    }

    public int readJson(String json_Str,int dp, String keyWordList[]) throws JSONException {
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
            return size;
        }catch (JSONException e){
            this.ps_error.append("error: ");
            for(int i = 0;i < 4;i++){
                this.ps_error.append(keyWordList[i] + " ");
            }
            this.ps_error.append("\n");
        }
        return size;
    }

}
