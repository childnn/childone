package org.anonymous.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/15 20:17
 */
@RestController
@RequestMapping("/my")
public class UserController {

    @GetMapping("/a")
    public String get() {
        return "hello world";
    }
}
