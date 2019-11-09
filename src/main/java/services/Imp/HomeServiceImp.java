package services.Imp;

import entity.Room;
import entity.User;
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
    }

    @Override
    public void leaveHome() {
        RoomDispatch.leaveHome(user);
    }

}
