package xin.charming.service;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xin.charming.bean.Folder;
import xin.charming.bean.Tag;
import xin.charming.bean.TagFolder;
import xin.charming.dao.FolderDao;
import xin.charming.dao.TagDao;
import xin.charming.utils.ColorUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class TagService {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private FolderDao folderDao;
    @Autowired
    private ColorUtils colorUtils;
    private Logger LOGGER = Logger.getLogger(TagService.class);

    /**
     * 根据文件夹获取所有标签
     *
     * @param folderId
     * @return
     */
    public List<TagFolder> getTagListByFolderId(int folderId) {
        TagFolder tagFolder = new TagFolder();
        tagFolder.setFolderId(folderId);
        return tagDao.queryTagByFolderId(tagFolder);
    }

    public int addTag(Tag tag) {
        int id = tagDao.getMaxId() + 1;
        tag.setId(id);
        tagDao.insertTag(tag);
        return id;
    }

    public int getMaxId() {
        return tagDao.getMaxId();
    }

    public void addTagWithFolderId(TagFolder tagFolder, HttpServletRequest request, MultipartFile iconFile) throws IOException {
        String fn = "";
        Tag tag = new Tag();
        tag.setType("2");
        //保存上传的图片
        if (iconFile != null && iconFile.getSize() > 0) {
            fn = writeFileToUploadDir(request, iconFile.getInputStream(), iconFile.getOriginalFilename());
            tag.setType("1");
        }
        int id = tagDao.getMaxId() + 1;
        int orderby = folderDao.getMaxOrderbyWithFolderId(tagFolder) + 1;
        //组装数据
        tag.setId(id);
        tag.setTname(tagFolder.getTname());
        tag.setIcon(fn.equals("") ? tagFolder.getIcon() : fn);
        tag.setUrl(tagFolder.getUrl());
        tag.setAuth(2);
        if (tag.getIcon() == null || tag.getIcon().equals("")) {
            tag.setTopName(tag.getTname().substring(0, 1));
            tag.setBgColor(tagFolder.getBgColor() == null||tagFolder.getBgColor().equals("") ? colorUtils.getRandom() : tagFolder.getBgColor());
        }else {
            tag.setTopName("");
            tag.setBgColor("#ffffff");
        }

        //添加tag
        tagDao.insertTag(tag);
        //添加tag folder关系
        tagFolder.setTagId(id);
        tagFolder.setOrderby(orderby);
        folderDao.insertTagFolder(tagFolder);
    }

    private String writeFileToUploadDir(HttpServletRequest request, InputStream inputStream, String iconName) throws IOException {
        String subfix = iconName.substring(iconName.lastIndexOf("."));
        String dir = request.getSession().getServletContext().getRealPath("") + "/upload";
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + subfix;
        LOGGER.info("=========upload==fileName:" + fileName);
        File file = new File(dir, fileName);
        FileUtils.copyInputStreamToFile(inputStream, file);
        return request.getContextPath() + "/upload/" + fileName;
    }

    public void modTagWithFolderId(TagFolder tagFolder, HttpServletRequest request, MultipartFile iconFile) throws IOException {
        String fn = "";
        Tag tag = new Tag();
        tag.setId(tagFolder.getTagId());
        tag.setType("2");
        //保存上传的图片
        if (iconFile != null && iconFile.getSize() > 0) {
            fn = writeFileToUploadDir(request, iconFile.getInputStream(), iconFile.getOriginalFilename());
            tag.setType("1");
        }
        //组装数据
        tag.setTname(tagFolder.getTname());
        tag.setIcon(tag.getIcon() == null ? "" : tag.getIcon());
        tag.setIcon(fn.equals("") ? tagFolder.getIcon() : fn);
        tag.setUrl(tagFolder.getUrl());
        tag.setAuth(2);
        if (tag.getIcon() == null || tag.getIcon().equals("")) {
            tag.setTopName(tag.getTname().substring(0, 1));
        } else {
            tag.setTopName("");
        }
        String modBg = tagFolder.getBgColor();
        if (modBg != null && !modBg.equals("")) {
            if (tag.getIcon().equals("")&&(modBg.equals("#ffffff")||modBg.equals("#fff"))){
                tag.setBgColor(colorUtils.getRandom());
            }else{
                tag.setBgColor(modBg);
            }
        } else {
            // tag.setBgColor("#ffffff");
            tag.setBgColor(colorUtils.getRandom());
        }

        //添加tag
        tagDao.updateByPrimaryKey(tag);
        //添加tag folder关系
        folderDao.updateTagFolderByTid(tagFolder);
    }

    public void delTag(Tag tag) {
        tagDao.deleteById(tag);
        TagFolder tf = new TagFolder();
        tf.setTagId(tag.getId());
        folderDao.deleteTagFolder(tf);
    }

    /**
     *
     * @param moveId 交换参照点
     * @param fromId 发起交换点
     */
    public void adjustOrderBy(int moveId, int fromId) {
        Tag toTag = new Tag();
        Tag fromTag = new Tag();
        toTag.setId(moveId);
        fromTag.setId(fromId);
        //1拿到变换位置的顺序orderby
        TagFolder toTagFolder = folderDao.queryTagFolderByTid(toTag);
        TagFolder fromTagFolder = folderDao.queryTagFolderByTid(fromTag);
        //2将交换参照点顺序后面的+1
        folderDao.updateOrderbyHeigher(toTagFolder);
        //3.将需移动到该位置的orderby为该位置orderby+1
        int toOrderby=toTagFolder.getOrderby();
        int fromOrderby=fromTagFolder.getOrderby();

        if(toOrderby<=fromOrderby){//后往前
            fromTagFolder.setOrderby(toTagFolder.getOrderby());
            toTagFolder.setOrderby(toTagFolder.getOrderby()+1);
            folderDao.updateTagFolderByTid(fromTagFolder);
            folderDao.updateTagFolderByTid(toTagFolder);
        }else{//前往后
            fromTagFolder.setOrderby(toTagFolder.getOrderby()+1);
            folderDao.updateTagFolderByTid(fromTagFolder);
        }



    }
}

