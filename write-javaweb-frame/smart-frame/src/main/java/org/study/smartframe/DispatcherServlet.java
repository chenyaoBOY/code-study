package org.study.smartframe;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.study.smartframe.entity.ModelData;
import org.study.smartframe.entity.Param;
import org.study.smartframe.entity.RequestHandler;
import org.study.smartframe.entity.View;
import org.study.smartframe.load.BeanParser;
import org.study.smartframe.load.ControllerParser;
import org.study.smartframe.util.ConfigUtil;
import org.study.smartframe.util.IOUtil;
import org.study.smartframe.util.ReflectUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/19 13:40
 * @description
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
@Slf4j
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        FramWorkParser.initSmartFrameWork();
        ServletContext servletContext = config.getServletContext();
        /**
         * 设置jsp路径映射
         */
        ServletRegistration jsp = servletContext.getServletRegistration("jsp");
        jsp.addMapping(ConfigUtil.getAppJspPath() + "*");
        /**
         * 静态文件路径映射
         */
        ServletRegistration aDefault = servletContext.getServletRegistration("default");
        aDefault.addMapping(ConfigUtil.getAppAssertPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {

        String requestPath = req.getRequestURI();
        String reqMethod = req.getMethod();
        RequestHandler handler = ControllerParser.getHandler(reqMethod, requestPath);
        if (handler == null) {
            log.error("could not find specify method:{} and url:{}", reqMethod, requestPath);
            return;
        }
        Object controllerBean = BeanParser.getBeanMap().get(handler.getControllerClass());
        if (controllerBean == null)
            throw new RuntimeException("could not find specify bean:" + handler.getControllerClass().getName());
        Object result = invokeMethod(controllerBean, handler, req);
        if (result instanceof View) {
            View view = (View) result;
            String path = view.getPath();
            if (StringUtils.isNotEmpty(path)) {
                if (path.startsWith("/")) {
                    rsp.sendRedirect(req.getContextPath() + path);
                } else {
                    Map<String, Object> model = view.getModel();
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigUtil.getAppJspPath() + path).forward(req, rsp);
                }
            }
        }
        if (result instanceof ModelData) {
            rsp.setContentType("application/json");
            rsp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            PrintWriter writer = rsp.getWriter();
            writer.write(JSON.toJSONString(result));
            writer.flush();
            writer.close();
        }
    }


    private Object invokeMethod(Object controllerBean, RequestHandler handler, HttpServletRequest req) throws IOException {
        if (handler.getParameterTypes() == null || handler.getParameterTypes().length == 0) {
            return ReflectUtil.invokeMethod(controllerBean, handler.getActionMethod());
        }
        return ReflectUtil.invokeMethod(controllerBean, handler.getActionMethod(), parserParam(req));
    }


    private Param parserParam(HttpServletRequest req) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = req.getParameter(paramName);
            map.put(paramName, paramValue);
        }
        String body = IOUtil.getInputStreamString(req.getInputStream(), StandardCharsets.UTF_8);
        if (StringUtils.isNotEmpty(body)) {
            map.putAll(JSON.parseObject(body));
        }
        return new Param(map);
    }
}
