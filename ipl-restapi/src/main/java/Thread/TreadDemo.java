package Thread;

import org.json.JSONException;

import java.io.IOException;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package Thread
 * @date 2018/7/17 9:04
 * @since api1.0
 */
public class TreadDemo extends Thread{

    public String dataStr;
    public int size;
    public Spider spider;
    public String pn = "1";
    public String[] KeyWordList = new String[4];

    public TreadDemo(String dataStr, Spider spider){
        this.dataStr = dataStr;
        this.spider = spider;
    }
    @Override
    public void run() {
        spider.begin();
        try {
            this.size = spider.readJson(spider.search(this.dataStr,1,pn),1,this.KeyWordList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int j = 1; j <= this.size;j++){
            if(size == -1){
                continue;
            }
            try {
                spider.readJson(spider.search(this.dataStr,j,this.pn),1,this.KeyWordList);
                spider.getFullText(this.KeyWordList);
//                System.out.print(getName() + "  "+j + " ");
//                for(int i = 0;i < 4;i++){
//                    System.out.print(KeyWordList[i] + " ");
//                }
//                System.out.println();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] a){

        System.out.println("begin.........");

        String data;
        String file_Path;
        String error_Path;
        int w;
        Thread[] threads = new Thread[12];

        for(int i = Integer.parseInt(a[0]); i<=Integer.parseInt(a[1]);i++){
            w = i - Integer.parseInt(a[0]);
            data = "((AD>=" + i + "0101) AND (AD<=" + i + "1231))";
            file_Path = "/usr/lunwen_txt/result/result_" + i + ".json";
            error_Path = "/usr/lunwen_txt/result_error/error_" + i + ".json";
            threads[w] = new TreadDemo(data,new Spider(file_Path,error_Path));
            threads[w].setName(Integer.toString(i));
            threads[w].start();
        }
    }
}
