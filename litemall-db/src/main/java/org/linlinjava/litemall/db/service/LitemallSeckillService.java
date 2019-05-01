package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.domain.LitemallSeckill;

import java.util.List;

/**
 * 秒杀模块.
 * @author Liu qingxiang
 * @since v1.0.0
 */
public interface LitemallSeckillService {

    /**
     * 分页获取秒杀商品列表.
     * @param page      页数
     * @param limit     分页数量
     * @param sort      排序属性
     * @param order     desc or esc
     * @return
     */
    List<LitemallSeckill> getSecKillList(Integer page, Integer limit, String sort, String order);



}
