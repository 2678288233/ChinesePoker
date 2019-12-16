package exceptions;

public class NotInHomeException extends Exception {
    private static final long serialVersionUID = 5L;
    public NotInHomeException(String message){
        super(message);
    }

}
