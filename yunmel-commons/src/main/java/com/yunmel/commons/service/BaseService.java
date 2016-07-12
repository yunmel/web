package com.yunmel.commons.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunmel.commons.component.SpringUtils;
import com.yunmel.commons.mapper.BaseMapper;
import com.yunmel.commons.model.BaseEntity;
import com.yunmel.commons.utils.Globle;
import com.yunmel.commons.utils.RandomUtils;
import com.yunmel.commons.utils.StrUtils;
import com.yunmel.commons.utils.ThreadLocalUtils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public abstract class BaseService<T extends BaseEntity> {
  @Autowired
  private BaseMapper<T> baseMapper;

  /**
   * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
   * 
   * @param <T extend T>
   */
  public List<T> select(T record) {
    record.set("delFlag", Globle.DEL_FLAG_NORMAL);
    return baseMapper.select(record);
  }

  public List<T> select(T record, String orderSqlStr) {
    Example example = new Example(record.getClass(), false);
    Criteria criteria = example.createCriteria();
    criteria.andEqualTo("delFlag", Globle.DEL_FLAG_NORMAL);
    for (Map.Entry<String, Object> entry : record.entrySet()) {
      if ("".equals(entry.getValue()))
        continue;
      criteria.andEqualTo(entry.getKey(), entry.getValue());
    }
    example.setOrderByClause(orderSqlStr);
    return baseMapper.selectByExample(example);
  }

  /**
   * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
   * 
   * @param <T extend T>
   */
  public int selectCount(T record) {
    record.set("delFlag", Globle.DEL_FLAG_NORMAL);
    return baseMapper.selectCount(record);
  }

  /**
   * 根据主键进行查询,必须保证结果唯一 单个字段做主键时,可以直接写主键的值 联合主键时,key可以是实体类,也可以是Map
   * 
   * @param <T extend T>
   */
  public T selectByPrimaryKey(Object key) {
    return baseMapper.selectByPrimaryKey(key);
  }

  /**
   * 插入一条数据 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写) 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
   * 
   * @param <T extend T>
   */
  public int insert(T record) {
    if (record.containsKey("createDate")) {
      record.set("createDate", System.currentTimeMillis());
    }
    if (record.containsKey("updateDate")) {
      record.set("updateDate", System.currentTimeMillis());
    }
    if (record.containsKey("delFlag")) {
      record.set("delFlag", Globle.DEL_FLAG_NORMAL);
    }
    record.setId(RandomUtils.genRandom32Hex());
    return baseMapper.insert(record);
  }

  /**
   * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
   * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
   * 
   * @param <T extend T>
   */
  public int insertSelective(T record) {
    if (StringUtils.isBlank(record.getString("createBy"))) {
      String user = (String) ThreadLocalUtils.get("user");
      record.set("createBy", user);
    }
    record.set("createDate", System.currentTimeMillis());
    record.set("updateDate", System.currentTimeMillis());
    record.set("delFlag", Globle.DEL_FLAG_NORMAL);
    record.setId(RandomUtils.genRandom32Hex());
    return baseMapper.insertSelective(record);
  }

  /**
   * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
   * 
   * @param <T extend T>
   */
  public int delete(T key) {
    return baseMapper.delete(key);
  }

  /**
   * 通过主键进行删除,这里最多只会删除一条数据 单个字段做主键时,可以直接写主键的值 联合主键时,key可以是实体类,也可以是Map
   * 
   * @param <T extend T>
   */
  public int deleteByPrimaryKey(Object key) {
    return baseMapper.deleteByPrimaryKey(key);
  }

  /**
   * 根据主键进行更新,这里最多只会更新一条数据 参数为实体类
   * 
   * @param <T extend T>
   */
  public int updateByPrimaryKey(T record) {
    // SysUser sysUser = SysUserUtils.getCacheLoginUser();
    // if(sysUser != null){
    // record.set("updateBy",sysUser.getId()+","+
    // SysUserUtils.getCacheLoginUser().getName());
    // }
    record.set("updateDate", new Date());
    return baseMapper.updateByPrimaryKey(record);
  }

  /**
   * 根据主键进行更新 只会更新不是null的数据
   * 
   * @param <T extend T>
   */
  public int updateByPrimaryKeySelective(T record) {
    // SysUser user = SysUserUtils.getCacheLoginUser();
    String user = (String) ThreadLocalUtils.get("user");
    record.set("updateBy", user);
    record.set("updateDate", System.currentTimeMillis());
    return baseMapper.updateByPrimaryKeySelective(record);
  }

  /**
   * 单表逻辑删除(需要有delFlag)
   * 
   * @param bean 删除的实体类型
   * @return 影响行数
   */
  public <M extends BaseEntity> int updateDelFlagToDelStatusById(Class<M> bean, String id) {
    String mapperName = StringUtils.uncapitalize(bean.getSimpleName()) + "Mapper";
    Mapper<M> mapper = SpringUtils.getBean(mapperName);
    M m = null;
    try {
      m = bean.newInstance();
      m.setId(id);
      m.set("delFlag", Globle.DEL_FLAG_DELETE);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return mapper.updateByPrimaryKeySelective(m);
  }

  /**
   * 保存或者更新，根据传入id主键是不是null来确认
   * 
   * @param record
   * @return 影响行数
   */
  public int save(T record) {
    int count = 0;
    if (record.get("id") == null) {
      count = this.insertSelective(record);
    } else {
      count = this.updateByPrimaryKeySelective(record);
    }
    return count;
  }

  /**
   * 单表分页
   * 
   * @param pageNum 页码
   * @param pageSize 条数
   * @param record 条件实体
   * @return
   */
  public PageInfo<T> selectPage(int pageNum, int pageSize, T record) {
    if (record.containsKey("delFlag")) {
      record.set("delFlag", Globle.DEL_FLAG_NORMAL);
    }
    PageHelper.startPage(pageNum, pageSize);
    return new PageInfo<T>(baseMapper.select(record));
  }

  /**
   * @Description:(单表分页可排序)
   * @param:@param pageNum
   * @param:@param pageSize
   * @param:@param record
   * @param:@param orderSqlStr (如:id desc)
   * @return:PageInfo<T>
   */
  public PageInfo<T> selectPage(int pageNum, int pageSize, T record, String orderSqlStr) {
    Example example = new Example(record.getClass(), false);
    Criteria criteria = example.createCriteria();
    if (record.containsKey("delFlag")) {
      criteria.andEqualTo("delFlag", Globle.DEL_FLAG_NORMAL);
    }
    for (Map.Entry<String, Object> entry : record.entrySet()) {
      if (entry.getValue() == null || "".equals(entry.getValue()))
        continue;
      criteria.andEqualTo(entry.getKey(), entry.getValue());
    }
    example.setOrderByClause(orderSqlStr);
    PageHelper.startPage(pageNum, pageSize);
    List<T> list = baseMapper.selectByExample(example);
    return new PageInfo<T>(list);
  }

  /**
   * 删除前验证是否有关联(仅限于单表)
   * 
   * @param bean 实体class
   * @param fields 检查的实体属性
   * @param values 属性值
   * @return -1有关联
   */
  public <M extends BaseEntity> int beforeDelete(Class<M> bean, Map<String, Object> params) {
    String mapperName = StringUtils.uncapitalize(bean.getSimpleName()) + "Mapper";
    Mapper<M> mapper = SpringUtils.getBean(mapperName);
    M m = null;
    try {
      m = bean.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    m.setAll(params);
    int count = mapper.selectCount(m);
    return count > 0 ? -1 : count;
  }


  /**
   * 有树结构的删除前验证(适应于两表)
   * 
   * @param id 删除的id
   * @param Field 验证的属性名称
   * @param beans class 第一个是要验证的class 第二个为删除的class
   * @return 未通过返回-1
   */
  public int beforeDeleteTreeStructure(Object id, String Field, Class<?>... beans) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("checkField", StrUtils.camelhumpToUnderline(Field));
    for (int i = 0; i < beans.length; i++) {
      Class<?> cl = beans[i];
      Table table = cl.getAnnotation(Table.class);
      if (table == null) {
        throw new RuntimeException("请配置table注释");
      }
      String tableName = table.name();
      // String tableName = StringConvert.camelhumpToUnderline(cl.getSimpleName());
      map.put("t" + i, tableName);
    }
    int count = 0; // baseMapper.beforeDeleteTreeStructure(map);
    return count > 0 ? -1 : count;
  }


  /**
   * 检查逻辑删除表的唯一属性
   * 
   * @param bean
   * @param checkField
   * @param value
   * @param id
   * @return
   */
  public <M extends BaseEntity> boolean unique(Class<M> bean, String checkField, Object value,
      Object id) {
    return unique(bean, checkField, value, id, null);
  }


  public <M extends BaseEntity> boolean unique(Class<M> bean, String checkField, Object value,
      Object id, Map<String, Object> params) {
    String mapperName = StringUtils.uncapitalize(bean.getSimpleName()) + "Mapper";
    Mapper<M> mapper = SpringUtils.getBean(mapperName);
    M m = null;
    try {
      m = bean.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    if (id != null) {
      m.set("id", id);
    }
    m.set(checkField, value);
    if (params != null) {
      m.setAll(params);
    }
    // int count = mapper.selectCount(m);
    if (id != null) {
      List<M> lists = mapper.select(m);
      if (lists.size() >= 2) {
        return false;
      } else if ((lists.size() == 1)) {
        return lists.get(0).get("id").toString().equals(id + "");
      } else {
        return true;
      }
    } else {
      int count = mapper.selectCount(m);
      return count <= 0 ? true : false;
    }
  }

  /**
   * 检查逻辑删除表的唯一属性,带状态的表
   * 
   * @param bean
   * @param checkField
   * @param value
   * @param id
   * @return
   */
  public <M extends BaseEntity> boolean uniqueHaveStatus(Class<M> bean, String checkField,
      Object value, Object id) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("status", Globle.SYSTEM_COMMON_ENABLE);
    return this.unique(bean, checkField, value, id, params);
  }

}
