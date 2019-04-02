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
public class MemberForm {

    @NotNull(message = "姓名不能为空")
    @Length(min = 1, message = "姓名非法")
    private String name;
    @NotNull(message = "描述不能为空")
    @Length(min = 1, message = "描述非法")
    private String description;
    @Min(value = 0, message = "代收本金非法")
    private int amount;
    @Min(value = 0, message = "代收利息非法")
    private int benefit;

}
