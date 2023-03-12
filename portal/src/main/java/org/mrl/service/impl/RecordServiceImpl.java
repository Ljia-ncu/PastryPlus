package org.mrl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mrl.mapper.RecordMapper;
import org.mrl.model.entity.Record;
import org.mrl.model.enums.TypeEnum;
import org.mrl.queue.RedisStreamProperties;
import org.mrl.service.RecordService;
import org.mrl.utils.Constants;
import org.mrl.utils.SecurityContext;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private RedisStreamProperties properties;

    @Override
    public Boolean checkRecord(TypeEnum type, Long typeId) {
        Long userId = SecurityContext.getCurrentUserID();
        String key = Constants.Portal.LIKE_RECORD_NAMESPACE + type.getType() + ":" + typeId + ":" + userId;
        String cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return Boolean.valueOf(cached);
        } else {
            long expire = ThreadLocalRandom.current().nextLong(3000, 5000);
            Record record = this.getBaseMapper().isExist(userId, type.getType(), typeId);
            boolean check = record != null && record.getStatus();
            redisTemplate.opsForValue().set(key, Boolean.toString(check), expire, TimeUnit.MILLISECONDS);
            return check;
        }
    }

    @Override
    public Boolean pushRecordMessage(TypeEnum type, Long typeId, Boolean status) {
        Long userId = SecurityContext.getCurrentUserID();
        Record record = new Record().setUserId(userId).setType(type).setTypeId(typeId).setStatus(status);
        ObjectRecord<String, Record> message = StreamRecords.newRecord().ofObject(record)
                .withStreamKey(properties.getKey());
        redisTemplate.opsForStream().add(message);
        return !status;
    }

    @Override
    public void handleRecordMessage(Record message, RecordId messageId) {
        Record record = this.getBaseMapper().isExist(message.getUserId(), message.getType().getType(), message.getTypeId());
        if (record == null) {
            this.save(message);
        } else if (record.getStatus() != message.getStatus()) {
            this.getBaseMapper().updateStatus(record.getId(), message.getStatus());
        }
        redisTemplate.opsForStream().acknowledge(properties.getKey(), properties.getGroup(), messageId);
    }
}

