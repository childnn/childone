package com.example.cloudfeign.service;

import com.example.cloudfeign.entity.PortEntity;
import com.example.cloudfeign.feign.PortFeign;
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
    private PortFeign portFeign;

    public List<PortEntity> ports() {
        return portFeign.ports();
    }
}
