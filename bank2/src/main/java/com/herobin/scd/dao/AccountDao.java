package com.herobin.scd.dao;

import com.herobin.scd.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountDao extends JpaRepository<UserAccount,Long>, JpaSpecificationExecutor<UserAccount> {

    UserAccount getByCardNumber(String account);
}
