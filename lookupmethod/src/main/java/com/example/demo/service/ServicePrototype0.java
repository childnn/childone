package com.example.demo.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/24 16:03
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServicePrototype0 {
}
