package com.example.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

/**
 * @see ResponseStatus
 * @see ResponseEntity
 * -------------------------------------
 * @see MockMvcRequestBuilders
 * @see MockHttpServletRequestBuilder
 * @see ResultActions
 * @see MvcResult
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc // spring-boot-自动配置--MockMvc.
//@WebAppConfiguration // 用不用都可以?
public class MockApplicationTests {

    @Autowired
    private WebApplicationContext ctx;
    //    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    /**
     * 1、mockMvc.perform执行一个请求。
     * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
     * 3、ResultActions.param添加请求传值
     * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
     * 5、ResultActions.andExpect添加执行完成后的断言。
     * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
     * 比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
     * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
     */
    @Test
    public void contextLoads() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("")
                        .param("name", "jack")
                        .accept(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello,jack"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        System.out.println("status = " + status);
        String content = response.getContentAsString(StandardCharsets.UTF_8);
        System.out.println("content = " + content);

        Assert.assertEquals(200, status);
        Assert.assertEquals("hello,jack", content);

//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        validator.validate(, );
    }

}
