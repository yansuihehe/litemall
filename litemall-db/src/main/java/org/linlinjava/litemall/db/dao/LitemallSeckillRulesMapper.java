package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallSeckillRules;
import org.linlinjava.litemall.db.domain.LitemallSeckillRulesExample;
import org.springframework.stereotype.Repository;

@Repository
public interface LitemallSeckillRulesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    long countByExample(LitemallSeckillRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallSeckillRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    int insert(LitemallSeckillRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    int insertSelective(LitemallSeckillRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallSeckillRules selectOneByExample(LitemallSeckillRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallSeckillRules selectOneByExampleSelective(@Param("example") LitemallSeckillRulesExample example, @Param("selective") LitemallSeckillRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallSeckillRules> selectByExampleSelective(@Param("example") LitemallSeckillRulesExample example, @Param("selective") LitemallSeckillRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    List<LitemallSeckillRules> selectByExample(LitemallSeckillRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallSeckillRules selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallSeckillRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    LitemallSeckillRules selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallSeckillRules selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallSeckillRules record, @Param("example") LitemallSeckillRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallSeckillRules record, @Param("example") LitemallSeckillRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallSeckillRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallSeckillRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallSeckillRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_seckill_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);


    /**
     * 秒杀商品数量减一处理.
     * @param id
     * @return
     */
    int seckill(Integer id);
}