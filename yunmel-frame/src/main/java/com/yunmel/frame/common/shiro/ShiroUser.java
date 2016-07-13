/**
 *
 */
package com.yunmel.frame.common.shiro;

import java.io.Serializable;
import java.util.List;

public class ShiroUser implements Serializable {
	private static final long serialVersionUID = -2736720500600824155L;
	public String id;
    public String loginName;
    public String name;
    public List<String> roleList;

    public ShiroUser(String id, String loginName, String name, List<String> roleList) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.roleList = roleList;
    }

    public String getName() {
        return name;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return loginName;
    }
}