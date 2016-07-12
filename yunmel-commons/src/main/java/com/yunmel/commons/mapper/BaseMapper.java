package com.yunmel.commons.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 自定义mapepr
 * 
 * @author xuyq
 *
 * @param <T>
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>
{
  /**
   * 批量删除
   * 
   * @param ids 主键数组
   * @return
   */
  @DeleteProvider(type = BaseMapperProvider.class, method = "dynamicSQL")
  public abstract int deleteByIDS(@Param("ids") Object[] ids);

}
