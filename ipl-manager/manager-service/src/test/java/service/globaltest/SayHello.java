package service.globaltest;

import ipl.manager.mapper.Say;

/**
 * @author 王海
 * @package service.globaltest;
 * @description 测试manager下的各个模块是否能够正常互相调用
 * @date 2018/3/14 19:20
 * @version V1.0
 */
public class SayHello {
    private String name;
    public SayHello(String name){
        this.name=name;
    }
    public void sayHello(SayHello sayHello, Say say){
        System.out.println("SayHello name is " + sayHello.name);
        System.out.println("say name is " + say.name);
    }

    public String getName(){
        return this.name;
    }

}
