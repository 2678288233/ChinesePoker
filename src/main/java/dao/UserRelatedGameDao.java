package dao;

import java.util.*;
import entity.UserRelatedGame;
import org.apache.ibatis.annotations.Param;

public interface UserRelatedGameDao {
    void insert(UserRelatedGame userRelatedGame);

    List<UserRelatedGame> selectByUser(@Param("userId")String userId);

    List<UserRelatedGame> selectByGame(@Param("gameId")String gameId);
}
