package dao.impl;

import dao.GameInfoDao;
import entity.GameInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GameInfoDaoImpl extends SqlSessionDaoSupport implements GameInfoDao {


    @Autowired
    @Qualifier("sqlSessionFactory")
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public void insert(GameInfo gameInfo) {
        this.getSqlSession().insert("dao.GameInfoDao.insert",gameInfo);
    }

    @Override
    public GameInfo select(String gameId) {
        return this.getSqlSession().selectOne("dao.GameInfoDao.select",gameId);
    }
}
