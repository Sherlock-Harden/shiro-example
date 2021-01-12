package org.example.shirp.service;

import org.example.shirp.pojo.User;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/11
 **/
public interface UserService {

  User getUser(String username);
}
