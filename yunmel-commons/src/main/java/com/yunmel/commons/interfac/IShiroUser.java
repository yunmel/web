/**
 * ©2016 yunmel,bjnsc Inc. All rights reserved.
 */
package com.yunmel.commons.interfac;

import java.io.Serializable;

/**
 * @author xuyq
 *
 */
public interface IShiroUser extends Serializable {
  public String getUserName();
  public String getUserId();
}
