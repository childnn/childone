package ${commandUrl};
import com.xq.live.commons.PageCommand;
import com.fasterxml.jackson.annotation.JsonFormat;
<#if isSwagger=="true" >
    import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
<#list pkgs as ps>
    <#if ps??>
        import ${ps};
    </#if>
</#list>


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Page${entityName}Command extends PageCommand  {



}
