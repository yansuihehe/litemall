package org.linlinjava.litemall.wx.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.linlinjava.litemall.core.filter.RequestContext;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.service.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.linlinjava.litemall.wx.util.WxResponseCode.AUTH_INVALID_ACCOUNT;

/**
 * request interceptor.
 * @author liu qingxiang
 * @since v1.0.0
 */
@Aspect
@Component
public class RequestInterceptor {
    public Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private LitemallUserService litemallUserService;


    @Pointcut("execution(* org.linlinjava..*.*(..)) && "
            + "(  @annotation(org.springframework.web.bind.annotation.PostMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.GetMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.PutMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    private void doRequestPointcut() {

    }

    @Before(value = "doRequestPointcut()")
    public void doAuth(JoinPoint joinPoint) throws NoSuchMethodException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String wechatToken = request.getHeader("X-Litemall-Token");
        if (wechatToken == null)
            return;

        // 拦截微信用户token，获取用户信息，放至ThreadLocal中
        int userId = UserTokenManager.getUserId(wechatToken);
        LitemallUser user = litemallUserService.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        RequestContext.data().initData(userId, user.getUserLevel(), wechatToken);

    }

}
