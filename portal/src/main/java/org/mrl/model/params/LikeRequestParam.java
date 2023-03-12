package org.mrl.model.params;

import lombok.Data;
import org.mrl.model.enums.TypeEnum;

@Data
public class LikeRequestParam {

    private TypeEnum type;

    private Long typeId;

    private Boolean status;
}
