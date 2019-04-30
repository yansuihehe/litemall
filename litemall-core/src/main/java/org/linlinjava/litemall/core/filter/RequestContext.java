package org.linlinjava.litemall.core.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class RequestContext {
    //region static

    //测试用上下文
    private static RequestContext testContext;

    //线程本地存储
    private final static ThreadLocal<RequestContext> contexts = new ThreadLocal<>();

    /**
     * 初始化当前线程的请求上下文
     *
     * @param request  请求
     * @param response 响应
     * @return 当前线程的请求上下文
     */
    public static RequestContext begin(HttpServletRequest request, HttpServletResponse response) {
        RequestContext rc = new RequestContext(request, response);
        contexts.set(rc);
        return rc;
    }

    /**
     * 初始化测试请求上下文,仅供测试使用
     *
     * @return 测试请求上下文
     */
    public static RequestContext test() {
        if (testContext == null)
            testContext = new RequestContext();
        return testContext;
    }

    /**
     * 获取当前线程的请求上下文,如果不存在抛出异常
     *
     * @return 当前线程的请求上下文
     */
    public static RequestContext get() {
        RequestContext rc = testContext == null ? contexts.get() : testContext;
        if (rc == null)
            throw new RuntimeException("this thread local not set instance of RequestContext");
        return rc;
    }

    /**
     * 获取测试请求上下文数据
     *
     * @return 测试请求上下文数据
     */
    public static RequestContextData testData() {
        return test().getData();
    }

    /**
     * 获取当前线程的请求上下文数据
     *
     * @return 当前线程的请求上下文数据
     */
    public static RequestContextData data() {
        return get().getData();
    }

    /**
     * 获取当前线程是否存在请求上下文
     *
     * @return 存在返回 true,否则返回 false
     */
    public static boolean exists() {
        return !(testContext == null && contexts.get() == null);
    }


    /**
     * 释放当前线程的请求上下文
     */
    public static void end() {
        RequestContext rc = contexts.get();
        if (rc == null)
            return;
        rc.destroy();
        contexts.remove();
    }

    //endregion


    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext context;
    private HttpSession session;
    private Map<String, Cookie> cookies;
    private RequestContextData data;

    /**
     * 构造函数
     */
    private RequestContext() {
        this.data = new RequestContextData();
    }

    /**
     * 构造函数
     *
     * @param request  请求
     * @param response 响应
     */
    private RequestContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.context = request.getServletContext();
        this.session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        this.cookies = cookies == null ? new HashMap<>(0) : Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, item -> item));
        this.data = new RequestContextData();
    }

    /**
     * 销毁数据
     */
    private void destroy() {
        this.request = null;
        this.response = null;
        this.context = null;
        this.session = null;
        this.cookies = null;
        this.data = null;
    }

    //region getter

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ServletContext getContext() {
        return context;
    }

    public HttpSession getSession() {
        return session;
    }

    public Map<String, Cookie> getCookies() {
        return cookies;
    }

    public RequestContextData getData() {
        return data;
    }

    //endregion
}
