package com.herobin.scd.configtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binzhang
 * @date 2019-11-06
 */
@RestController
public class ConfigClientController {

    @Autowired
    private ConfigInfoProperties configInfoValues;

    @RequestMapping("/getConfigInfo")
    public String getConfigInfo() {
        return configInfoValues.getConfig();
    }
}
