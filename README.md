[![](https://img.shields.io/badge/博客-进击的Z同学-success.svg)](https://blog.csdn.net/qq_24095055)&emsp;
[![](https://img.shields.io/badge/官网-SpringCloud-blueviolet.svg)](https://spring.io/projects/spring-cloud)&emsp;
[![](https://img.shields.io/badge/邮箱-herobin1995@163.com-red.svg)](mailto:herobin1995@163.com)&emsp;
[![](https://img.shields.io/badge/QQ-752658686-blue.svg)](#)

**SpringCloudDemo**

> 一个SpringCloud微服务模板

**所用微服务组件**

| 组件 | 方案 |
|--|--|
| 服务发现 | eureka |
| 网关 | Spring Cloud Zuul |
| 配置中心 | Spring Cloud Config |
| 共用组件 | 服务间调用组件Feign、负载均衡组件Ribbon、熔断器Hystrix |
| 搭配使用 | 分布式事务、容器化等 |

**介绍**

> 一个简单的微服务示例，功能模块主要分为多个银行之间的转账服务，各个银行有自己的微服务和数据库，要保持增减库存的一致性，验证分布式事务的安全问题。

**启动步骤**

程序可直接启动，步骤如下：
- 1.数据库脚本在resources/db文件夹下，本示例有bank1和bank2两个银行数据库，还有一个user的用户微服务和自己的数据库。
- 2.在application.yml中将数据库用户名密码等信息改为自己的。
- 3.启动程序。
- 4.可以在swagger中查看接口。https://127.0.0.1:port/swagger-ui.html

**若有问题可通过上面的邮箱或QQ等方式联系我~**
