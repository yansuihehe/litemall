package org.linlinjava.litemall.admin.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallSeckill;
import org.linlinjava.litemall.db.service.LitemallSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 秒杀模块.
 * @author Liu qingxiang
 * @since v1.0.0
 */

@RestController
@RequestMapping("/admin/seckill")
@Validated
public class AdminSeckillController {

    @Autowired
    private LitemallSeckillService litemallSeckillService;

    /**
     * 获取秒杀列表.
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping(value = "/list")
    public Object getSeckillList(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "add_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallSeckill> list = litemallSeckillService.getSecKillList(page, limit, sort, order);
        return ResponseUtil.ok(list);
    }

}
