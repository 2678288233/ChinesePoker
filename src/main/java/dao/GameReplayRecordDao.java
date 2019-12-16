package dao;

import entity.GameReplayRecord;

import java.util.List;

public interface GameReplayRecordDao {
    void insert(entity.GameReplayRecord gameReplayRecord);
    List<entity.GameReplayRecord> selectByGameID(String gameID);
}
