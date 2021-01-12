package org.example.shirp.pojo;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * permission 权限表
 *
 * @author yuansijia
 */
@Data
@Builder
public class Permission implements Serializable {

  /**
   * 主键
   */
  private Long id;
  /**
   * 上级ID
   */
  private Long pid;
  /**
   * 权限名
   */
  private String permissionName;
  /**
   * 权限编码
   */
  private String permissionCode;
  /**
   * 地址
   */
  private String url;
  /**
   * 子权限
   */
  private List<Permission> subPermissions;

}
