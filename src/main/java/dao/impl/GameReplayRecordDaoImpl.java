package dao.impl;

import dao.GameReplayRecordDao;
import entity.GameReplayRecord;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameReplayRecordDaoImpl extends SqlSessionDaoSupport implements GameReplayRecordDao {

    @Autowired
    @Qualifier("sqlSessionFactory")
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public void insert(GameReplayRecord gameReplayRecord) {
        this.getSqlSession().insert("dao.GameReplayRecordDao.insert",gameReplayRecord);
    }

    @Override
    public List<GameReplayRecord> selectByGameID(String gameID) {

        return this.getSqlSession().selectList("dao.GameReplayRecordDao.selectByGameID",gameID);
    }
}
