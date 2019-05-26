package org.linlinjava.litemall.db.domain;

import java.math.BigDecimal;

/**
 * 货品价格
 */
public class PriceVo {

    /**
     * 最终价格
     */
    private BigDecimal finalPrice;

    /**
     * 优惠类型
     */
    private int discountType;


    /**
     * 优惠名称
     */
    private String discountName;


    /**
     * 展示价格
     */
    private BigDecimal showPrice;

    public PriceVo(BigDecimal finalPrice, int discountType, String discountName, BigDecimal showPrice) {
        this.finalPrice = finalPrice;
        this.discountType = discountType;
        this.discountName = discountName;
        this.showPrice = showPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public BigDecimal getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }
}
