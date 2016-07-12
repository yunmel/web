package com.yunmel.commons.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.jdbc.SqlBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

public class BaseMapperProvider extends MapperTemplate {

  public BaseMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
    super(mapperClass, mapperHelper);
  }

  public SqlNode beforeDeleteTreeStructureSql(MappedStatement ms) {
    // 修改返回值类型为实体类型
    setResultType(ms, Integer.class);
    List<SqlNode> sqlNodes = new ArrayList<SqlNode>();
    StaticTextSqlNode node = new StaticTextSqlNode(
        "select count(0) from #{t0} t0 ,#{t1} t1 where t1.parent_ids like concat('%,',#{id},',%') or t1.id=#{id} and t0.#{checkField} = t1.id");
    sqlNodes.add(node);

    return new MixedSqlNode(sqlNodes);
  }

  public SqlNode deleteByIDS(MappedStatement ms) {
    Class<?> entityClass = getEntityClass(ms);
    Set<EntityColumn> entityColumns = EntityHelper.getPKColumns(entityClass);
    EntityColumn entityColumn = null;
    Iterator<EntityColumn> it = entityColumns.iterator();
    if (it.hasNext()) {
      entityColumn = it.next();
    }
    EntityColumn column = entityColumn;
    List<SqlNode> sqlNodes = new ArrayList<SqlNode>();
    SqlBuilder.BEGIN();
    SqlBuilder.DELETE_FROM(tableName(entityClass));
    String sql = SqlBuilder.SQL();
    sqlNodes.add(new StaticTextSqlNode(sql + " WHERE " + column.getColumn() + " IN "));
    SqlNode foreach = new ForEachSqlNode(ms.getConfiguration(),
        new StaticTextSqlNode("#{" + column.getProperty() + "}"), "ids", "index",
        column.getProperty(), "(", ")", ",");

    sqlNodes.add(foreach);
    return new MixedSqlNode(sqlNodes);
  }
}
