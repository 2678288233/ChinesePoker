package dao;

import entity.UserBonus;

public interface UserBonusDao {
    void insert(UserBonus userBonus);
    void updateDelta(UserBonus userBonus);
    UserBonus select(String id);
}
