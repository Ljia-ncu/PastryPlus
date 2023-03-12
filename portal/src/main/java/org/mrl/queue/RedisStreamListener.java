package org.mrl.queue;

import org.mrl.model.entity.Record;
import org.mrl.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class RedisStreamListener implements StreamListener<String, ObjectRecord<String, Record>> {
    @Autowired
    private RecordService recordService;

    @Override
    public void onMessage(ObjectRecord<String, Record> message) {
        recordService.handleRecordMessage(message.getValue(), message.getId());
    }
}
