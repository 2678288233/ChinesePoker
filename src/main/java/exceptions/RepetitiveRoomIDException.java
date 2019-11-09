package exceptions;

public class RepetitiveRoomIDException extends BaseException {
    private static final long serialVersionUID = 2L;
    public RepetitiveRoomIDException(String message){
        super(message);
    }
}
