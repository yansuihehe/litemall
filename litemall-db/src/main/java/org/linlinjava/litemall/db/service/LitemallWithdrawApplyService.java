package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallWithdrawApplyMapper;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallWithdrawApply;
import org.linlinjava.litemall.db.domain.LitemallWithdrawApplyExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author sunyan
 * @date 2019/4/22
 */
@Service
public class LitemallWithdrawApplyService {

    private static final Logger log = LoggerFactory.getLogger(CommissionService.class);

    @Resource
    private LitemallWithdrawApplyMapper withdrawApplyMapper;

    @Autowired
    private LitemallUserService userService;

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
    public List<LitemallWithdrawApply> query(Integer offset, Integer limit, Byte status, String openid, Integer userId){
        LitemallWithdrawApplyExample example = new LitemallWithdrawApplyExample();
        LitemallWithdrawApplyExample.Criteria criteria = example.or();
        if(status != null){
            criteria.andStatusEqualTo(status);
        }
        if(!StringUtils.isEmpty(openid)){
            criteria.andOpenIdEqualTo(openid);
        }
        if(userId != null){
            criteria.andUserIdEqualTo(userId);
        }
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);
        return withdrawApplyMapper.selectByExample(example);
    }

    /**
     * 审批通过
     * @param id
     */
    @Transactional
    public void approve(Integer id){
        //TODO 修改之前审核状态
        LitemallWithdrawApply apply = new LitemallWithdrawApply();
        apply.setId(id);
        apply.setStatus(LitemallWithdrawApply.STATUS_APPROVE);
        withdrawApplyMapper.updateByPrimaryKeySelective(apply);
        //从佣金中扣除金额 在已提现金额中增加
        LitemallUser user = userService.findById(apply.getUserId());
        user.setCommissionAmount(user.getCommissionAmount().subtract(apply.getWithdrawAmount()));
        user.setWithdrawAmount(user.getWithdrawAmount().add(apply.getWithdrawAmount()));
        int count = userService.updateWithOptimisticLocker(user);
        if(count == 0){//提现失败
            log.error("提现申请{}审批通过失败", id);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } else {
            log.info("提现申请{}审批通过成功，用户{}提现金额{}", id, apply.getUserId(), apply.getWithdrawAmount());
        }
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

    /**
     * 查询提现中的金额
     * @param userId
     * @return
     */
    public BigDecimal queryPendingAmount(Integer userId){
        LitemallWithdrawApplyExample example = new LitemallWithdrawApplyExample();
        LitemallWithdrawApplyExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo(LitemallWithdrawApply.STATUS_PENDING);
        List<LitemallWithdrawApply> litemallWithdrawApplies = withdrawApplyMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(litemallWithdrawApplies)){
            Optional<BigDecimal> amount = litemallWithdrawApplies.stream().map(apply -> apply.getWithdrawAmount()).reduce((amount1, amount2) -> amount1.add(amount2));
            return amount.orElse(BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }

}
