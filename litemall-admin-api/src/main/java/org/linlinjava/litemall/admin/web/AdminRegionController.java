package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.vo.RegionVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallRegion;
import org.linlinjava.litemall.db.domain.RegionQueryVo;
import org.linlinjava.litemall.db.service.LitemallRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/region")
@Validated
public class AdminRegionController {
    private final Log logger = LogFactory.getLog(AdminRegionController.class);

    @Autowired
    private LitemallRegionService regionService;

    @GetMapping("/clist")
    public Object clist(@NotNull Integer id) {
        List<LitemallRegion> regionList = regionService.queryByPid(id);
        return ResponseUtil.ok(regionList);
    }

    @GetMapping("/list")
    public Object getAllList(){
        List<RegionVo> result = new ArrayList<>();
        List<RegionQueryVo> litemallRegionList = regionService.getAllRegionInfo();
        boolean isFistProvince = true;
        RegionVo province = new RegionVo();
        RegionVo city = null;
        for (RegionQueryVo regionQueryVo: litemallRegionList) {
            if (isFistProvince) {
                province.setId(regionQueryVo.getProvinceId());
                province.setName(regionQueryVo.getProvinceName());
                province.setType(regionQueryVo.getProvinceType());
                province.setCode(regionQueryVo.getProvinceCode());
                isFistProvince = false;
            }
            RegionVo area = new RegionVo();
            // 同一省
            if (regionQueryVo.getProvinceCode() == province.getCode()) {
                if (city != null && regionQueryVo.getCityCode() != city.getCode()) {
                    province.getChildren().add(city);
                    city = new RegionVo();
                    city.setId(regionQueryVo.getCityId());
                    city.setName(regionQueryVo.getCityName());
                    city.setType(regionQueryVo.getCityType());
                    city.setCode(regionQueryVo.getCityCode());
                } else if (city == null) {
                    city = new RegionVo();
                    city.setId(regionQueryVo.getCityId());
                    city.setName(regionQueryVo.getCityName());
                    city.setType(regionQueryVo.getCityType());
                    city.setCode(regionQueryVo.getCityCode());
                }
                area.setId(regionQueryVo.getAreaId());
                area.setName(regionQueryVo.getAreaName());
                area.setType(regionQueryVo.getAreaType());
                area.setCode(regionQueryVo.getAreaCode());
                city.getChildren().add(area);
            } else {
                province.getChildren().add(city);
                result.add(province);
                province = new RegionVo();
                province.setId(regionQueryVo.getProvinceId());
                province.setName(regionQueryVo.getProvinceName());
                province.setType(regionQueryVo.getProvinceType());
                province.setCode(regionQueryVo.getProvinceCode());
                city = new RegionVo();
                city.setId(regionQueryVo.getCityId());
                city.setName(regionQueryVo.getCityName());
                city.setType(regionQueryVo.getCityType());
                city.setCode(regionQueryVo.getCityCode());
                area.setId(regionQueryVo.getAreaId());
                area.setName(regionQueryVo.getAreaName());
                area.setType(regionQueryVo.getAreaType());
                area.setCode(regionQueryVo.getAreaCode());
                city.getChildren().add(area);
            }
        }
        result.add(province);
        return ResponseUtil.ok(result);
    }
}
