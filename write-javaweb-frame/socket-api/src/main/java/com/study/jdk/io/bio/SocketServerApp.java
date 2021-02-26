package com.study.jdk.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyao
 * @date 2021/2/18 10:54
 * @description
 */
public class SocketServerApp {

    static Map<String, Object> bean;

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5
            , 5
            , 60
            , TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)
            , new ThreadPoolExecutor.AbortPolicy());

    static {
        bean = new HashMap<>();
        bean.put("TcpService", new TcpServiceImpl());
    }

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8080);
        System.out.println("socket server started......");
        while (true) {
            Socket client = server.accept();
            executor.execute(() -> handleSocketClient(client));
        }

    }

    private static void handleSocketClient(Socket client)  {
        try {
            client.setSoTimeout(3000);
            client.setKeepAlive(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                InputStream inputStream = client.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                String methodName = ois.readUTF();
                String intefaceName = ois.readUTF();
                Object[] methodParams = (Object[]) ois.readObject();//参数
                Class<?>[] classes = (Class<?>[]) ois.readObject();//参数类型
                if (bean.containsKey(intefaceName)) {
                    Object serviceImpl = bean.get(intefaceName);
                    Method method = serviceImpl.getClass().getMethod(methodName, classes);
                    Object o = method.invoke(serviceImpl, methodParams);
                    System.out.println(o.toString());
                    ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                    oos.writeObject(o);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
