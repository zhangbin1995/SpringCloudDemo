package com.herobin.scd.service.impl;

import com.google.common.base.Strings;
import com.herobin.scd.common.ObjectResult;
import com.herobin.scd.dao.AccountDao;
import com.herobin.scd.entity.UserAccount;
import com.herobin.scd.feign.Bank2Client;
import com.herobin.scd.service.AccountService;
import com.herobin.scd.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("all")

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private Bank2Client bank2Client;

    @Override
    public UserAccount getByCardNumber(String fromAccount) {
        return accountDao.getByCardNumber(fromAccount);
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        return accountDao.save(userAccount);
    }

    /**
     * 保存账户信息
     * @param userAccount
     * @return
     */
    public ObjectResult add(UserAccount userAccount) {
        userAccount.setCardNumber(String.valueOf(SnowflakeIdUtils.getInstance().nextId()));
        userAccount.setCreateTime(new Date());
        userAccount.setUpdateTime(new Date());
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

    /**
     * 账户余额增加
     * @param account
     * @param number
     * @return
     */
    @Override
    public ObjectResult incre(String account, BigDecimal number) {
        UserAccount userAccount = accountDao.getByCardNumber(account);
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
    @SuppressWarnings("all")
    public ObjectResult decre(String cardNumber, BigDecimal number) {
        UserAccount userAccount = accountDao.getByCardNumber(cardNumber);
        if (userAccount == null) {
            return ObjectResult.error("cardNumber is error");
        }
        userAccount.setAmount(userAccount.getAmount().subtract(number));
        return ObjectResult.success(accountDao.save(userAccount));
    }
}
