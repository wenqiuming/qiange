package xin.charming.controller;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xin.charming.bean.*;
import xin.charming.dao.FolderDao;
import xin.charming.dao.TagDao;
import xin.charming.service.EngineService;
import xin.charming.service.FolderService;
import xin.charming.service.TagService;
import xin.charming.service.UserService;
import xin.charming.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class WelcomeController {
    @Resource
    private FolderService folderService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private EngineService engineService;
    private Logger LOGGER = Logger.getLogger(WelcomeController.class);

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("welcome");
        return mav;
    }

    @RequestMapping("/nav")
    public ModelAndView nav(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie coo : cookies) {
                String ckeName = coo.getName();
                String ckeVal = coo.getValue();
                if ("username".equals(ckeName)) {
                    //request.setAttribute("username", ckeVal);
                    String username = URLDecoder.decode(ckeVal, "utf-8");
                    request.getSession().setAttribute("username", username);
                }
                if ("uid".equals(ckeName)) {
                    //request.setAttribute("uid", ckeVal);
                    request.getSession().setAttribute("uid", ckeVal);
                }
            }
        }

        ModelAndView mav = new ModelAndView("nav");
        List<Folder> folders = null;
        String loginUid = (String) request.getSession().getAttribute("uid");
        if (null != loginUid) {
            folders = folderService.getFolderByUid(Integer.parseInt(loginUid));
        } else {
            folders = folderService.getDefaultFolder();
        }
        mav.addObject("folders", folders);
        mav.addObject("engines", engineService.getEngines());
        //request.setAttribute("folders",folders);
        return mav;
    }

    @RequestMapping("/queryTagsByFolder")
    @ResponseBody
    public List<TagFolder> queryTagsByFolder(HttpServletRequest request, int folderId) {
        List<TagFolder> rs = tagService.getTagListByFolderId(folderId);
        return rs;
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        if (StringUtils.isEmptyAndNull(username) || StringUtils.isEmptyAndNull(password)) {
            return "fail";
        } else {
            User user = new User(username, password);
            User u = userService.queryUserByAuth(user);
            if (u != null) {
                try {
                    setUserSession(request, response, u);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "fail";
                }
                return "success";
            } else {
                return "fail";
            }

        }
    }

    @RequestMapping("/doLogout")
    @ResponseBody
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            c.setMaxAge(0);
            response.addCookie(c);
        }
        request.getSession().invalidate();
        return "success";
    }

    @RequestMapping(value = "/doRegiste")
    @ResponseBody
    public String doRegiste(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        if (StringUtils.isEmptyAndNull(username) || StringUtils.isEmptyAndNull(password)) {
            return "用户名和密码不能为空";
        } else {
            User user = new User(username, password);
            if (userService.addUser(user)) {
                User u = userService.queryUserByAuth(user);
                folderService.initFolderAndTag(u);
                return "success";
            } else {
                return "该账号已存在";
            }

        }
    }

    private void setUserSession(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        //清空以前的session和cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            c.setMaxAge(0);
            response.addCookie(c);
        }
        request.getSession().invalidate();
        //设置session和cookie
        HttpSession session = request.getSession();
        session.setAttribute("uid", user.getId());
        session.setAttribute("username", user.getLoginName());

        String username = URLEncoder.encode(user.getLoginName(), "utf-8");
        Cookie nameCookie = new Cookie("username", username+"");
        Cookie uidCookie = new Cookie("uid", user.getId() + "");
        uidCookie.setMaxAge(60 * 60 * 24 * 30);
        nameCookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(uidCookie);
        response.addCookie(nameCookie);
    }

    @RequestMapping("/addFolder")
    @ResponseBody
    public String addFolder(HttpServletRequest request, HttpServletResponse response, String fname) {
        HttpSession session = request.getSession();
        int uid = Integer.parseInt(session.getAttribute("uid").toString());
        Folder folder = new Folder();
        folder.setUid(uid);
        folder.setFname(fname);
        folderService.addFolder(folder);
        return "success";
    }

    @RequestMapping("/addTag")
    @ResponseBody
    public String addTag(HttpServletRequest request, HttpServletResponse response, TagFolder tagFolder, @Param("iconFile") MultipartFile iconFile) {
        if (tagFolder.getFolderId() == 0) {
            return "请选择所属类目";
        }
        try {
            tagService.addTagWithFolderId(tagFolder, request, iconFile);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping("/modFolder")
    @ResponseBody
    public String modFolder(HttpServletRequest request, HttpServletResponse response, Folder folder) {
        folderService.modFolder(folder);
        return "success";
    }

    @RequestMapping("/delFolder")
    @ResponseBody
    public String delFolder(HttpServletRequest request, HttpServletResponse response, Folder folder) {
        if (folder.getId() == null) {
            return "fail";
        }
        folderService.delFolder(folder);
        return "success";
    }

    @RequestMapping("/modTag")
    @ResponseBody
    public String modTag(HttpServletRequest request, HttpServletResponse response, TagFolder tagFolder, @Param("iconFile") MultipartFile iconFile) {
        try {
            tagService.modTagWithFolderId(tagFolder, request, iconFile);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @RequestMapping("/delTag")
    @ResponseBody
    public String delTag(HttpServletRequest request, HttpServletResponse response, Tag tag) {
        tagService.delTag(tag);
        return "success";
    }

    @RequestMapping("/adjustOrderBy")
    @ResponseBody
    public String adjustOrderBy(HttpServletRequest request, HttpServletResponse response, int moveId, int fromId) {
        tagService.adjustOrderBy(moveId, fromId);
        return "success";
    }

    @RequestMapping("/getEngines")
    @ResponseBody
    public List<Engine> getEngines(HttpServletRequest request, HttpServletResponse response) {
        return engineService.getEngines();
    }
}
