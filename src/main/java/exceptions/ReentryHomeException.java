package exceptions;

public class ReentryHomeException extends BaseException {
    private static final long serialVersionUID = 5L;
    public ReentryHomeException(String message){
            super(message);
        }

}
