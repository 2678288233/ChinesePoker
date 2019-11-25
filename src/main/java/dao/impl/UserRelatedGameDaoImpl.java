package dao.impl;

import dao.UserRelatedGameDao;
import entity.UserRelatedGame;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRelatedGameDaoImpl extends SqlSessionDaoSupport implements UserRelatedGameDao {

    @Autowired
    @Qualifier("sqlSessionFactory")
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public void insert(UserRelatedGame userRelatedGame) {
        this.getSqlSession().insert("dao.UserRelatedGameDao.insert",userRelatedGame);
    }

    @Override
    public List<UserRelatedGame> selectByUser(String userId) {
        return this.getSqlSession().selectList("dao.UserRelatedGameDao.selectByUser",userId);
    }

    @Override
    public List<UserRelatedGame> selectByGame(String gameId) {
        return this.getSqlSession().selectList("dao.UserRelatedGameDao.selectByGame",gameId);
    }
}
