package ${controllerUrl};

import com.xq.live.commons.Result;
import ${abstractControllerUrl}.${entityName}ControllerInterface;
import ${entityVoUrl}.${entityName}Vo;
import ${serviceUrl}.${entityName}Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
<#if isSwagger=="true" >
import io.swagger.annotations.Api;
</#if>
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
public class ${entityName}Controller implements ${entityName}ControllerInterface{


 @Autowired
 private ${entityName}Service ${lowerEntityName}Service;

 @Override
 public Result add${entityName}(@RequestBody @Valid Add${entityName}Command command){
  return ${lowerEntityName}Service.add${entityName}(command);
 }

 @Override
 public Result update${entityName}(@RequestBody @Valid Update${entityName}Command command){
  return ${lowerEntityName}Service.update${entityName}(command);
 }

 @Override
 public PageResult<${entityName}Vo> page${entityName}(@RequestBody @Valid Page${entityName}Command command){
  return ${lowerEntityName}Service.page${entityName}(command);
 }

 @Override
 public Result<${entityName}Vo> query${entityName}ById(@RequestBody @Valid LongCommand command){
  return ${lowerEntityName}Service.query${entityName}ById(command);
 }

 @Override
 public Result delete${entityName}ById(@RequestBody @Valid LongCommand command){
  return ${lowerEntityName}Service.delete${entityName}ById(command);
 }
}