package org.linlinjava.litemall.db.service.impl;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallSeckillRulesMapper;
import org.linlinjava.litemall.db.domain.LitemallSeckillRules;
import org.linlinjava.litemall.db.domain.LitemallSeckillRulesExample;
import org.linlinjava.litemall.db.service.LitemallSeckillRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 秒杀服务.
 * @author Liu qingxiang
 * @since v1.0.0
 */
@Service
public class LitemallSeckillRulesServiceImpl implements LitemallSeckillRulesService {

    @Autowired
    private LitemallSeckillRulesMapper litemallSeckillRulesMapper;

    @Override
    public List<LitemallSeckillRules> getSecKillRulesList(Integer page, Integer limit, String sort, String order) {
        LitemallSeckillRulesExample example = new LitemallSeckillRulesExample();
        LitemallSeckillRulesExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);

        return litemallSeckillRulesMapper.selectByExample(example);
    }

    @Override
    public void saveSecKillRules(LitemallSeckillRules litemallSeckillRules) {
        try {
            litemallSeckillRules.setAddTime(LocalDateTime.now());
            litemallSeckillRules.setUpdateTime(LocalDateTime.now());
            litemallSeckillRules.setDeleted(false);
            litemallSeckillRulesMapper.insert(litemallSeckillRules);
        } catch (Exception e) {
            throw new RuntimeException("保存秒杀规则失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSecKillRules(LitemallSeckillRules litemallSeckillRules) {

        try {
            litemallSeckillRules.setUpdateTime(LocalDateTime.now());
            litemallSeckillRulesMapper.updateByPrimaryKey(litemallSeckillRules);
        } catch (Exception e) {
            throw new RuntimeException("更新秒杀规则失败");
        }


    }

    @Override
    public boolean seckill(LitemallSeckillRules litemallSeckillRules) {
        try {
            // todo 是否需要校验规则的存在性,应该不需要校验，默认存在，直接处理.
            /*litemallSeckillRules = litemallSeckillRulesMapper.selectByPrimaryKey(litemallSeckillRules.getId());
            if (litemallSeckillRules == null) {
                throw new RuntimeException("秒杀规则不存在");
            }*/
            int result = litemallSeckillRulesMapper.seckill(litemallSeckillRules.getId());
            if (result == 1)
                return true;
        } catch (Exception e) {
            throw new RuntimeException("秒杀失败,系统异常");
        }
        return false;
    }

    @Override
    public LitemallSeckillRules queryById(Integer id) {
        LitemallSeckillRulesExample example = new LitemallSeckillRulesExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return litemallSeckillRulesMapper.selectOneByExample(example);
    }
}
