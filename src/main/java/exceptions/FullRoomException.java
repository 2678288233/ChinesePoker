package exceptions;

public class FullRoomException extends BaseException {
    private static final long serialVersionUID = 4L;
    public FullRoomException(String message){
        super(message);
    }
}
