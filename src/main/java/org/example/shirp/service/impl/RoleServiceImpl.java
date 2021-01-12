package org.example.shirp.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import org.example.shirp.pojo.Role;
import org.example.shirp.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/11
 **/
@Service
public class RoleServiceImpl implements RoleService {

  @Override
  public List<Role> getRoles(String username) {
    List<Role> roles = new LinkedList<>();
    for (int i = 0; i < username.length(); i++) {
      roles.add(Role.builder().id((long) i).roleName(username.getBytes(StandardCharsets.UTF_8)[i] + "").build());
    }
    return roles;
  }
}
