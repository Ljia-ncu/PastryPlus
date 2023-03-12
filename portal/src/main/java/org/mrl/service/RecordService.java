package org.mrl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.annotation.Nonnull;
import org.mrl.model.entity.Record;
import org.mrl.model.enums.TypeEnum;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.transaction.annotation.Transactional;

public interface RecordService extends IService<Record> {

    Boolean checkRecord(@Nonnull TypeEnum type, @Nonnull Long typeId);

    Boolean pushRecordMessage(@Nonnull TypeEnum type, @Nonnull Long typeId, @Nonnull Boolean status);

    @Transactional
    void handleRecordMessage(Record message, RecordId messageId);
}
