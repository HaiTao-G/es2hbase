package com.haitao.es2eshbase.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MigrateServiceTest {

    @Autowired
    private MigrateService migrateService;

    @Test
    void migrat() {
        migrateService.run();
    }
}