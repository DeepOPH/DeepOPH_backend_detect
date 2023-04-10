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

package com.pig4cloud.pigx.files.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * files 表
 *
 * @author pigx code generator
 * @date 2022-12-01 17:19:20
 */
@Data
@TableName("files")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "files 表")
public class Files extends Model<Files> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="编号")
    private Long id;

    /**
     * 用户
     */
    @ApiModelProperty(value="用户")
    private String user;

    /**
     * 图片链接
     */
    @ApiModelProperty(value="图片链接")
    private String image;

    /**
     * 自测结果
     */
    @ApiModelProperty(value="自测结果")
    private String result;

    /**
     * 档案类型
     */
    @ApiModelProperty(value="档案类型")
    private String type;

    /**
     * 建立时间
     */
    @ApiModelProperty(value="建立时间")
    private LocalDateTime createTime;

    /**
     * 删除标记
     */
    @ApiModelProperty(value="删除标记")
    private String delFlag;

}
