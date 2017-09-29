package xin.charming.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import xin.charming.bean.Engine;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EngineDao extends SqlSessionDaoSupport {
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public List<Engine> selectList() {
        List<Engine> rs = this.getSqlSession().selectList("engine.select");
        return rs;
    }
}
