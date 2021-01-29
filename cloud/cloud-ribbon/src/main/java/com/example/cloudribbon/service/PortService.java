package com.example.cloudribbon.service;

import com.example.cloudribbon.entity.PortEntity;
import com.example.cloudribbon.ribbon.PortRibbon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 20:15
 */
@Service
public class PortService {
    @Autowired
    private PortRibbon portRibbon;

    public List<PortEntity> ports() {
        return portRibbon.ports();
    }
}
