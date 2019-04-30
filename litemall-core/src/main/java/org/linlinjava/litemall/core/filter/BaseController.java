package org.linlinjava.litemall.core.filter;

import org.linlinjava.litemall.core.util.ResponseUtil;

import java.util.Map;

public class BaseController {

    protected Object fail(int retFlag, String retMsg) {
        return ResponseUtil.fail(retFlag, retMsg);
    }

    protected Map<String, Object> fail(int retFlag, String retMsg, Object result) {
        Map<String, Object> resultMap = (Map<String, Object>) ResponseUtil.fail(retFlag, retMsg);
        resultMap.put("body", result);
        return resultMap;
    }

    protected Byte getUserLevel() {
        return RequestContext.data().getUserLevel();
    }

    protected int getUserId() {
        return RequestContext.data().getUserId();
    }

}
