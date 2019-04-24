package org.linlinjava.litemall.admin.web;

import com.github.pagehelper.PageInfo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallWithdrawApply;
import org.linlinjava.litemall.db.service.LitemallWithdrawApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现
 * @author sunyan
 * @date 2019/4/24
 */
@RestController
@RequestMapping("/admin/withdraw")
@Validated
public class AdminWithdrawController {

    @Autowired
    private LitemallWithdrawApplyService withdrawApplyService;

    /**
     * 审批通过
     * 注意：往用户微信钱包打款 需要到微信支付后台手动打款，为了安全考虑系统不提供自动打款功能
     * 此审批通过只会修改申请状态
     * @param id
     * @return
     */
    @PostMapping("/approve/{id}")
    public Object approve(@PathVariable(name = "id") Integer id){
        withdrawApplyService.approve(id);
        return ResponseUtil.ok();
    }

    /**
     * 审批拒绝
     * @param id
     * @return
     */
    @PostMapping("/reject/{id}")
    public Object reject(@PathVariable(name = "id") Integer id){
        withdrawApplyService.reject(id);
        return ResponseUtil.ok();
    }

    /**
     * 列表查询
     * @param page
     * @param limit
     * @param status
     * @param openid
     * @return
     */
    @GetMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       Byte status, String openid){
        List<LitemallWithdrawApply> applyList = withdrawApplyService.query(page, limit, status, openid);
        long total = PageInfo.of(applyList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", applyList);
        return ResponseUtil.ok(data);
    }

}
