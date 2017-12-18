package cn.linzs.app.common.exception;

/**
 * @Author linzs
 * @Date 2017-12-18 12:18
 * @Description
 */
public class ExpiredTokenException extends BaseException {

    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
