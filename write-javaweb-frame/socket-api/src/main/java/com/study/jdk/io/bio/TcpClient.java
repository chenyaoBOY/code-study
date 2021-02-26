package com.study.jdk.io.bio;



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 *  目的：调用server上的接口方法 getPerson 传递参数  返回Person对象
 *      实现RPC远程调用
 */
public class TcpClient {

    public static void main(String[] args) throws Exception{

        //Student getPerson(String name, Integer age);
        //定义参数
        Object[] params = {"陈瑶",18};
        Method method = TcpService.class.getMethod("getPerson", String.class, Integer.class);

        //连接服务
        Socket client = new Socket("localhost", 8080);


        while (true){
            Thread.sleep(2000);

            OutputStream os = client.getOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeUTF("getPerson");//方法名称
            oos.writeUTF("TcpService");//接口名称
            oos.writeObject(params);//接口参数
            oos.writeObject(method.getParameterTypes());//参数类型
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            Object o = ois.readObject();
            if(o instanceof Person){
                Person p =(Person)o;
                System.out.println("name="+p.name+"--age="+p.age);
            }
        }

    }
}
