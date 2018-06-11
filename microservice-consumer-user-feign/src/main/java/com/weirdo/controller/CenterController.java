package com.weirdo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ML.Zhang
 * @Date 2018/6/5
 * @Description
 */
@Controller
public class CenterController {

    //入口

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
    //头
    @RequestMapping(value = "/top")
    public String top(){
        return "top";
    }
    //身体
    @RequestMapping(value = "/main")
    public String main(){
        return "main";
    }
    //左
    @RequestMapping(value = "/left")
    public String left(){
        return "left";
    }
    //右
    @RequestMapping(value = "/right")
    public String right(){
        return "right";
    }
    //商品身体
    @RequestMapping(value = "/frame/product_main")
    public String product_main(){
        return "frame/product_main";
    }
    //商品身体--左
    @RequestMapping(value = "/frame/product_left.do")
    public String product_left(){
        return "frame/product_left";
    }
}
