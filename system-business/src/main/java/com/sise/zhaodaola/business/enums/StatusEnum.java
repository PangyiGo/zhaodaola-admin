package com.sise.zhaodaola.business.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 9:03
 * Description:
 */
@Getter
public enum StatusEnum {

    Enable(1,"激活"),

    Disable(2,"禁用");

    @EnumValue
    private final int status;

    private final String desc;

    StatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
