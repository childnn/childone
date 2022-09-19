package com.example.demo.config;

import com.example.demo.service.ServicePrototype;
import com.example.demo.service.ServiceSingleton;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.beans.factory.config.ServiceLocatorFactoryBean
 * @since 2022/04/23
 */
public interface ProtoFactory {

    ServicePrototype getServicePrototype();

    ServicePrototype getServicePrototype(String name);

    ServiceSingleton getServiceSingleton();

    // ... other bean

}
