package com.example.bootweb.jsp;

import com.example.bootweb.mail.MailUtilPlus;
import com.example.bootweb.mapper.EmailMapper;
import com.example.bootweb.pojo.EmailTemplate;
import com.example.bootweb.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/5 10:16
 */
@Controller
public class JspController {

    @Autowired
    private EmailMapper mapper;

    @RequestMapping("/test")
    public ModelAndView jsp() {
        List<Student> list = new ArrayList<Student>() {
            private static final long serialVersionUID = 8284996318502207859L;

            {
                add(new Student().id(10).name("jack").sex("male"));
                add(new Student().id(8).name("rose").sex("female"));
            }
        };

        return new ModelAndView("index", "test", list);
    }

    @PostMapping("insert")
    public String insert(EmailTemplate template) {
        mapper.insert(template);
        return "send-email";
    }

    @GetMapping("/")
    public ModelAndView list() {
        List<EmailTemplate> list = mapper.list();
        return new ModelAndView("index", "list", list);
    }

    @GetMapping("getOne")
    public ModelAndView update(Integer id) {
        EmailTemplate one = mapper.getOne(id);
        System.err.println(one);
        return new ModelAndView("add-email-template", "one", one);
    }

    @PostMapping(value = "insertOrUpdate"/*, produces  = "text/plain;charset=UTF-8"*/)
    public ModelAndView insertOrUpdate(@RequestParam(required = false) Integer id, String subject, String content) {
        EmailTemplate template = new EmailTemplate();
        template.setSubject(subject).setContent(content).setTemplateId(id);
        System.err.println("template = " + template);
        if (id == null) {
            mapper.insert(template);
        } else {
            mapper.update(template);
        }

        return list();
    }

    @GetMapping("add")
    public String add() {
        return "add-email-template";
    }

    @GetMapping("send")
    public ModelAndView send(Integer id) {
        EmailTemplate one = mapper.getOne(id);
        return new ModelAndView("send-email", "one", one);
    }

    @GetMapping("delete")
    public ModelAndView delete(Integer id) {
        mapper.delete(id);
        return list();
    }

    @PostMapping("sendEmail")
    public ModelAndView send(String sender, String pwd, String receiver, String subject, String content, String params) {

        MailUtilPlus.sendMail(receiver, subject, content, sender, sender, pwd);

        return list();
    }

}
