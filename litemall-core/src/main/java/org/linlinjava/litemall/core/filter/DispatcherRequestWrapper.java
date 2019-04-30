package org.linlinjava.litemall.core.filter;

import org.apache.catalina.Globals;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Objects;

final class DispatcherRequestWrapper extends HttpServletRequestWrapper {
    private static final String SLASH = "/";
    private static final String NEED_VERIFY = "needVerify";
    private static final String SLASH_NEED_VERIFY = SLASH + NEED_VERIFY;
    private static final int LEN_SLASH_NEED_VERIFY = SLASH_NEED_VERIFY.length();
    private static final String NEED_VERIFY_TRUE = "Y";
    private static final String NEED_VERIFY_FALSE = "N";
    private boolean refresh;
    private boolean needVerify;
    private String requestURI;
    private String servletPath;

    DispatcherRequestWrapper(HttpServletRequest request) {
        super(request);
        this.refresh();
    }

    private void refresh() {
        if (this.refresh)
            return;
        this.requestURI = this.procPath(super.getRequestURI());
        this.servletPath = this.procPath(super.getServletPath());
        this.refresh = true;
    }

    private String procPath(String path) {
        if (path == null)
            return null;
        path = StringUtils.stripEnd(path, SLASH);
        if (StringUtils.endsWithIgnoreCase(path, SLASH_NEED_VERIFY)) {
            this.needVerify = true;
            return path.substring(0, path.length() - LEN_SLASH_NEED_VERIFY);
        }
        return path;
    }

    @Override
    public void setAttribute(String name, Object o) {
        super.setAttribute(name, o);
        if (Objects.equals(name, Globals.DISPATCHER_TYPE_ATTR))
            this.refresh = false;
    }

    @Override
    public String getParameter(String name) {
        return StringUtils.equalsIgnoreCase(name, NEED_VERIFY)
                ? (this.needVerify ? NEED_VERIFY_TRUE : NEED_VERIFY_FALSE)
                : super.getParameter(name);
    }

    @Override
    public String getServletPath() {
        this.refresh();
        return this.servletPath;
    }

    @Override
    public String getRequestURI() {
        this.refresh();
        return this.requestURI;
    }
}
