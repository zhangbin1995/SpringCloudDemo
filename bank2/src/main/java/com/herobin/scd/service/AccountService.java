package com.herobin.scd.service;

import com.herobin.scd.common.ObjectResult;
import com.herobin.scd.entity.UserAccount;

import java.math.BigDecimal;

public interface AccountService {

    /**
     * 保存/修改用户信息
     * @param account
     * @return
     */
    ObjectResult save(UserAccount account);

    ObjectResult incre(String cardNumber, BigDecimal number);

    ObjectResult decre(String cardNumber, BigDecimal number);
}
