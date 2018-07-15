package readDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package readDatabase
 * @date 2018/7/14 15:57
 * @since api1.0
 */
public class testMain {
    public static void main(String a[]){
        String str = "        dockey[CN98126546@CN1115637C@20030723][PDF:PDF:CN98126546.pdf@raw/fullimg/CN/SQ:1]";
        String pattern = "(?<=PDF:PDF:).*?(?=:)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        if(m.find()){
            System.out.println(m.group());
        }
    }
}
