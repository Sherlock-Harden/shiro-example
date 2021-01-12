package org.example.shirp.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.example.shirp.pojo.ao.LoginAO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/12
 **/
@RestController
@RequestMapping("/user")
public class UserController {

  @PostMapping("/login")
  public String login(LoginAO login) {

    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(), login.getPassword(),
        login.getRememberMe());
    subject.login(token);
    boolean authenticated = subject.isAuthenticated();
    if (!authenticated) {
      throw new AuthenticationException();
    }
    return "success";
  }

}
