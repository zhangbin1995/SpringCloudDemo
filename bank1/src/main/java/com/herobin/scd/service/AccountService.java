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
    ObjectResult add(UserAccount account);

    /**
     * 返回全部用户信息
     * @return
     */
    ObjectResult queryAll();

    ObjectResult incre(String account, BigDecimal number);

    ObjectResult decre(String cardNumber, BigDecimal number);

    UserAccount getByCardNumber(String fromAccount);

    UserAccount save(UserAccount userAccount);
}
