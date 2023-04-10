/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.files.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.excel.annotation.ResponseExcel;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.files.entity.Files;
import com.pig4cloud.pigx.files.service.FilesService;
import com.pig4cloud.pigx.files.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * files 表
 *
 * @author pigx code generator
 * @date 2022-12-01 17:19:20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/files" )
@Api(value = "files", tags = "files 表管理")
public class FilesController {

    private final  FilesService filesService;

	@Resource
	private ModelService modelService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param files files 表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page{user}" )
    @PreAuthorize("@pms.hasPermission('files_files_view')" )
    public R getFilesPage(Page page, @PathVariable("user" ) String user,Files files) {
        return R.ok(filesService.page(page, Wrappers.query(files).eq("user",user)));
    }

    /**
     * 通过id查询files 表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('files_files_view')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(filesService.getById(id));
	}

    /**
     * 新增files 表
     * @param files files 表
     * @return R
     */
    @ApiOperation(value = "新增files 表", notes = "新增files 表")
    @SysLog("新增files 表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('files_files_add')" )
    public R save(@RequestBody Files files) {
		LocalDateTime date=LocalDateTime.now();
		files.setCreateTime(date);
        return R.ok(filesService.save(files));
    }

    /**
     * 修改files 表
     * @param files files 表
     * @return R
     */
    @ApiOperation(value = "修改files 表", notes = "修改files 表")
    @SysLog("修改files 表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('files_files_edit')" )
    public R updateById(@RequestBody Files files) {
		LocalDateTime date=LocalDateTime.now();
		files.setCreateTime(date);
    	return R.ok(filesService.updateById(files));
    }

    /**
     * 通过id删除files 表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除files 表", notes = "通过id删除files 表")
    @SysLog("通过id删除files 表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('files_files_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(filesService.removeById(id));
    }


    /**
     * 导出excel 表格
     * @param files 查询条件
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('files_files_export')" )
    public List<Files> export(Files files) {
        return filesService.list(Wrappers.query(files));
    }

}
