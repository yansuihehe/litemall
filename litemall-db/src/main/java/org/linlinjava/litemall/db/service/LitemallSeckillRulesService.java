package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.domain.LitemallSeckillRules;

import java.util.List;
import java.util.Map;

/**
 * 秒杀规则模块.
 * @author Liu qingxiang
 * @since v1.0.0
 */
public interface LitemallSeckillRulesService {

    /**
     * 分页获取秒杀规则列表.
     * @param page      页数
     * @param limit     分页数量
     * @param sort      排序属性
     * @param order     desc or esc
     * @return
     */
    List<LitemallSeckillRules> getSecKillRulesList(Integer page, Integer limit, String sort, String order, String query);

    /**
     * 保存秒杀规则.
     * @param litemallSeckillRules
     */
    void saveSecKillRules(LitemallSeckillRules litemallSeckillRules);

    /**
     * 更新秒杀规则.
     * @param litemallSeckillRules
     */
    void updateSecKillRules(LitemallSeckillRules litemallSeckillRules);

    /**
     * 秒杀规则表商品数量处理.
     * @param litemallSeckillRules
     * @return 成功:true 失败：false
     */
    boolean seckill(LitemallSeckillRules litemallSeckillRules);

    /**
     * 根据id查询有效的秒杀规则信息.
     * @param id
     * @return
     */
    LitemallSeckillRules queryById(Integer id);

    /**
     * 根据商品id查询有效的秒杀规则信息.
     * @param goodId
     * @return
     */
    List<LitemallSeckillRules> queryByGoodId(Integer goodId);


    /**
     * 根据id查询有效的秒杀规则信息.
     * @param id
     * @return
     */
    boolean deleteById(Integer id);

    /**
     * 根据id查询有效的秒杀规则信息.
     * @param id
     * @return
     */
    Map seckillRuleDetail(Integer id);


}
