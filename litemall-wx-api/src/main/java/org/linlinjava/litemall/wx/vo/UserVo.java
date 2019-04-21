package org.linlinjava.litemall.wx.vo;

import java.math.BigDecimal;

/**
 * @author sunyan
 * @date 2019/4/21
 */
public class UserVo {

    /**
     * 佣金金额
     */
    private BigDecimal commissionAmount;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmount;

    public UserVo(BigDecimal commissionAmount, BigDecimal withdrawAmount) {
        this.commissionAmount = commissionAmount;
        this.withdrawAmount = withdrawAmount;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }
}
