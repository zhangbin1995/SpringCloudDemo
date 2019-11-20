package com.herobin.scd.service.impl;

import com.herobin.scd.common.ObjectResult;
import com.herobin.scd.dao.AccountDao;
import com.herobin.scd.entity.UserAccount;
import com.herobin.scd.service.AccountService;
import com.herobin.scd.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    /**
     * 保存/修改账户信息
     * @param userAccount
     * @return
     */
    @SuppressWarnings("all")
    public ObjectResult save(UserAccount userAccount) {
        userAccount.setCardNumber(String.valueOf(SnowflakeIdUtils.getInstance().nextId()));
        userAccount.setCreateTime(new Date());
        userAccount.setUpdateTime(new Date());
        return ObjectResult.success(accountDao.save(userAccount));
    }

    /**
     * 账户余额增加
     * @param account
     * @param number
     * @return
     */
    @Override
    public ObjectResult incre(String account, BigDecimal number) {
        UserAccount userAccount = accountDao.getByCardNumber(account);
        System.out.println(userAccount.toString());
        if (userAccount == null) {
            return ObjectResult.error("cardNumber is error");
        }
        userAccount.setAmount(userAccount.getAmount().add(number));
        return ObjectResult.success(accountDao.save(userAccount));
    }

    /**
     * 账户余额减少
     * @param cardNumber
     * @param number
     * @return
     */
    @Override
    public ObjectResult decre(String cardNumber, BigDecimal number) {
        UserAccount userAccount = accountDao.getByCardNumber(cardNumber);
        if (userAccount == null) {
            return ObjectResult.error("cardNumber is error");
        }
        userAccount.setAmount(userAccount.getAmount().subtract(number));
        return ObjectResult.success(accountDao.save(userAccount));
    }


    /**
     * 返回全部账户信息
     * @return
     */
    public ObjectResult queryAll() {
        List<UserAccount> userAccountList = accountDao.findAll();
        return ObjectResult.success(userAccountList);
    }
}
