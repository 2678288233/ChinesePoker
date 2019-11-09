package exceptions;

public class ReentryRoomException extends BaseException{
    private static final long serialVersionUID = 3L;
    public ReentryRoomException(String message){
        super(message);
    }
}
