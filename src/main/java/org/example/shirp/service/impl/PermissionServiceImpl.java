package org.example.shirp.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.example.shirp.pojo.Permission;
import org.example.shirp.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/11
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

  @Override
  public List<Permission> getPermissions(String roleName) {

    List<Permission> permissions = new ArrayList<>();
    for (int i = 0; i < roleName.length(); i++) {
      List<Permission> subPermissions = new ArrayList<>();
      for (int j = 0; j < roleName.length(); j++) {
        Permission subPermission = Permission.builder()
            .id(((long) (i + 1) * roleName.length() + j))
            .pid((long) i)
            .permissionCode(roleName.getBytes(StandardCharsets.UTF_8)[i] + "")
            .permissionName(roleName.getBytes(StandardCharsets.UTF_8)[i] + "")
            .url("http://10.10.92.125:8080/" + i + "/" + ((long) (i + 1) * roleName.length() + j))
            .build();
        subPermissions.add(subPermission);
      }
      Permission permission = Permission.builder()
          .id((long) i)
          .pid(0L)
          .permissionCode(roleName.getBytes(StandardCharsets.UTF_8)[i] + "")
          .permissionName(roleName.getBytes(StandardCharsets.UTF_8)[i] + "")
          .url("http://10.10.92.125:8080/" + i + "/")
          .subPermissions(subPermissions)
          .build();
      permissions.add(permission);
    }
    return permissions;
  }
}
