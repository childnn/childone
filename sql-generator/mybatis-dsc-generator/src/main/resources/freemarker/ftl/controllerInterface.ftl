package ${controllerUrl};


import ${entityVoUrl}.${entityName}Vo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<#if isSwagger=="true" >
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import com.xq.live.commons.Result;
import com.xq.live.commons.PageResult;
import com.xq.live.commons.LongCommand;
import ${commandUrl}.Add${entityName}Command;
import ${commandUrl}.Update${entityName}Command;
import ${commandUrl}.Page${entityName}Command;

import javax.validation.Valid;

 <#if isSwagger=="true" >
@Api(description = "${entityComment}",value="${entityComment}" )
</#if>
@RestController
@RequestMapping("/${objectName}")
public interface ${entityName}ControllerInterface{

 @ApiOperation("新增")
 @PostMapping("/add${entityName}")
 Result add${entityName}(@RequestBody @Valid Add${entityName}Command command);

 @ApiOperation("编辑")
 @PostMapping("/update${entityName}")
 Result update${entityName}(@RequestBody @Valid Update${entityName}Command command);

 @ApiOperation("查询")
 @PostMapping("/page${entityName}")
 PageResult<${entityName}Vo> page${entityName}(@RequestBody @Valid Page${entityName}Command command);

 @ApiOperation("通过ID查询")
 @PostMapping("/query${entityName}ById")
 Result<${entityName}Vo> query${entityName}ById(@RequestBody @Valid LongCommand command);

 @ApiOperation("通过ID逻辑删除")
 @PostMapping("/delete${entityName}ById")
 Result delete${entityName}ById(@RequestBody @Valid LongCommand command);
}