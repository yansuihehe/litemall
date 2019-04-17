package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.express.ExpressService;
import org.linlinjava.litemall.core.express.dao.ExpressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信快递查询服务.
 * @author Liu qingxiang
 * @since v1.0.0
 */
@RestController
@RequestMapping("/wx/express")
@Validated
public class WxExpressController {

    @Autowired
    private ExpressService expressService;

    @GetMapping("/{expCode}/{expNo}")
    public Map getExpressInfo(@PathVariable String expCode, @PathVariable String expNo) {
        ExpressInfo expressInfo = expressService.getExpressInfo(expCode, expNo);
        Map<String, Object> result = new HashMap<>();
        if (expressInfo == null) {
            throw new RuntimeException("获取快递信息失败");
        }
        result.put("expressInfo", expressInfo);
        return result;
    }

}
