package org.example.shirp.service;

import java.util.List;
import org.example.shirp.pojo.Role;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/11
 **/
public interface RoleService {

  /**
   * 获取用户角色
   *
   * @param username 用户名
   * @return list 角色集合
   */
  List<Role> getRoles(String username);

}
