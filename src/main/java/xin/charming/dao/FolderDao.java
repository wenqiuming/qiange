package xin.charming.dao;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.charming.bean.Folder;
import xin.charming.bean.Tag;
import xin.charming.bean.TagFolder;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FolderDao extends SqlSessionDaoSupport {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public List<Folder> queryFolderByUid(Folder folder) {
        List<Folder> rs = this.getSqlSession().selectList("folder.queryFolderByUid", folder);
        return rs;
    }

    public void insert(Folder folder) {
        this.getSqlSession().insert("folder.insert", folder);
    }

    public void updateByPrimaryKey(Folder folder) {
        this.getSqlSession().update("folder.updateByPrimaryKey", folder);
    }

    public void deleteByPrimaryKey(Folder folder) {
        this.getSqlSession().delete("folder.deleteByPrimaryKey", folder);
    }

    /*tagFolder*/
    public void insertTagFolder(TagFolder tagFolder) {
        this.getSqlSession().insert("folder.insertTagFolder", tagFolder);
    }

    public void updateTagFolderByTid(TagFolder tagFolder) {
        this.getSqlSession().update("folder.updateTagFolderByTid", tagFolder);
    }

    public void deleteTagFolder(TagFolder tagFolder) {
        this.getSqlSession().delete("folder.deleteTagFolder", tagFolder);
    }

    public int getMaxOrderbyWithFolderId(TagFolder tagFolder) {
        int rs = 1;
        try {
            rs = this.getSqlSession().selectOne("folder.getMaxOrderbyWithFolderId", tagFolder);
        } catch (Exception e) {
            rs = 1;
        }
        return rs;
    }

    public void insertTagFolderSimple(TagFolder tagFolder) {
        this.getSqlSession().insert("folder.insertTagFolderSimple", tagFolder);
    }

    public TagFolder queryTagFolderByTid(Tag tag) {
        TagFolder rs = this.getSqlSession().selectOne("folder.queryTagFolderByTid", tag);
        return rs;
    }

    public void updateOrderbyHeigher(TagFolder tagFolder) {
        this.getSqlSession().update("folder.updateOrderbyHeigher", tagFolder);
    }

    public int getMaxId(){
        return this.getSqlSession().selectOne("folder.getMaxId");
    }
}
