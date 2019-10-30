package ${serviceUrl};

import ${entityVoUrl}.${entityName}Vo;
import com.xq.live.commons.PageResult;
import com.xq.live.commons.LongCommand;
import com.xq.live.commons.Result;
import ${commandUrl}.Add${entityName}Command;
import ${commandUrl}.Update${entityName}Command;
import ${commandUrl}.Page${entityName}Command;

public interface ${entityName}Service{

 /**
   *新增
   **/
 Result add${entityName}(Add${entityName}Command command);

/**
  *修改
  **/
 Result update${entityName}(Update${entityName}Command command);

 /**
   *分页查询
   **/
 PageResult<${entityName}Vo> page${entityName}(Page${entityName}Command command);

 /**
   *通过ID查询
   **/
 Result<${entityName}Vo> query${entityName}ById(LongCommand command);

 /**
   *通过ID删除
   **/
 Result delete${entityName}ById(LongCommand command);
}