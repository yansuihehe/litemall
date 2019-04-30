package org.linlinjava.litemall.core.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public final class DispatcherFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        //转发 (forward) 保持原始上下文
        if (RequestContext.exists()) {
            chain.doFilter(request, response);
            return;
        }

        //非转发 (not forward)
        HttpServletRequest httpRequest = new DispatcherRequestWrapper((HttpServletRequest) request);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        RequestContext.begin(httpRequest, httpResponse);
        try {
            chain.doFilter(httpRequest, httpResponse);
        } finally {
            RequestContext.end();
        }
    }

    @Override
    public void destroy() {
    }
}
