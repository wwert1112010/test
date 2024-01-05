package com.qj.websocket.controller;

import com.qj.websocket.service.WebSocketSever;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {


    @Resource
    ApplicationContext context;


    @GetMapping("test")
    @ResponseBody
    public String test() {
        Map<String, WebSocketSever> beansOfType = context.getBeansOfType(WebSocketSever.class);
        beansOfType.forEach((k, v) -> {
            System.out.println(k + ":" + v);
            System.out.println(v);
        });
        return "abc";
    }


    /**
     * 跳转到websocketDemo.html页面，携带自定义的cid信息。
     * http://localhost:8081/demo/toWebSocketDemo/user-1
     *
     * @param cid
     * @param model
     * @return
     */
    @GetMapping("/toWebSocketDemo/{cid}")
    public String toWebSocketDemo(@PathVariable String cid, Model model) {
        model.addAttribute("cid", cid);
        return "websocketDemo";
    }

}
