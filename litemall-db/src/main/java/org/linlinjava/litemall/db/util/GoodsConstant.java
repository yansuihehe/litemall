package org.linlinjava.litemall.db.util;

public class GoodsConstant {

    public enum DiscountType{
        NOMAL(0, ""),
        VIP_PRICE(1, "会员价"),
        SECKILL_PRICE(2, "秒杀价");

        /**
         * 类型
         */
        private int type;

        /**
         * 名称
         */
        private String name;

        DiscountType(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }
}
