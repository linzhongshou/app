package cn.linzs.app.common.exception;

/**
 * @Author linzs
 * @Date 2017-12-18 12:20
 * @Description
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
