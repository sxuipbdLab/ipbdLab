package readDatabase;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * GetAllData Tester.
 *
 * @author <wang>
 * @version 1.0
 * @since <pre>07/09/2018</pre>
 */
public class GetAllDataTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: daysBetween(LocalDate date1, LocalDate date2)
     */
    @Test
    public void testDaysBetween() throws Exception {
//TODO:
    }

    /**
     * Method: search(String dataRange, int dp)
     */
    @Test
    public void testSearch() throws Exception {
//TODO:
    }

    /**
     * Method: parseJsonPrepareForFullText(String jsonStr, int dp)
     */
    @Test
    public void testParseJsonPrepareForFullText() throws Exception {
//TODO:
    }

    /**
     * Method: count(String jsonStr)
     */
    @Test
    public void testCount() throws Exception {
//TODO:
    }

    /**
     * Method: structureDataRange(int... args)
     */
    @Test
    public void testStructureDataRange() throws Exception {
//TODO:
    }

    /**
     * Method: getFullText(String keyword[])
     */
    @Test
    public void testGetFullText() throws Exception {
//TODO:
    }
    /*public static void main(String[] args) throws JSONException {
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
    }*/

} 
