package xin.charming.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import xin.charming.bean.User;

import javax.annotation.Resource;

@Component
public class UserDao extends SqlSessionDaoSupport {
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public int insertUser(User user) {
        return this.getSqlSession().insert("user.insertUser", user);
    }

    public User selectUserByAuth(User user) {
        return this.getSqlSession().selectOne("user.selectUserByAuth", user);
    }
}
