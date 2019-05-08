package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.PageInfo;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallWithdrawApply;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.LitemallWithdrawApplyService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param amount 提现金额
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
        //校验用户账户佣金额度是否大于提现金额  同时查询审核中的金额
        BigDecimal pendingAmount = withdrawApplyService.queryPendingAmount(userId);
        BigDecimal willWithdrawAmount = pendingAmount.add(amount);
        LitemallUser user = userService.findById(userId);
        if (amount != null && user.getCommissionAmount().compareTo(willWithdrawAmount) == -1) {
            return ResponseUtil.badArgumentValue();
        }
        String openid = user.getWeixinOpenid();
        withdrawApplyService.apply(userId, amount, channel, openid);
        return ResponseUtil.ok();
    }

    /**
     * 获取用户提现记录
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/applications")
    public Object getWithdrawApplications(@LoginUser Integer userId,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer limit){
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        List<LitemallWithdrawApply> result = withdrawApplyService.query(page, limit, null, null, userId);
        long total = PageInfo.of(result).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", result);
        return ResponseUtil.ok(data);
    }

}
