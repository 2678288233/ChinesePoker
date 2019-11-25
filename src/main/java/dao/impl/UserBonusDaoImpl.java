package dao.impl;

import dao.UserBonusDao;
import entity.UserBonus;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserBonusDaoImpl extends SqlSessionDaoSupport implements UserBonusDao {

    @Autowired
    @Qualifier("sqlSessionFactory")
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public void insert(UserBonus userBonus) {
        this.getSqlSession().insert("dao.UserBonusDao.insert",userBonus);
    }

    @Override
    public void updateDelta(UserBonus userBonus) {
        this.getSqlSession().update("dao.UserBonusDao.updateDelta",userBonus);
    }

    @Override
    public UserBonus select(String id) {
        return this.getSqlSession().selectOne("dao.UserBonusDao.select",id);
    }
}
