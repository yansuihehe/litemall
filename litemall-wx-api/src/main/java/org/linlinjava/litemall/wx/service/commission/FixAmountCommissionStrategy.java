package org.linlinjava.litemall.wx.service.commission;

import org.linlinjava.litemall.db.domain.LitemallCart;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.service.LitemallGoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 佣金定额计算策略
 * @author sunyan
 * @date 2019/4/21
 */
@Component
public class FixAmountCommissionStrategy implements CommissionStrategy{

    @Autowired
    private LitemallGoodsProductService productService;

    @Override
    public BigDecimal computeByOrder(LitemallOrder order, List<LitemallCart> checkedGoodsList) {
        if(CollectionUtils.isEmpty(checkedGoodsList)){
            return BigDecimal.ZERO;
        }
        Optional<BigDecimal> sum = checkedGoodsList.stream().map(checkedGoods -> computeByProduct(order, checkedGoods)).reduce((a, b) -> a.add(b));
        if(sum.isPresent()){
            return sum.get();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal computeByProduct(LitemallOrder order, LitemallCart checkedGoods) {
        Integer productId = checkedGoods.getProductId();
        LitemallGoodsProduct product = productService.findById(productId);
        return product.getCommission();
    }
}
