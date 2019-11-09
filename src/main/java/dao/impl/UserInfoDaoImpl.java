package dao.impl;

import dao.UserInfoDao;
import entity.UserInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;



@Repository
public class UserInfoDaoImpl extends SqlSessionDaoSupport implements UserInfoDao {


    @Autowired
    @Qualifier("sqlSessionFactory")
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public void insert(UserInfo userInfo) {
        this.getSqlSession().insert("dao.UserInfoDao.insert",userInfo);
    }


}
