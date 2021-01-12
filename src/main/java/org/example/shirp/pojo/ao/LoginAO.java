package org.example.shirp.pojo.ao;

import java.io.Serializable;
import lombok.Data;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/12
 **/
@Data
public class LoginAO implements Serializable {

  private String username;
  private String password;
  public Boolean rememberMe;

}
