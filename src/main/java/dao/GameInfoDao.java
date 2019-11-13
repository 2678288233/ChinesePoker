package dao;

import entity.GameInfo;
import org.apache.ibatis.annotations.Param;

public interface GameInfoDao {
    /**
     * 插入对局信息，传参需要是实体类。
     * ID，time和duration都不可为空。
     * @param gameInfo
     */
    void insert(GameInfo gameInfo);

    GameInfo select(@Param("gameId")String gameId);
}
