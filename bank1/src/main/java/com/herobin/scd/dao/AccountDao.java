package com.herobin.scd.dao;

import com.herobin.scd.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface AccountDao extends JpaRepository<UserAccount,Long>, JpaSpecificationExecutor<UserAccount> {

    UserAccount getByCardNumber(String cardNumber);

}
