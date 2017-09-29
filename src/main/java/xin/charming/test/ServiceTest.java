package xin.charming.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xin.charming.bean.Engine;
import xin.charming.bean.Folder;
import xin.charming.bean.User;
import xin.charming.service.EngineService;
import xin.charming.service.FolderService;
import xin.charming.service.TagService;
import xin.charming.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ServiceTest {

    @Autowired
    private FolderService folderService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private EngineService engineService;

    @Test
    public void folderTest() {
        //测试查询
        //List<Folder> rs=folderService.getDefaultFolder();
        //System.out.print(rs.size());
    }

    @Test
    public void tagTest() {
        tagService.adjustOrderBy(24, 26);
    }

    @Test
    public void UserTest() {
        User user = new User();
        user.setLoginName("wqm");
        user.setPassword("123456");
        userService.addUser(user);
        User u = userService.queryUserByAuth(user);
        System.out.println(u);
    }

    @Test
    public void EngineTest() {
        List<Engine> rs = engineService.getEngines();
        System.out.println(rs.size());
    }
}
