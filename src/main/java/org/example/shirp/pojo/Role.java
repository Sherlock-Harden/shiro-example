package org.example.shirp.pojo;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * role 角色表
 *
 * @author yuansijia
 */
@Data
@Builder
public class Role implements Serializable {

  /**
   * 主键
   */
  private Long id;
  /**
   * 角色名
   */
  private String roleName;
  /**
   * 权限
   */
  private List<Permission> permissions;
}
