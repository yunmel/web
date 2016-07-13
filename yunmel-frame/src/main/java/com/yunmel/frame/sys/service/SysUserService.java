

package com.yunmel.frame.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunmel.commons.exception.BaseException;
import com.yunmel.commons.service.BaseService;
import com.yunmel.commons.utils.Collections3;
import com.yunmel.commons.utils.DealParamUtil;
import com.yunmel.commons.utils.EncryptUtils;
import com.yunmel.commons.utils.PasswordEncoders;
import com.yunmel.frame.common.ErrorCode;
import com.yunmel.frame.consts.Constant;
import com.yunmel.frame.sys.exception.APIException;
import com.yunmel.frame.sys.mapper.SysOfficeMapper;
import com.yunmel.frame.sys.mapper.SysRoleMapper;
import com.yunmel.frame.sys.mapper.SysUserMapper;
import com.yunmel.frame.sys.model.SysOffice;
import com.yunmel.frame.sys.model.SysUser;
import com.yunmel.frame.sys.vo.SysUserVO;

/**
 * 
 * @author
 */
@Service("sysUserService")
public class SysUserService extends BaseService<SysUser> {
  private final static Logger LOG = LoggerFactory.getLogger(SysUserService.class);
  @Resource
  private SysUserMapper sysUserMapper;
  @Resource
  private SysRoleMapper sysRoleMapper;
  @Resource
  private SysOfficeMapper sysOfficeMapper;
  @Resource
  private SysConfigService sysConfigService;

  /**
   * 添加或更新用户
   * 
   * @param sysUser
   * @return
   */
  public int saveSysUser(SysUser sysUser) {
    if (sysUser.getId() != null) {// 修改-判断用户是否是admin用户
      SysUser user = this.selectByPrimaryKey(sysUser.getId());
      if (user.getUsername().equals(Constant.SYS_ADMIN_USER))
        return 0;
    } else {
      // 禁止添加admin用户
      if (sysUser.getUsername().equals(Constant.SYS_ADMIN_USER))
        return 0;
    }


    int count = 0;
    SysOffice sysOffice = sysOfficeMapper.findOfficeCompanyIdByDepId(sysUser.getUnitId());
    String companyId = sysUser.getUnitId();
    if (sysOffice != null) {
      companyId = sysOffice.getId();
    }
//    sysUser.setCompanyId(companyId);
    if (StringUtils.isNotBlank(sysUser.getPassword())) {
      String encryptPwd = PasswordEncoders.encrypt(sysUser.getPassword(), sysUser.getUsername());
      sysUser.setPassword(encryptPwd);
    } else {
      sysUser.remove("password");
    }
    if (null == sysUser.getId()) {
      sysUser.setStatus(Constant.USER_STATUS_NORMAL);
      count = this.insertSelective(sysUser);
    } else {
      sysRoleMapper.deleteUserRoleByUserId(sysUser.getId());
      count = this.updateByPrimaryKeySelective(sysUser);
    }
    if (sysUser.getRoleIds() != null)
      sysRoleMapper.insertUserRoleByUserId(sysUser);
    return 1;
  }

  /**
   * 删除用户
   * 
   * @param userId
   * @return
   */
  public int deleteUser(String userId) {
    SysUser sysUser = this.selectByPrimaryKey(userId);
    // 禁止操作admin用户
    if (sysUser.getUsername().equals(Constant.SYS_ADMIN_USER)) {
      return 0;
    }
    sysRoleMapper.deleteUserRoleByUserId(userId);
    return this.updateDelFlagToDelStatusById(SysUser.class, userId);
  }

  /**
   * 分页
   * 
   * @param params
   * @return
   */
  public PageInfo<SysUser> findPageInfo(Map<String, Object> params) {
    DealParamUtil.dealParam(params);
    PageHelper.startPage(params);
    List<SysUser> list = this.findByParams(params);
    return new PageInfo<SysUser>(list);
  }
  
  public List<SysUser> findAllUser() {
    return sysUserMapper.findAllUser();
  }

  /**
   * 不分页
   * @param map
   * @return
   */
  public List<SysUser> findByParams(Map<String, Object> map) {
    return sysUserMapper.findPageInfo(map);
  }

  /**
   * 验证用户
   * 
   * @param username 用户名
   * @param password 密码
   * @return user
   */
  public SysUser checkUser(String username, String password) {
    SysUser sysUser = new SysUser();
    String secPwd = PasswordEncoders.encrypt(password, username);
    sysUser.setUsername(username);
    sysUser.setPassword(secPwd);
    List<SysUser> users = this.select(sysUser);
    if (users != null && users.size() == 1 && users.get(0) != null) {
      return users.get(0);
    }
    return null;
  }

  public SysUser findUserByUsername(String username) {
    return sysUserMapper.getUserByUsername(username);
  }

//  public int updatePassword(SysUser user, String secPwd) {
//    SysPwdHistory sph = new SysPwdHistory();
//    sph.setUsername(user.getUsername());
//    sph.setPassword(user.getPassword());
//    sph.setCreateDate(new Date());
//    int count = sysPwdHistoryMapper.insertSelective(sph);
//
//    SysUser newUser = new SysUser();
//    newUser.setId(user.getId());
//    newUser.setUsername(user.getUsername());
//    newUser.setPassword(secPwd);
//
//    int ntime =
//        Integer.parseInt(sysConfigService.findByKeyFromRedis(SysConfigKey.PWD_NEXT_MOD_TIME));
//    if (ntime == 0) {
//      newUser.setNextModPwdDate(null);
//    } else {
//      newUser.setNextModPwdDate(DateTime.now().plusDays(ntime).toDate());
//    }
//
//
//    /**
//     * 为了解决第一用户登录后，必须修改密码才能进入系统，而判断用户是否是第一次进入系统的 标准是看getLastLoginDate的值是否为空。 如果用户修改了密码，必须将值修改后才能进入系统
//     */
//    if (user.getLastLoginDate() == null) {
//      newUser.setLastLoginDate(user.getLoginDate());
//      newUser.setLastLoginIp(user.getLoginIp());
//    }
//    count += this.updateByPrimaryKeySelective(newUser);
//    return count;
//  }

 

  public Integer updateStauts(String[] ids, String status) {
    int c = 0;
    for (String id : ids) {
      c += updateStauts(id, status, false);
    }
    if (c > 0) {
      // putRedis();
    }
    return c;
  }

  public Integer updateStauts(String id, String status) {
    return updateStauts(id, status, true);
  }

  private Integer updateStauts(String id, String status, boolean updateReids) {
    SysUser sysUser = this.selectByPrimaryKey(id);
    // 禁止操作admin用户
    if (sysUser.getUsername().equals(Constant.SYS_ADMIN_USER)) {
      return 0;
    }
    Integer _status = Integer.valueOf(status);
    if (Constant.USER_STATUS_NORMAL.equals(_status) || Constant.USER_STATUS_STOP.equals(_status)) {
      SysUser sd = new SysUser();
      sd.setId(id);
      sd.setStatus(_status);

      // 锁定的账户需要解锁
      if (Constant.USER_STATUS_NORMAL.equals(status)
          && Constant.USER_STATUS_LOCK.equals(sysUser.getStatus())) {
        sd.setStatus(Constant.USER_STATUS_NORMAL);
        sd.setErrorCount(0);
      }

      int c = this.updateByPrimaryKeySelective(sd);
      if (c > 0 && updateReids) {
        // putRedis();
      }
      return c;
    } else {
      return 0;
    }
  }


  public Integer resetPwd(String[] ids) {
    int c = 0;
    SysUser user = null;
    SysUser newUser = null;
    for (String id : ids) {
      user = selectByPrimaryKey(id);
      if (user != null) {
        newUser = new SysUser();
        newUser.setId(id);
        newUser.setPassword(PasswordEncoders.encrypt("z123456", user.getUsername()));
        c = this.updateByPrimaryKeySelective(newUser);
      }
    }
    return c;
  }

  public Integer deleteUser(String[] ids) {
    int c = 0;
    for (String id : ids) {
      c += deleteUser(id);
    }
    if (c > 0) {
      // putRedis();
    }
    return c;
  }

  public Integer addUser(SysUserVO sysUser, String officeId, String password) {
    if (officeId == null || sysUser == null || sysUser.getSysUser().isEmpty()) {
      return 0;
    }
    // String companyId = sysOfficeService.getCompanyIdByOfficeId(String.parseString(officeId));
    int c = 0;
    for (SysUser stu : sysUser.getSysUser()) {
      if (StringUtils.isNotBlank(stu.getUsername()) && StringUtils.isNotBlank(stu.getName())
          && !stu.getUsername().equals(Constant.SYS_ADMIN_USER)) {
        stu.setDelFlag(Constant.DEL_FLAG_NORMAL);
        stu.setStatus(Constant.USER_STATUS_NORMAL);
        stu.setUnitId(officeId);
        stu.setPassword(password);
//        stu.setCompanyId("");
        c += this.insertSelective(stu);
      }
    }
    return c;
  }

  
  
  /**
   * 移动端用户注册
   * 
   * @param username
   * @param password
   * @param name
   * @return
   * @throws UserNameExistException
   */
  public SysUser addUser(String username, String password, String name, String avatar,String unitId)
      throws APIException {
    SysUser user = findUserByUsername(username);
    if (null != user) {
      throw new APIException(ErrorCode.USERNAME_EXIST);
    }
    user = new SysUser();
    user.setUsername(username);
    user.setMobile(username);
    user.setUnitId(unitId);
    user.setPassword(EncryptUtils.SHA1_HEX(password));
    user.setName(name);
    user.setStatus(Constant.USER_STATUS_NORMAL);
    user.setAvatar("dl/avatar/" + avatar);
    Integer c = insertSelective(user);
    return user;
  }

  /**
   * 移动端登录
   * 
   * @param username
   * @param password
   * @return
   * @throws BaseException
   */
  public SysUser updateLogin(String username, String password) throws BaseException {
    SysUser user = findUserByUsername(username);
    if (null == user) {
      throw new APIException(ErrorCode.USER_NOT_EXIST);
    }
    if (!user.getPassword().equals(EncryptUtils.SHA1_HEX(password))) {
      throw new APIException(ErrorCode.USER_PASSWORD_ERROR);
    }
    return findUserByUsername(username);
  }
  
  

  public PageInfo<SysUser> findUnApproveUser(Map<String, Object> params) {
    DealParamUtil.dealParam(params);
    PageHelper.startPage(params);
    List<SysUser> list = sysUserMapper.findUserByStatus(params);
    return new PageInfo<SysUser>(list);
  }

  /**
   * 通过用户名和密码查询用户
   * @param username 用户名
   * @param password 密码
   * @return
   */
  public SysUser getByUserNameAndPwd(String username, String password) {
    SysUser record = new SysUser();
    record.setUsername(username);
    List<SysUser> users = this.select(record);
    if(Collections3.isEmpty(users)){
      return null;
    }
    
    SysUser user = users.get(0);
    if(user.getPassword().equals(EncryptUtils.SHA1_HEX(password))){
      return user;
    }
    return null;
  }
}
