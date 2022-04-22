package com.ssafy.dockerby.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

  /**
   * React router와 연동을 위한 Mapper
   */
  @GetMapping("/")
  public String router() {
    return "/index.html";
  }

}
