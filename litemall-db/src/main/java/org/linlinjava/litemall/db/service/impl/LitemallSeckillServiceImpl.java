package org.linlinjava.litemall.db.service.impl;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallSeckillMapper;
import org.linlinjava.litemall.db.domain.LitemallSeckill;
import org.linlinjava.litemall.db.domain.LitemallSeckillExample;
import org.linlinjava.litemall.db.service.LitemallSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 秒杀服务.
 * @author Liu qingxiang
 * @since v1.0.0
 */
@Service
public class LitemallSeckillServiceImpl implements LitemallSeckillService {

    @Autowired
    private LitemallSeckillMapper litemallSeckillMapper;

    @Override
    public List<LitemallSeckill> getSecKillList(Integer page, Integer limit, String sort, String order) {
        LitemallSeckillExample example = new LitemallSeckillExample();
        LitemallSeckillExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);

        return litemallSeckillMapper.selectByExample(example);
    }
}
