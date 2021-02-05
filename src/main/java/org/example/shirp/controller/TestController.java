package org.example.shirp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/12
 **/
@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/index")
  public String test() {
    return "index";
  }
}
