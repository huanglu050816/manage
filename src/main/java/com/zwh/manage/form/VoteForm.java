/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Data
public class VoteForm {

    @NotNull(message = "姓名不能为空")
    @Length(min = 1, message = "姓名非法")
    private String name;
    @Length(min = 11, max = 11, message = "手机号必须为11位")
    private String mobile;
    @Min(value = 0, message = "代收本金非法")
    private int amount;
    @Min(value = 0, message = "代收利息非法")
    private int benefit;
    @NotNull(message = "投票信息不能为空")
    @Length(min = 1, message = "投票信息非法")
    private String voteInfo;

}
