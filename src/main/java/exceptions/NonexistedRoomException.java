package exceptions;

public class NonexistedRoomException extends BaseException {
    private static final long serialVersionUID = 1L;
    public NonexistedRoomException(String message){
        super(message);
    }
}
