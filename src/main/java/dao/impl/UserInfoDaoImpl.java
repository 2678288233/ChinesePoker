package dao.impl;

import dao.UserInfoDao;
import entity.UserInfo;
import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


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

    @Override
    public void update(UserInfo userInfo) {
        this.getSqlSession().update("dao.UserInfoDao.update",userInfo);
    }

    @Override
    public UserInfo select(String id) {
        return this.getSqlSession().selectOne("dao.UserInfoDao.select",id);

    }

    @Override
    public UserInfo selectByName(String username) {
        return this.getSqlSession().selectOne("dao.UserInfoDao.selectByName",username);
    }

    @Override
    public void update(String userID,  boolean win,int score) {

        Map<String,String> para=new HashMap<>(3);

        para.put("userID",userID);
        para.put("score",String.valueOf(score));
        if (win) para.put("win","");
        else para.put("lose","");
        this.getSqlSession().update("dao.UserInfoDao.updateAfterGameOver",para);
    }


}
