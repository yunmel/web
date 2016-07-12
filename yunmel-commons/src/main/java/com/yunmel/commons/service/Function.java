package com.yunmel.commons.service;

public abstract interface Function<E, T> {
  public abstract T callBack(E e);
}
