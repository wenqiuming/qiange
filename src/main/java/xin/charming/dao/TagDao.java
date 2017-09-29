package xin.charming.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.charming.bean.Folder;
import xin.charming.bean.Tag;
import xin.charming.bean.TagFolder;
import xin.charming.dao.TagDao;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TagDao extends SqlSessionDaoSupport {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private Logger LOGGER = Logger.getLogger(TagDao.class);

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public void insertTag(Tag tag) {
        this.getSqlSession().insert("tag.insertTag", tag);
    }

    public void deleteById(Tag tag) {
        this.getSqlSession().delete("tag.deleteByPrimaryKey", tag);
    }

    public void updateByPrimaryKey(Tag tag) {
        this.getSqlSession().update("tag.updateByPrimaryKey", tag);
    }

    public List<Tag> queryTag(Tag tag) {
        List<Tag> tags = this.getSqlSession().selectList("tag.queryTag", tag);
        return tags;
    }

    public List<TagFolder> queryTagByFolderId(TagFolder tagFolder) {
        LOGGER.info(String.format("===w===========FolderId=[%s]", tagFolder.getFolderId()));
        List<TagFolder> rs = this.getSqlSession().selectList("tag.queryTagByFolderId", tagFolder);
        return rs;
    }

    public int getMaxId() {
        int rs = 1;
        try {
            rs = this.getSqlSession().selectOne("tag.getMaxId");
        } catch (Exception e) {
            rs = 1;
        }
        return rs;
    }

    public int getFolderIdByTag(Tag tag) {
        int rs = this.getSqlSession().selectOne("tag.getFidByTag");
        return rs;
    }

    public Tag getByPrimaryKey(Tag tag) {
        Tag rs = this.getSqlSession().selectOne("tag.getByPrimaryKey", tag);
        return rs;
    }
}
