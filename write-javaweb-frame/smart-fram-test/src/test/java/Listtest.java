import org.study.frame.proxy.HelloImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenyao
 * @date 2021/1/20 15:31
 * @description
 */
public class Listtest {

    public static void main(String[] args) {
        List<HelloImpl> list= new ArrayList<>();
        list.add(new HelloImpl("chenyao"));
        list.add(new HelloImpl("zhangdi"));
        list.add(new HelloImpl("jiafu"));

        List<HelloImpl> collect = list.stream().filter(val -> val.getName().equals("chenyao")).collect(Collectors.toList());
        HelloImpl hello = collect.get(0);
        hello.setName("asdlkghadsklg");
        System.out.println(1);
    }
}
