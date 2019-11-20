package com.herobin.scd.controller;

import com.herobin.scd.common.ObjectResult;
import com.herobin.scd.entity.UserAccount;
import com.herobin.scd.feign.Bank2Client;
import com.herobin.scd.service.AccountService;
import com.herobin.scd.utils.SnowflakeIdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@SuppressWarnings("all")
@Api(value = "账户服务", tags = "账户服务")
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private Bank2Client bank2Client;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     * @param queueName 队列名称
     */
    public void send(String queueName) {
        String msg = "my_fanout_msg:" + System.currentTimeMillis();
        //设置消息头
        Message message = MessageBuilder.withBody(msg.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8").setMessageId(UUID.randomUUID() + "").build();
        System.out.println(msg + ":" + msg);
        amqpTemplate.convertAndSend(queueName, message);
    }

    @ApiOperation(value = "发起转账", notes = "发起转账")
    @PostMapping(value = "/transferByMQ")
    public ObjectResult transferAccountsByMQ(@RequestParam String fromAccount,
                                             @RequestParam String toAccount,
                                             @RequestParam BigDecimal number) {
        UserAccount userAccount = accountService.getByCardNumber(fromAccount);
        if (userAccount == null) {
            return ObjectResult.error("cardNumber is error");
        }
        userAccount.setAmount(userAccount.getAmount().subtract(number));
        UserAccount account1 = accountService.save(userAccount);
        UserAccount transferInfo = new UserAccount();
        transferInfo.setCardNumber(toAccount);
        transferInfo.setAmount(number);
        // 这里我们传送的exchange为transfer-exchange，记得要去RabbitMQ的控制台创建好 配置好和队列的绑定关系
        amqpTemplate.convertAndSend("transfer-exchange", transferInfo);
        return ObjectResult.success(account1.getAmount());
    }

    @ApiOperation(value = "添加账户", notes = "添加用户")
    @PostMapping(value = "/add")
    public ObjectResult add(@RequestBody UserAccount account) {

        return accountService.add(account);
    }

    @ApiOperation(value = "发起转账", notes = "发起转账")
    @PostMapping(value = "/transfer")
    public ObjectResult transferAccounts(@RequestParam String fromAccount,
                                         @RequestParam String toAccount,
                                         @RequestParam BigDecimal number) {
        UserAccount userAccount = accountService.getByCardNumber(fromAccount);
        UserAccount backAccount = new UserAccount(userAccount.getUaId(),userAccount.getAccountHolder(),
                userAccount.getCardNumber(),userAccount.getIdNumber(),
                userAccount.getAmount(),userAccount.getPhone(),
                userAccount.getCreateTime(),userAccount.getUpdateTime());
        if (userAccount == null) {
            return ObjectResult.error("cardNumber is error");
        }
        userAccount.setAmount(userAccount.getAmount().subtract(number));
        UserAccount account1 = accountService.save(userAccount);
        ObjectResult feignResult = null;
        try {
            feignResult = bank2Client.increAccounts(toAccount, number);
        } catch (RuntimeException e) {
            account1 = accountService.save(backAccount);
            return ObjectResult.error("转账失败(目标账户服务状态异常)！余额：" +
                    account1.getAmount());
        }
        if (feignResult == null || !feignResult.getCode()
                .equals(ObjectResult.SUCCESS.getCode())) {
            account1 = accountService.save(backAccount);
            return ObjectResult.error("转账失败(目标账户服务状态异常)！余额：" +
                    account1.getAmount());

        }
        return ObjectResult.success(account1.getAmount());
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
}
