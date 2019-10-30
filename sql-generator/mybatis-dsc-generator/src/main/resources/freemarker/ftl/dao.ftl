package ${daoUrl};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import ${entityUrl}.${entityName};


@Mapper
public interface ${entityName}Dao extends BaseMapper<${entityName}> {
   /**
     * 批量新增
     **/
   void batchAdd(@Param("list")List<${entityName}> list);

}
	