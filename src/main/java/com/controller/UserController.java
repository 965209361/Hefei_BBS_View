package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Hefei_bbs;
import com.model.User;
import com.service.HeFei_BBSService;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private HeFei_BBSService heFei_bbsService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/demoPage")
    public String demoPage() {
        return "demo";
    }

    @RequestMapping("/showUser")
    public void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        long userId = Long.parseLong(request.getParameter("id"));
        User user = this.userService.selectUser(userId);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(user));
        response.getWriter().close();
    }

    /*@RequestMapping("/demo")
    public String welcome(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        List<Hefei_bbs> Domainlist = new ArrayList<>();
        Domainlist = heFei_bbsService.QueryNews(1, 10);
        int pageNum = heFei_bbsService.QueryPageCount();
        pageNum = pageNum / 10 + 1;
        model.addAttribute("rows", Domainlist);
        model.addAttribute("page", 1);
        model.addAttribute("pageCount", pageNum);
        model.addAttribute("shoudao","列表信息传递完毕");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(model));
        response.getWriter().close();
        return "demo";
    }*/
    @RequestMapping("/demo")
    public String welcome(Model model) {
        List<Hefei_bbs> Domainlist = new ArrayList<>();
        Domainlist = heFei_bbsService.QueryNews(1, 10);
        int pageNum = heFei_bbsService.QueryPageCount();
        pageNum = pageNum / 10 + 1;
        model.addAttribute("rows", Domainlist);
        model.addAttribute("page", 1);
        model.addAttribute("pageCount", pageNum);
        model.addAttribute("shoudao", "文件信息传递完毕");
        return "demo";
    }

}