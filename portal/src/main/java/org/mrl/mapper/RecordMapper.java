package org.mrl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mrl.model.entity.Record;

public interface RecordMapper extends BaseMapper<Record> {

    Record isExist(@Param(value = "userId") Long userId,
                   @Param(value = "type") Integer type,
                   @Param(value = "typeId") Long typeId);

    void updateStatus(@Param("id") Long id, @Param("status") Boolean status);
}
