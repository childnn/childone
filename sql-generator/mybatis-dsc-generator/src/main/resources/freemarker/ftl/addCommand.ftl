package ${commandUrl};

import com.xq.live.commons.BaseCommand;

<#if isSwagger=="true" >
import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
<#list pkgs as ps>
    <#if ps??>
import ${ps};
    </#if>
</#list>


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Add${entityName}Command extends BaseCommand  {

<#list cis as ci>
    <#if ci.property!="isDeleted" && ci.property!="createTime"&& ci.property!="createBy"
    && ci.property!="updateBy"&& ci.property!="updateTime" &&ci.property!="id" &&ci.property!="platform"
    &&ci.property!="platformType">
        <#if isSwagger=="true" >
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}")
        </#if>
    private ${ci.javaType} ${ci.property};
    </#if>

</#list>

}
