package cn.linzs.app.common.exception;

/**
 * @Author linzs
 * @Date 2018-03-19 10:32
 * @Description
 */
public class FileNotFoundException extends BaseException {

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
