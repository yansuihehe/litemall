package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;

public interface GoodsProductMapper {
    int addStock(@Param("id") Integer id, @Param("num") Short num);
    int reduceStock(@Param("id") Integer id, @Param("num") Short num);
    int updateByGoodsIdAndSpecifications(@Param("record") LitemallGoodsProduct product);
}