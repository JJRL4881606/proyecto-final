package exceptions;

@SuppressWarnings("serial")
public class RepositoryException extends RuntimeException{

    public RepositoryException(String message){
        super(message);
    }
}