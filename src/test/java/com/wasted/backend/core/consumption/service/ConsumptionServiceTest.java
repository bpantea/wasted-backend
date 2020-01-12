package com.wasted.backend.core.consumption.service;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Date;

@SpringBootTest
@Transactional
public class ConsumptionServiceTest {
    @Autowired
    private ConsumptionService service;

    @Test
    public void testNumberSec() throws InterruptedException {
        Date d1 = new Date();
        Thread.sleep(1000);
        Date d2 = new Date();
        long nrSec = (d2.getTime() - d1.getTime())/1000;
        Assert.isTrue(nrSec==1,"nrSecGresit");
    }



}
