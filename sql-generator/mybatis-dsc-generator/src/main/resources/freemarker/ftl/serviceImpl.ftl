package ${serviceImplUrl};

import ${entityVoUrl}.${entityName}Vo;
import ${entityUrl}.${entityName};
import ${daoUrl}.${entityName}Dao;
import ${serviceUrl}.${entityName}Service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.xq.live.commons.PageResult;
import com.xq.live.commons.LongCommand;
import com.xq.live.commons.Result;
import com.xq.live.utils.BeanUtils;
import com.xq.live.commons.BaseEnum;
import ${commandUrl}.Add${entityName}Command;
import ${commandUrl}.Update${entityName}Command;
import ${commandUrl}.Page${entityName}Command;


@Service
public class ${entityName}ServiceImpl implements ${entityName}Service  {

 @Autowired
 private ${entityName}Dao ${lowerEntityName}Dao;

 /**
   *新增
   **/
 @Override
 public Result add${entityName}(Add${entityName}Command command){
   checkAdd${entityName}Command(command);
   ${entityName} insertEntity = BeanUtils.copyProperties(command,${entityName}.class);
   insertEntity.setCreatedProperties();
   ${lowerEntityName}Dao.insert(insertEntity);
   return Result.succeed("新增成功");
 }

 /**
   * 校验新增参数
   **/
 private void checkAdd${entityName}Command(Add${entityName}Command command){
   //TODO
 }

 /**
   *修改
   **/
 @Override
 public Result update${entityName}(Update${entityName}Command command){
   checkUpdate${entityName}Command(command);
   ${entityName} updateEntity = BeanUtils.copyProperties(command,${entityName}.class);
   updateEntity.setUpdatedProperties();
   ${lowerEntityName}Dao.updateById(updateEntity);
   return Result.succeed("编辑成功");
 }

 /**
   *校验修改参数
   **/
 private void checkUpdate${entityName}Command(Update${entityName}Command command){
   //TODO
 }

 /**
   *分页查询
   **/
 @Override
 public PageResult<${entityName}Vo> page${entityName}(Page${entityName}Command command){
  PageResult<${entityName}Vo> result = new PageResult<>();
  //TODO 编写查询逻辑

  return result;
 }

 /**
   *通过ID查询
   **/
 @Override
 public Result<${entityName}Vo> query${entityName}ById(LongCommand command){
   return Result.succeed(BeanUtils.copyProperties(${lowerEntityName}Dao.selectById(command.getId()),${entityName}Vo.class),"查询成功");
 }

 /**
   *通过ID删除
   **/
 @Override
 public Result delete${entityName}ById(LongCommand command){
   ${entityName} existsEntity = ${lowerEntityName}Dao.selectById(command.getId());
   if(existsEntity==null){
     return Result.failed("ID不存在");
   }
   ${entityName} deletedEntity = new ${entityName}();
   deletedEntity.setUpdatedProperties();
   deletedEntity.setId(command.getId());
   deletedEntity.setIsDeleted(BaseEnum.N.getCode());
   ${lowerEntityName}Dao.updateById(deletedEntity);
   return Result.succeed("逻辑删除成功");
 }
}