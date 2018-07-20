package readDatabase;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package readDatabase
 * @date 2018/7/17 17:22
 * @since api1.0
 */
public class testJVM {
    public static void main(String[] a){
        Runtime run = Runtime.getRuntime();

        long max = run.maxMemory()/(1024*1024);

        long total = run.totalMemory()/(1024*1024);

        long free = run.freeMemory()/(1024*1024);

        long usable = max - total + free;

        System.out.println("最大内存 = " + max);
        System.out.println("已分配内存 = " + total);
        System.out.println("已分配内存中的剩余空间 = " + free);
        System.out.println("最大可用内存 = " + usable);
    }
}
