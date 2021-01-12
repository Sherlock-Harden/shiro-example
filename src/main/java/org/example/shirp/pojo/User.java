package org.example.shirp.pojo;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * user 用户表
 *
 * @author yuansijia
 */
@Data
@Builder
public class User implements Serializable {

  /**
   * 主键
   */
  private Long id;
  /**
   * 用户名
   */
  private String username;
  /**
   * 昵称
   */
  private String nickname;
  /**
   * 密码
   */
  private String password;
  /**
   * 角色
   */
  private List<Role> roles;

}
