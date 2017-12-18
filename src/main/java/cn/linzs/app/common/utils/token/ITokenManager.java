package cn.linzs.app.common.utils.token;

import cn.linzs.app.common.utils.token.model.TokenModel;

import java.util.Map;

/**
 * Created by linzs on 2017-12-18 11:04
 *
 * @Description Token管理接口
 */
public interface ITokenManager {

    /**
     * 生成一个Token
     * */
    TokenModel generateToken(Map<String, Object> dataMap);

    /**
     * 获取TokenModel
     * */
    TokenModel getTokenModel(String token);

    /**
     * 检查Token是否过期
     *      true: 过期
     *      false: 有效期
     * */
    boolean expired(String token);

    /**
     * 刷新Token
     * */
    void refreshToken(String token);
}
