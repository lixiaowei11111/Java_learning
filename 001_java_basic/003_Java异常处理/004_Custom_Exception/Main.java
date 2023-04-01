public class Main {
    public static void main(String[] args) {

    }

    static void process1(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException();
        }
    }
}

class BaseException extends RuntimeException {
    public BaseException(){};
    public BaseException(String msg,Throwable cause){
        super(msg,cause);
    }
    public BaseException(String msg){
        super(msg);
    }
    public BaseException(Throwable cause){
        super(cause);
    }
};

class UserNotFoundException extends BaseException{};
class LoginFailedException extends BaseException{};