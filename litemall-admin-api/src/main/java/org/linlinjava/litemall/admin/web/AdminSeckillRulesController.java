package org.linlinjava.litemall.admin.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallSeckillRules;
import org.linlinjava.litemall.db.service.LitemallSeckillRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 秒杀规则模块.
 * @author Liu qingxiang
 * @since v1.0.0
 */

@RestController
@RequestMapping("/admin/seckill/rules")
@Validated
public class AdminSeckillRulesController {

    @Autowired
    private LitemallSeckillRulesService litemallSeckillRulesService;

    /**
     * 获取秒杀列表.
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:seckill:rules:list")
    @RequiresPermissionsDesc(menu={"推广管理" , "秒杀规则"}, button="查询")
    @GetMapping(value = "/list")
    public Object getSeckillRulesList(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "add_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallSeckillRules> list = litemallSeckillRulesService.getSecKillRulesList(page, limit, sort, order);
        return ResponseUtil.okList(list);
    }

    /**
     * 保存秒杀规则.
     * @param rules
     * @return
     */
    @RequiresPermissions("admin:seckill:rules:save")
    @RequiresPermissionsDesc(menu={"推广管理" , "秒杀规则"}, button="保存")
    @PostMapping(value = "/save")
    public Object saveSecKillRulesList(@RequestBody LitemallSeckillRules rules) {
        litemallSeckillRulesService.saveSecKillRules(rules);
        return ResponseUtil.ok();
    }

    /**
     * 更新秒杀规则.
     * @param rules
     * @return
     */
    @RequiresPermissions("admin:seckill:rules:update")
    @RequiresPermissionsDesc(menu={"推广管理" , "秒杀规则"}, button="编辑")
    @PostMapping(value = "/update")
    public Object updateSecKillRulesList(@RequestBody LitemallSeckillRules rules) {
        litemallSeckillRulesService.updateSecKillRules(rules);
        return ResponseUtil.ok();
    }

    /**
     * 删除秒杀规则.
     * @param rules
     * @return
     */
    @RequiresPermissions("admin:seckill:rules:delete")
    @RequiresPermissionsDesc(menu={"推广管理" , "秒杀规则"}, button="删除")
    @PostMapping(value = "/delete")
    public Object deleteSecKillRulesList(@RequestBody LitemallSeckillRules rules) {
        litemallSeckillRulesService.deleteById(rules.getId());
        return ResponseUtil.ok();
    }

    /**
     * 获取秒杀规则详情.
     * @param ruleId    描述规则id
     * @return
     */
    @RequiresPermissions("admin:seckill:rules:detail")
    @RequiresPermissionsDesc(menu={"推广管理" , "秒杀规则"}, button="详情")
    @GetMapping(value = "/detail/{ruleId}")
    public Object secKillRulesDetail(@PathVariable(value = "ruleId") int ruleId) {
        litemallSeckillRulesService.deleteById(ruleId);
        return ResponseUtil.ok();
    }


}
