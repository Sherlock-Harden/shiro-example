package org.example.shirp.pojo.ao;

import lombok.Data;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/12
 **/
@Data
public class LoginAO {

  private String username;
  private String password;
  public Boolean rememberMe;

}
