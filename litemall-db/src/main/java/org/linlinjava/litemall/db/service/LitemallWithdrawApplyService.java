package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallWithdrawApplyMapper;
import org.linlinjava.litemall.db.domain.LitemallWithdrawApply;
import org.linlinjava.litemall.db.domain.LitemallWithdrawApplyExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 查询列表
     * @param offset
     * @param limit
     * @param status
     * @param openid
     * @return
     */
    public List<LitemallWithdrawApply> query(Integer offset, Integer limit, Byte status, String openid){
        LitemallWithdrawApplyExample example = new LitemallWithdrawApplyExample();
        LitemallWithdrawApplyExample.Criteria criteria = example.or();
        if(status != null){
            criteria.andStatusEqualTo(status);
        }
        if(!StringUtils.isEmpty(openid)){
            criteria.andOpenIdEqualTo(openid);
        }
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);
        return withdrawApplyMapper.selectByExample(example);
    }

    /**
     * 审批通过
     * @param id
     */
    public void approve(Integer id){
        //TODO 修改之前审核状态
        LitemallWithdrawApply apply = new LitemallWithdrawApply();
        apply.setId(id);
        apply.setStatus(LitemallWithdrawApply.STATUS_APPROVE);
        withdrawApplyMapper.updateByPrimaryKeySelective(apply);
    }

    /**
     * 拒绝
     * @param id
     */
    public void reject(Integer id){
        //TODO 修改之前审核状态
        LitemallWithdrawApply apply = new LitemallWithdrawApply();
        apply.setId(id);
        apply.setStatus(LitemallWithdrawApply.STATUS_REJECT);
        withdrawApplyMapper.updateByPrimaryKeySelective(apply);
    }

}
