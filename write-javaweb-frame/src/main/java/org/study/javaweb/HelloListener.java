package org.study.javaweb;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

/**
 * @author chenyao
 * @date 2021/2/19 16:44
 * @description
 */
@WebListener
public class HelloListener implements ServletContextListener
        , ServletContextAttributeListener
        , ServletRequestListener
        , ServletRequestAttributeListener
        , AsyncListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListener init");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContextListener Destroyed");
    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {

    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {

    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {

    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {

    }

    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {

    }

    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {

    }

    @Override
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {

    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {

    }
}
