package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 佣金相关
 *
 * @author sunyan
 * @date 2019/4/29
 */
@Service
public class CommissionService {

    private static final Logger log = LoggerFactory.getLogger(CommissionService.class);

    /**
     * 重试次数
     */
    public static final int RETRY_TIMES = 4;

    /**
     * 重试间隔（ms）
     */
    public static final int RETRY_INTERVAL = 1000;

    @Autowired
    private LitemallOrderService litemallOrderService;

    @Autowired
    private LitemallUserService litemallUserService;

    /**
     * 往上级用户账户中增加佣金
     *
     * @param orderId
     */
    public void addCommission(Integer orderId) {
        LitemallOrder order = litemallOrderService.findById(orderId);
        LitemallUser user = litemallUserService.findById(order.getUserId());
        if (user.getSuperior() != null) {
            LitemallUser superior = litemallUserService.findById(user.getSuperior());
            if (superior != null) {
                BigDecimal orderCommission = order.getCommission();// 订单产生的佣金
                superior.setCommissionAmount(superior.getCommissionAmount().add(orderCommission));
                int count = 0;
                int times = 0;
                do {
                    if (times > 0) {
                        try {
                            Thread.sleep(RETRY_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count = litemallUserService.updateWithOptimisticLocker(user);
                    times++;
                } while (count == 0 && times < RETRY_TIMES);
                if(count == 0){//更新失败
                    log.error("用户ID={}增加账户佣金失败，佣金来源订单ID={}, 金额={}", superior.getId(), orderId, orderCommission);
                } else {//更新成功
                    log.info("用户ID={}增加账户佣金成功，佣金来源订单ID={}, 金额={}", superior.getId(), orderId, orderCommission);
                }
            }

        }
    }

}
