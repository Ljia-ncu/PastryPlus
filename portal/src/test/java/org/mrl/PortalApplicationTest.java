package org.mrl;

import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.mrl.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class PortalApplicationTest {

    @Autowired
    RecordMapper recordMapper;

    @Test
    void testDB() {
        Map<String,Object> map = new HashMap<>();

    }

}
