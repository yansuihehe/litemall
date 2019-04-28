package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.LitemallWithdrawApplyService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 用户提现申请
 *
 * @author sunyan
 * @date 2019/4/22
 */
@RestController
@RequestMapping("/wx/withdraw")
@Validated
public class WxWithdrawApplyController {

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private LitemallWithdrawApplyService withdrawApplyService;

    /**
     * 用户提交申请
     *
     * @param userId  用户id
     * @param amount  提现金额
     * @param channel 提现渠道 默认微信 1
     * @return
     */
    @PostMapping("/apply")
    public Object apply(@LoginUser Integer userId,@RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        BigDecimal amount = new BigDecimal(JacksonUtil.parseString(body, "amount"));
        Byte channel = JacksonUtil.parseByte(body, "channel");
        //校验用户账户佣金额度是否大于提现金额
        LitemallUser user = userService.findById(userId);
        if (amount != null && user.getCommissionAmount().compareTo(amount) == -1) {
            return ResponseUtil.badArgumentValue();
        }
        String openid = user.getWeixinOpenid();
        withdrawApplyService.apply(userId, amount, channel, openid);
        return ResponseUtil.ok();
    }

}
