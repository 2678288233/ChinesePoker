package dao;

import entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


public interface UserInfoDao {
    /** 传递整个实体对象作为参数。其中积分、胜负场次均为0. */
    void insert(UserInfo userInfo);

    /** 传递整个实体对象作为参数。如果是改密码，放入新密码，否则是null。
    * 如果要改胜负场次和积分，把int的值置为变化量，正数为加，负数为减。
    * 为0则不变。*/
    void update(UserInfo userInfo);

    /**
     * 传递用户名字来获取用户的所有信息
     * @param userName
     */
    UserInfo select(@Param("userName")String userName);
}
