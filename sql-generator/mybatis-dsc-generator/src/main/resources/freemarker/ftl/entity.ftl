package ${entityUrl};
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xq.live.commons.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
<#if isSwagger=="true" >
import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
<#list pkgs as ps>
	<#if ps??>
import ${ps};
	</#if>
</#list>


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ${entityName} extends BaseModel {

<#list cis as ci>
  <#if ci.property!="isDeleted" && ci.property!="createTime"&& ci.property!="createBy"
    && ci.property!="updateBy"&& ci.property!="updateTime" >
      <#if ci.javaType=="Date">
          <#if ci.jdbcType=="date">
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
          <#elseif ci.jdbcType=="time">
  @DateTimeFormat(pattern = "HH:mm:ss")
  @JsonFormat(pattern="HH:mm:ss",timezone = "GMT+8")
          <#else>
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
          </#if>
      </#if>
      <#if ci.property=="id">
  @TableId(value = "id", type = IdType.AUTO)
      </#if>
      <#if isSwagger=="true" >
  @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}")
      </#if>
  private ${ci.javaType} ${ci.property};
  </#if>
</#list>
}
	