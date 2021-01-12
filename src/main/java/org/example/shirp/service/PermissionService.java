package org.example.shirp.service;

import java.util.List;
import org.example.shirp.pojo.Permission;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/11
 **/
public interface PermissionService {

  /**
   * 获取角色权限
   *
   * @param roleName 角色名称
   * @return list 权限列表
   */
  List<Permission> getPermissions(String roleName);
}
