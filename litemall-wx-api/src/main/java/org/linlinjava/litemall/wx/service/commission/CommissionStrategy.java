package org.linlinjava.litemall.wx.service.commission;

import org.linlinjava.litemall.db.domain.LitemallCart;
import org.linlinjava.litemall.db.domain.LitemallOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 佣金计算规则
 * @author sunyan
 * @date 2019/4/21
 */
public interface CommissionStrategy {

    /**
     * 订单佣金计算策略
     * @param order 订单
     * @param checkedGoodsList 购物车中的商品
     * @return
     */
    BigDecimal computeByOrder(LitemallOrder order, List<LitemallCart> checkedGoodsList);

    /**
     * 货品佣金计算策略
     * @param order 订单
     * @param checkedGoods 购物车中一种货品
     * @return
     */
    BigDecimal computeByProduct(LitemallOrder order, LitemallCart checkedGoods);

}
