package org.mrl.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.mrl.model.enums.TypeEnum;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Record implements Serializable {

    private Long id;

    private Long userId;

    private TypeEnum type;

    private Long typeId;

    private Boolean status;

    @TableField(fill = FieldFill.INSERT)
    private Date InsertTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date UpdateTime;
}
