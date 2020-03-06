package com.sise.zhaodaola.core;

import com.sise.zhaodaola.business.service.UserSerivce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {

    @Autowired
    private UserSerivce userSerivce;

    @Test
    void contextLoads() {
        System.out.println(userSerivce.list());
    }

}
