package org.linlinjava.litemall.wx.vo;

import java.math.BigDecimal;
import java.util.Optional;

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
     * 已提现金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 提现中金额（审核中）
     */
    private BigDecimal pendingWithdrawAmount;

    /**
     * 可提现金额 = 佣金金额 - 提现中金额
     */
    private BigDecimal allowWithdrawAmount;

    public UserVo(BigDecimal commissionAmount, BigDecimal withdrawAmount, BigDecimal pendingWithdrawAmount) {
        this.commissionAmount = Optional.ofNullable(commissionAmount).orElse(BigDecimal.ZERO);
        this.withdrawAmount = Optional.ofNullable(withdrawAmount).orElse(BigDecimal.ZERO);
        this.pendingWithdrawAmount = Optional.ofNullable(pendingWithdrawAmount).orElse(BigDecimal.ZERO);
        this.allowWithdrawAmount = this.commissionAmount.subtract(this.pendingWithdrawAmount);
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

    public BigDecimal getPendingWithdrawAmount() {
        return pendingWithdrawAmount;
    }

    public void setPendingWithdrawAmount(BigDecimal pendingWithdrawAmount) {
        this.pendingWithdrawAmount = pendingWithdrawAmount;
    }

    public BigDecimal getAllowWithdrawAmount() {
        return allowWithdrawAmount;
    }

    public void setAllowWithdrawAmount(BigDecimal allowWithdrawAmount) {
        this.allowWithdrawAmount = allowWithdrawAmount;
    }
}
