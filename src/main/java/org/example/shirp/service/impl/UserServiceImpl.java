package org.example.shirp.service.impl;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.example.shirp.constant.ShiroConstants;
import org.example.shirp.pojo.User;
import org.example.shirp.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/11
 **/
@Service
public class UserServiceImpl implements UserService {

  @Override
  public User getUser(String username) {
    return User.builder()
        .id((long) username.length())
        .username(username)
        .nickname("nickname")
        .password(new Md5Hash("demoData", ByteSource.Util.bytes(ShiroConstants.CREDENTIALS_SALT)).toString())
        .build();
  }
}
