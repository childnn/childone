package org.example.aop.listener;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AdvisedSupportListener;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2024/6/4
 */
public class MyAdvisedSupportListener implements AdvisedSupportListener {
    /**
     * Invoked when the first proxy is created.
     *
     * @param advised the AdvisedSupport object
     */
    @Override
    public void activated(AdvisedSupport advised) {
        System.out.println("advised.getAdvisors() = " + Arrays.asList(advised.getAdvisors()));
    }

    /**
     * Invoked when advice is changed after a proxy is created.
     *
     * @param advised the AdvisedSupport object
     */
    @Override
    public void adviceChanged(AdvisedSupport advised) {
        System.out.println("advised.getAdvisors() = " + Arrays.asList(advised.getAdvisors()));
    }

}
