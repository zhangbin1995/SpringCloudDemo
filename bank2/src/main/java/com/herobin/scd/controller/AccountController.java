package com.herobin.scd.controller;

import com.herobin.scd.common.ObjectResult;
import com.herobin.scd.entity.UserAccount;
import com.herobin.scd.service.AccountService;
import com.rabbitmq.client.Channel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Api(value = "账户服务", tags = "账户服务")
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "添加账户", notes = "添加账户")
    @PostMapping(value = "/add")
    public ObjectResult save(@RequestBody UserAccount account) {

        return accountService.save(account);
    }

    @ApiOperation(value = "发起转账", notes = "发起转账")
    @PostMapping(value = "/transfer")
    public ObjectResult transferAccounts(@RequestParam String fromAccount, @RequestParam String toAccount, @RequestParam BigDecimal number) {
        return null;
    }

    @ApiOperation(value = "增加账户余额", notes = "增加账户余额")
    @PostMapping(value = "/incre")
    public ObjectResult increAccounts(@RequestParam(value = "cardNumber") String cardNumber, @RequestParam(value = "number") BigDecimal number) {
        return accountService.incre(cardNumber, number);
    }

    @ApiOperation(value = "减少账户余额", notes = "减少账户余额")
    @PostMapping(value = "/decre")
    public ObjectResult decreAccounts(@RequestParam String cardNumber, @RequestParam BigDecimal number) {
        return accountService.decre(cardNumber, number);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "transfer-queue", durable = "true"),
            exchange = @Exchange(name = "transfer-exchange", durable = "true", type = "topic"),
            key = "transfer.*"
    ))
    @RabbitHandler
    public void onTransferMessage(@Payload UserAccount transferInfo,
                                  @Headers Map<String, Object> headers,
                                  Channel channel) throws Exception {
        String cardNumber = transferInfo.getCardNumber();
        BigDecimal number = transferInfo.getAmount();
        ObjectResult result = accountService.incre(cardNumber,number);
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        if (result.getCode().equals(ObjectResult.SUCCESS.getCode())) {
            channel.basicAck(deliveryTag, false);
        }
    }

}
