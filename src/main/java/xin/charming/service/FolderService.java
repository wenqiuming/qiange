package xin.charming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.charming.bean.Folder;
import xin.charming.bean.Tag;
import xin.charming.bean.TagFolder;
import xin.charming.bean.User;
import xin.charming.dao.FolderDao;
import xin.charming.dao.TagDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {
    @Autowired
    private FolderDao dao;
    @Autowired
    private TagDao tagDao;

    /**
     * 获取默认文件夹
     * uid=1表示默认
     *
     * @return
     */
    public List<Folder> getDefaultFolder() {
        Folder param = new Folder();
        param.setUid(1);
        return dao.queryFolderByUid(param);
    }

    /**
     * 根据用户id查询所有文件夹
     *
     * @param uid
     * @return
     */
    public List<Folder> getFolderByUid(int uid) {
        Folder folder = new Folder();
        folder.setUid(uid);
        return dao.queryFolderByUid(folder);
    }

    /**
     * 添加文件夹
     *
     * @param folder
     */
    public void addFolder(Folder folder) {
        dao.insert(folder);
    }

    /**
     * 修改文件夹
     *
     * @param folder fname
     */
    public void modFolder(Folder folder) {
        dao.updateByPrimaryKey(folder);
    }

    /**
     * 删除文件夹
     *
     * @param folder
     */
    public void delFolder(Folder folder) {
        dao.deleteByPrimaryKey(folder);
    }

    public int getMaxOrderByFid(TagFolder tagFolder) {
        return dao.getMaxOrderbyWithFolderId(tagFolder);
    }

    public int getMaxId() {
        return dao.getMaxId();
    }

    /**
     * 初始化
     *
     * @param u
     */
    public void initFolderAndTag(User u) {
        //为账号添加默认类目
        List<Folder> folders = getDefaultFolder();
        List<Folder> newFolders = new ArrayList<Folder>();
        int maxId = getMaxId() + 1;
        for (int i = 0; i < folders.size(); i++) {
            Folder newFolder = new Folder();
            Folder folder = folders.get(i);
            newFolder.setId(maxId + i);
            newFolder.setUid(u.getId());
            newFolder.setFname(folder.getFname());
            //数据库添加
            addFolder(newFolder);
            newFolders.add(newFolder);
        }
        //添加默认tag
        for (int i = 0; i < folders.size(); i++) {
            Folder newFolder = newFolders.get(i);
            Folder folder = folders.get(i);
            TagFolder param = new TagFolder();
            param.setFolderId(folder.getId());
            List<TagFolder> tags = tagDao.queryTagByFolderId(param);
            for (TagFolder item : tags) {
                Tag tag = new Tag();
                tag.setBgColor(item.getBgColor());
                tag.setIcon(item.getIcon());
                tag.setTopName(item.getTopName());
                tag.setUrl(item.getUrl());
                tag.setTname(item.getTname());
                tag.setType("2");
                tag.setAuth(2);
                int tid = tagDao.getMaxId() + 1;
                tag.setId(tid);
                tagDao.insertTag(tag);
                TagFolder tf = new TagFolder();
                tf.setFolderId(newFolder.getId());
                tf.setTagId(tid);
                tf.setOrderby(item.getOrderby());
                dao.insertTagFolder(tf);
            }
        }

    }
}
