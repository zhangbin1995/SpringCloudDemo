package com.herobin.scd.feign;

import com.herobin.scd.common.ObjectResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author binzhang
 * @date 2019-11-06
 */
@FeignClient(name = "bank2")
public interface Bank2Client {

    @PostMapping(value = "/account/incre")
    ObjectResult increAccounts(
            @RequestParam(value = "cardNumber") String cardNumber
            , @RequestParam(value = "number") BigDecimal number);
}
