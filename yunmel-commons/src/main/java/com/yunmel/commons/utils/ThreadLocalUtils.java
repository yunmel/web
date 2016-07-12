package com.yunmel.commons.utils;

import java.util.Map;

import com.google.common.collect.Maps;

public class ThreadLocalUtils
{
  private static final ThreadLocal SESSION_MAP = new ThreadLocal();

  protected ThreadLocalUtils()
  {}

  public static Object get(String attribute)
  {
    Map map = (Map) SESSION_MAP.get();
    if (null != map)
    {
      return map.get(attribute);
    }
    return null;
  }

  public static <T> T get(String attribute, Class<T> clazz)
  {
    return (T) get(attribute);
  }

  public static void set(String attribute, Object value)
  {
    Map<String, Object> map = (Map<String, Object>) SESSION_MAP.get();

    if (map == null)
    {
      map = Maps.newHashMap();
      SESSION_MAP.set(map);
    }

    map.put(attribute, value);
  }

}
