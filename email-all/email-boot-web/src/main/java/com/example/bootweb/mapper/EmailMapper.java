package com.example.bootweb.mapper;

import com.example.bootweb.pojo.EmailTemplate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/5 11:47
 */
@Repository
public interface EmailMapper {

    @Insert({
            "insert into email_template (subject, content)\n" +
                    "values (#{temp.subject}, #{temp.content})"
    })
    void insert(@Param("temp") EmailTemplate template);

    @Update({
            "update email_template\n" +
                    "set subject = #{temp.subject},\n" +
                    "    content = #{temp.content}\n" +
                    "where template_id = #{temp.templateId}"
    })
    void update(@Param("temp") EmailTemplate template);

    @Select({
            "SELECT template_id templateId, subject, content FROM email_template"
    })
    List<EmailTemplate> list();

    @Select({
            "SELECT template_id templateId, subject, content FROM email_template WHERE template_id = #{whatever}"
    })
    EmailTemplate getOne(Serializable id);

    @Delete({
            "DELETE\n" +
                    "FROM email_template\n" +
                    "WHERE template_id = #{whatever}"
    })
    void delete(Serializable id);
}
