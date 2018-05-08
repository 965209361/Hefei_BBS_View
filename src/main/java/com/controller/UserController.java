package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Hefei_bbs;
import com.model.User;
import com.service.HeFei_BBSService;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String welcome(ModelMap model) {
        List<Hefei_bbs> Domainlist = new ArrayList<>();
        Domainlist = heFei_bbsService.QueryNews(0, 10);
        int pageNum = heFei_bbsService.QueryPageCount();
        pageNum = pageNum / 10 + 1;
        model.addAttribute("rows", Domainlist);
        model.addAttribute("page", 1);
        model.addAttribute("pageCount", pageNum);
        model.addAttribute("shoudao", "文件信息传递完毕");
        return "demo";
    }

    @RequestMapping(value = "/getnews", method = RequestMethod.GET)
    public String getNews(@RequestParam("page") int page, @RequestParam("pageCount") int pageCount, ModelMap modelMap) {
        List<Hefei_bbs> Domainlist = new ArrayList<>();
//        heFei_bbsService.hefei_BBs();
        int pageFirst = (page-1) * pageCount ;
        Domainlist = heFei_bbsService.QueryNews(pageFirst, pageCount);
        int pageNum = heFei_bbsService.QueryPageCount();
        pageNum = pageNum / 10 + 1;
        modelMap.addAttribute("rows", Domainlist);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("pageCount", pageNum);
        modelMap.addAttribute("shoudao", "翻页完成");
        return "demo";
    }
   /* @RequestMapping(value = "/getnews", method = RequestMethod.GET)
    public void getNews(@RequestParam("page") int page, @RequestParam("pageCount") int pageCount, ModelMap modelMap, HttpServletRequest request,HttpServletResponse response)throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        List<Hefei_bbs> Domainlist = new ArrayList<>();
//        heFei_bbsService.hefei_BBs();
        int pageFirst = (page-1) * pageCount ;
        Domainlist = heFei_bbsService.QueryNews(page, pageCount);
        int pageNum = heFei_bbsService.QueryPageCount();
        pageNum = pageNum / 10 + 1;
        modelMap.addAttribute("rows", Domainlist);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("pageCount", pageNum);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(modelMap));
        response.getWriter().close();
    }*/

    @RequestMapping(value = "/UpAndDownPage", method = RequestMethod.GET)
    public String UpAndDownPage(@RequestParam("page") int page, @RequestParam("pageCount") int pageCount, @RequestParam("param") int param, ModelMap modelMap) {
        /**
         * param为1则上一页,param为2则下一页
         */
        if (param == 1) {
            page = page - 1;
        }
        if (param == 2) {
            page = page + 1;
        }
        List<Hefei_bbs> Domainlist = new ArrayList<>();
        int pageFirst = (page-1) * pageCount ;
        Domainlist = heFei_bbsService.QueryNews(pageFirst, pageCount);
        int pageNum = heFei_bbsService.QueryPageCount();
        pageNum = pageNum / 10 + 1;
        modelMap.addAttribute("rows", Domainlist);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("pageCount", pageNum);
        modelMap.addAttribute("shoudao", "操作完成");
        return "demo";
    }

}