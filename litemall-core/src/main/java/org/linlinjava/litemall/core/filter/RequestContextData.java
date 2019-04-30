package org.linlinjava.litemall.core.filter;

import org.springframework.util.Assert;

import java.util.Stack;

public final class RequestContextData {
    private boolean inited;
    private int userId;
    private Byte userLevel;
    private String userToken;
    private Stack<BaseController> controllerStack = new Stack<>();

    //构造函数
    RequestContextData() {
    }

    //region getter

    public boolean isInited() {
        return inited;
    }

    public int getUserId() {
        return userId;
    }

    public Byte getUserLevel() {
        return userLevel;
    }

    public BaseController getEntryController() {
        return this.controllerStack.empty() ? null : this.controllerStack.firstElement();
    }

    public BaseController getExecutingController() {
        return this.controllerStack.empty() ? null : this.controllerStack.lastElement();
    }

    public BaseController[] getControllerStack() {
        return this.controllerStack.toArray(new BaseController[this.controllerStack.size()]);
    }

    public void  initData(int userId, Byte userLevel, String token) {
        this.userId = userId;
        this.userLevel = userLevel;
        this.userToken = token;
    }

    public void completeInit() {
        this.inited = true;
    }

    public void enterController(BaseController controller) {
        Assert.notNull(controller, "controller can not be null");
        this.controllerStack.push(controller);
    }

    public void exitController() {
        this.controllerStack.pop();
    }
}
