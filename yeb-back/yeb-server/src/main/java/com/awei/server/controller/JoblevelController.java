package com.awei.server.controller;


import com.awei.server.pojo.Joblevel;
import com.awei.server.pojo.RespBean;
import com.awei.server.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJoblevels() {
        return joblevelService.list();
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public RespBean saveJoblevel(@RequestBody Joblevel joblevel) {
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "更新职位")
    @PutMapping("/")
    public RespBean updateJoblevel(@RequestBody Joblevel joblevel) {
        if (joblevelService.updateById(joblevel)) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除职位")
    @DeleteMapping("/{id}")
    public RespBean deleteJoblevel(@RequestParam Integer id) {
        if (joblevelService.removeById(id)) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public RespBean deleteJoblevelByIds(Integer[] ids) {
        if (joblevelService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
