package cn.linzs.app.common.utils.token.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author linzs
 * @Date 2017-12-18 11:12
 * @Description
 */
public class TokenModel implements Serializable {

    private String token; // 唯一字符标识token
    private Map<String, Object> dataMap; // 用户自定义数据
    private Long expire; // 过期时间，单位：秒
    private Long createTime; // 创建Token的时间

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
