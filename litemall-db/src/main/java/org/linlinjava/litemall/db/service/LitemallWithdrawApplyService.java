package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallWithdrawApplyMapper;
import org.linlinjava.litemall.db.domain.LitemallWithdrawApply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author sunyan
 * @date 2019/4/22
 */
@Service
public class LitemallWithdrawApplyService {

    @Resource
    private LitemallWithdrawApplyMapper withdrawApplyMapper;

    /**
     * 创建提现申请
     * @param userId
     * @param amount
     * @param channel
     */
    public void apply(Integer userId, BigDecimal amount, Byte channel, String openid){
        LitemallWithdrawApply apply = new LitemallWithdrawApply();
        apply.setAddTime(LocalDateTime.now());
        apply.setChannel(channel);
        apply.setUserId(userId);
        apply.setWithdrawAmount(amount);
        apply.setOpenId(openid);
        withdrawApplyMapper.insertSelective(apply);
    }

}
