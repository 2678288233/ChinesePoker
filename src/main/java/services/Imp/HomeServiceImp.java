package services.Imp;

import entity.Room;
import entity.User;
import exceptions.NotInHomeException;
import services.HomeService;

import java.util.List;

public class HomeServiceImp implements HomeService {
    User user;
    public HomeServiceImp(User user){
        this.user=user;
    }


    @Override
    public void enterHome() {

        RoomDispatch.enterHome(user);
        user.setStatus(User.UserStatus.home);
    }

    @Override
    public void leaveHome() {
        try {
            RoomDispatch.leaveHome(user);
        } catch (NotInHomeException e) {
            //e.printStackTrace();
        }
    }

}
