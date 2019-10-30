package ${entityVoUrl};
import com.xq.live.spi.common.SpiBaseVo;
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
public class ${entityName}Vo extends SpiBaseVo  {

<#list cis as ci>
    <#if ci.javaType=="Date">
        <#if ci.jdbcType=="date">
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
        <#elseif ci.jdbcType=="time">
    @JsonFormat(pattern="HH:mm:ss",timezone = "GMT+8")
        <#else>
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        </#if>
    </#if>
    <#if isSwagger=="true" >
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}")
    </#if>
    private ${ci.javaType} ${ci.property};

</#list>

}
