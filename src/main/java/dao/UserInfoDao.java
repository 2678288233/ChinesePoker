package dao;

import entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


public interface UserInfoDao {
    /** 传递整个实体对象作为参数。其中积分、胜负场次均为0. */
    void insert(UserInfo userInfo);

    /** 传递整个实体对象作为参数。如果是改密码，放入新密码，否则是null。
    * */
    void update(UserInfo userInfo);

    /**
     * 传递id来获取用户的所有信息
     * @param id
     */
    UserInfo select(String id);
    UserInfo selectByName(String username);

}
