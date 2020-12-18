package com.spring.common.config;

import com.spring.common.config.autoregister.ManualBean;
import com.spring.common.config.autoregister.ManualDIBean;
import com.spring.common.utils.ManualRegisterBeanUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName BeanRegisterAutoConfig
 * @Description 自动注册bean
 * @Author xuqianghui
 * @Date 2019/7/22 17:26
 * @Version 1.0
 */
@Configuration
public class BeanRegisterAutoConfig {

    public BeanRegisterAutoConfig(ApplicationContext applicationContext) {
        System.out.println("BeanRegisterAutoConf init: " + System.currentTimeMillis());
        registerManualBean((ConfigurableApplicationContext) applicationContext);
    }

    private void registerManualBean(ConfigurableApplicationContext applicationContext) {
        // 主动注册一个没什么依赖的Bean
        ManualBean manualBean = ManualRegisterBeanUtil.registerBean(applicationContext, "manualBean", ManualBean.class);
        manualBean.print("test print manualBean");

        // manualDIBean 内部，依赖由Spring容器创建的OriginBean
        ManualDIBean manualDIBean = ManualRegisterBeanUtil.registerBean(applicationContext, "manualDIBean",
                ManualDIBean.class, "依赖OriginBean的自定义Bean");
        manualDIBean.print("test print manualDIBean");
    }
}
