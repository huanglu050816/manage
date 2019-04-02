package com.zwh.manage.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Data
public class BaseInfoForm {

    @NotNull(message = "所在区域未选择")
    @Length(min = 1, max = 64, message = "所在区域非法")
    private String location;
    @Min(value = 0, message = "线上投资金额非法")
    private int onlineAmount;
    @Min(value = 0, message = "线上投资金额非法")
    private int offlineAmount;
    @Length(min = 11, max = 11, message = "手机号必须为11位")
    private String mobile;
    @Length(max = 512, message = "留言不能超过500字")
    private String msg;

}
