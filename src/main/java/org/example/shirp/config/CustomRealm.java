package org.example.shirp.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.example.shirp.constant.ShiroConstants;
import org.example.shirp.pojo.Permission;
import org.example.shirp.pojo.Role;
import org.example.shirp.pojo.User;
import org.example.shirp.service.PermissionService;
import org.example.shirp.service.RoleService;
import org.example.shirp.service.UserService;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/04
 **/
public class CustomRealm extends AuthorizingRealm {

  @Resource
  private UserService userService;
  @Resource
  private RoleService roleService;
  @Resource
  private PermissionService permissionService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    User user = (User) principalCollection.getPrimaryPrincipal();

    List<String> roles = roleService.getRoles(user.getUsername())
        .stream()
        .map(Role::getRoleName)
        .collect(Collectors.toList());

    List<String> permissionCodes = new ArrayList<>();
    for (String role : roles) {
      List<Permission> permissions = permissionService.getPermissions(role);
      getPermissionCodes(permissions, permissionCodes);
    }
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    simpleAuthorizationInfo.addRoles(roles);
    simpleAuthorizationInfo.addStringPermissions(permissionCodes);
    return simpleAuthorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    if (authenticationToken.getPrincipal() == null) {
      return null;
    }
    //获取用户信息
    String username = authenticationToken.getPrincipal().toString();
    User user = userService.getUser(username);
    if (user == null) {
      throw new UnknownAccountException("用户不存在...");
    }
    ByteSource credentialsSalt = ByteSource.Util.bytes(ShiroConstants.CREDENTIALS_SALT);
    return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
  }

  private List<String> getPermissionCodes(List<Permission> permissions, List<String> permissionsCodes) {
    if (permissions == null || permissions.isEmpty()) {
      return permissionsCodes;
    }
    for (Permission permission : permissions) {
      permissionsCodes.add(permission.getPermissionCode());
      getPermissionCodes(permission.getSubPermissions(), permissionsCodes);
    }
    return permissionsCodes;
  }


  public static void main(String[] args) {
    ByteSource credentialsSalt = ByteSource.Util.bytes(ShiroConstants.CREDENTIALS_SALT);
    System.out.println(new Md5Hash("123456", credentialsSalt).toString());
  }
}
