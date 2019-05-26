package org.linlinjava.litemall.db.service.impl;

import com.github.pagehelper.PageHelper;
import org.checkerframework.checker.units.qual.A;
import org.linlinjava.litemall.db.dao.LitemallSeckillRulesMapper;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import org.linlinjava.litemall.db.domain.LitemallSeckillRules;
import org.linlinjava.litemall.db.domain.LitemallSeckillRulesExample;
import org.linlinjava.litemall.db.service.LitemallGoodsProductService;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.linlinjava.litemall.db.service.LitemallSeckillRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 秒杀服务.
 * @author Liu qingxiang
 * @since v1.0.0
 */
@Service
public class LitemallSeckillRulesServiceImpl implements LitemallSeckillRulesService {

    @Autowired
    private LitemallSeckillRulesMapper litemallSeckillRulesMapper;

    @Autowired
    private LitemallGoodsService litemallGoodsService;

    @Autowired
    private LitemallGoodsProductService litemallGoodsProductService;

    @Override
    public List<LitemallSeckillRules> getSecKillRulesList(Integer page, Integer limit, String sort, String order, String query) {
        LitemallSeckillRulesExample example = new LitemallSeckillRulesExample();
        LitemallSeckillRulesExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(query)) {
            criteria.andGoodsNameLike("%" + query + "%");
            Pattern pattern = Pattern.compile("[0-9]*");
            if (pattern.matcher(query).matches()) {
                example.or().andGoodsIdEqualTo(Integer.parseInt(query))
                    .andDeletedEqualTo(false);
                example.or().andProductIdEqualTo(Integer.parseInt(query))
                    .andDeletedEqualTo(false);

            }
        }
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);

        return litemallSeckillRulesMapper.selectByExample(example);
    }

    @Override
    public void saveSecKillRules(LitemallSeckillRules litemallSeckillRules) {
        LitemallGoods litemallGoods = litemallGoodsService.findById(litemallSeckillRules.getGoodsId());
        litemallSeckillRules.setGoodsName(litemallGoods.getName());
        LitemallGoodsProduct litemallGoodsProduct = litemallGoodsProductService.findById(litemallSeckillRules.getProductId());
        litemallSeckillRules.setPicUrl(litemallGoodsProduct.getUrl());
        litemallSeckillRules.setAddTime(LocalDateTime.now());
        litemallSeckillRules.setUpdateTime(LocalDateTime.now());
        litemallSeckillRules.setDeleted(false);
        litemallSeckillRulesMapper.insert(litemallSeckillRules);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSecKillRules(LitemallSeckillRules litemallSeckillRules) {
        litemallSeckillRules.setUpdateTime(LocalDateTime.now());
        litemallSeckillRulesMapper.updateByPrimaryKey(litemallSeckillRules);
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
            e.printStackTrace();
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

    @Override
    public List<LitemallSeckillRules> queryByGoodId(Integer goodId) {
        LitemallSeckillRulesExample example = new LitemallSeckillRulesExample();
        example.or().andGoodsIdEqualTo(goodId).andDeletedEqualTo(false).andBeginTimeLessThanOrEqualTo(LocalDateTime.now()).andExpireTimeGreaterThanOrEqualTo(LocalDateTime.now());
        return litemallSeckillRulesMapper.selectByExample(example);
    }

    @Override
    public boolean deleteById(Integer id) {
        int result = litemallSeckillRulesMapper.deleteByPrimaryKey(id);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Map seckillRuleDetail(Integer id) {
        return null;
    }

    @Override
    public List<LitemallSeckillRules> queryByProductId(Integer productId) {
        LitemallSeckillRulesExample example = new LitemallSeckillRulesExample();
        example.or().andProductIdEqualTo(productId).andDeletedEqualTo(false);
        return litemallSeckillRulesMapper.selectByExample(example);
    }
}
