package xin.charming.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xin.charming.bean.Folder;
import xin.charming.bean.Tag;
import xin.charming.bean.TagFolder;
import xin.charming.dao.FolderDao;
import xin.charming.dao.TagDao;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DaoTest {

    @Autowired
    private FolderDao folderDao;
    @Autowired
    private TagDao tagDao;
    @Test
    public void FolderTest(){
        //添加
        Folder folder=new Folder();
        folder.setUid(3);
        folder.setFname("万里挑一");
       // folderDao.insert(folder);

        //查询
        Folder param=new Folder();
        //param.setUid(1);
        param.setFname("测试");
        param.setId(3);
       // param.setUid(24);
      //  List<Folder> rs=folderDao.queryFolderByUid(param);
        //更新
        //folderDao.updateByPrimaryKey(param);
        folderDao.deleteByPrimaryKey(param);

    }
    @Test
    public void tagTest(){
        //插入
        Tag tag=new Tag("tname","","url","1","bgcolor","topname",2);
        //tagDao.insertTag(tag);
        //更新
        Tag updateTag=new Tag();
        updateTag.setId(3);
        updateTag.setIcon("icon");
        updateTag.setType("2");
        //tagDao.updateByPrimaryKey(updateTag);
        //查询
        Tag queryParam=new Tag();
        queryParam.setId(2);
//       List<Tag> queryTags=tagDao.queryTag(queryParam);
//        System.out.println(queryTags);
        //删除
        Tag delParam=new Tag();
//        delParam.setId(3);
//        tagDao.deleteById(delParam);
        //关联查询
        TagFolder tagFolder=new TagFolder();
        tagFolder.setFolderId(1);
        //tagDao.queryTagByFolderId(tagFolder);
        //tagDao.getMaxId();
    }
    @Test
    public void tagFolderTest(){
        //插入
        TagFolder insertParam=new TagFolder();
        insertParam.setFolderId(2);
        insertParam.setOrderby(2);
        insertParam.setTagId(2);
       // folderDao.insertTagFolder(insertParam);
       // folderDao.insertTagFolder(insertParam);
        //查询
        TagFolder queryParam=new TagFolder();
        queryParam.setFolderId(1);
        queryParam.setTagId(3);
        queryParam.setOrderby(222);
        //tagDao.queryTagByFolderId(queryParam);
       // folderDao.updateTagFolderByTid(queryParam);
        //folderDao.deleteTagFolder(queryParam);
        int rs=folderDao.getMaxOrderbyWithFolderId(queryParam);
        System.out.println(rs);
    }

}
