package org.mrl.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum TypeEnum {
    POST(0),
    COMMENT(1),
    USER(2);

    @EnumValue
    private final Integer type;

    TypeEnum(Integer type) {
        this.type = type;
    }
}
