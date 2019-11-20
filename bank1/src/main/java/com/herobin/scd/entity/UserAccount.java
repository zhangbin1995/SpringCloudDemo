package com.herobin.scd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_account")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ua_id")
    private Long uaId;

    // 开户人
    @Column(name = "account_holder")
    private String accountHolder;

    // 银行卡号
    @Column(name = "card_number")
    private String cardNumber;

    // 银行卡号
    @Column(name = "id_number")
    private String idNumber;

    // 余额
    @Column(name = "amount")
    private BigDecimal amount;

    // 预留手机号
    @Column(name = "phone")
    private String phone;

    // 开户时间
    @Column(name = "create_time")
    private Date createTime;

    // 最近一次操作时间
    @Column(name = "update_time")
    private Date updateTime;
}
