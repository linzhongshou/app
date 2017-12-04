package cn.linzs.app.common.dto;

/**
 * @Author linzs
 * @Date 2017-12-04 9:43
 * @Description
 */
public class ReturnResult {

    public final static class OperationCode {
        public final static int SUCCESS = 1;
        public final static int ERROR = 0;
        public final static int EXCEPTION = -1;
    }

    public final static class HttpCode {
        // 1xx（临时响应）
        // 表示临时响应并需要请求者继续执行操作的状态代码。
        public final static int _100 = 100; //（继续）请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。
        public final static int _101 = 101; //（切换协议）请求者已要求服务器切换协议，服务器已确认并准备切换。

        // 2xx（成功）
        // 表示成功处理了请求的状态代码。
        public final static int _200 = 200; //（成功）服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
        public final static int _201 = 201; //（已创建）请求成功并且服务器创建了新的资源。
        public final static int _202 = 202; //（已接受）服务器已接受请求，但尚未处理。
        public final static int _203 = 203; //（非授权信息）服务器已成功处理了请求，但返回的信息可能来自另一来源。
        public final static int _204 = 204; //（无内容）服务器成功处理了请求，但没有返回任何内容。
        public final static int _205 = 205; //（重置内容）服务器成功处理了请求，但没有返回任何内容。
        public final static int _206 = 206; //（部分内容）服务器成功处理了部分 GET 请求。

        // 3xx（重定向）
        // 表示要完成请求，需要进一步操作。 通常，这些状态代码用来重定向。
        public final static int _300 = 300; //（多种选择）针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。
        public final static int _301 = 301; //（永久移动）请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。
        public final static int _302 = 302; //（临时移动）服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。
        public final static int _303 = 303; //（查看其他位置）请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。
        public final static int _304 = 304; //（未修改）自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容。
        public final static int _305 = 305; //（使用代理）请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理。
        public final static int _307 = 307; //（临时重定向）服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。

        // 4xx（请求错误）
        // 这些状态代码表示请求可能出错，妨碍了服务器的处理。
        public final static int _400 = 400; //（错误请求）服务器不理解请求的语法。
        public final static int _401 = 401; //（未授权）请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
        public final static int _403 = 403; //（禁止）服务器拒绝请求。
        public final static int _404 = 404; //（未找到）服务器找不到请求的网页。
        public final static int _405 = 405; //（方法禁用）禁用请求中指定的方法。
        public final static int _406 = 406; //（不接受）无法使用请求的内容特性响应请求的网页。
        public final static int _407 = 407; //（需要代理授权）此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
        public final static int _408 = 408; //请求超时）服务器等候请求时发生超时。
        public final static int _409 = 409; //（冲突）服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
        public final static int _410 = 410; //（已删除）如果请求的资源已永久删除，服务器就会返回此响应。
        public final static int _411 = 411; //（需要有效长度）服务器不接受不含有效内容长度标头字段的请求。
        public final static int _412 = 412; //（未满足前提条件）服务器未满足请求者在请求中设置的其中一个前提条件。
        public final static int _413 = 413; //（请求实体过大）服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
        public final static int _414 = 414; //（请求的 URI 过长）请求的 URI（通常为网址）过长，服务器无法处理。
        public final static int _415 = 415; //（不支持的媒体类型）请求的格式不受请求页面的支持。
        public final static int _416 = 416; //（请求范围不符合要求）如果页面无法提供请求的范围，则服务器会返回此状态代码。
        public final static int _417 = 417; //（未满足期望值）服务器未满足”期望”请求标头字段的要求。

        // 5xx（服务器错误）
        // 这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。
        public final static int _500 = 500; //（服务器内部错误）服务器遇到错误，无法完成请求。
        public final static int _501 = 501; //（尚未实施）服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。
        public final static int _502 = 502; //（错误网关）服务器作为网关或代理，从上游服务器收到无效响应。
        public final static int _503 = 503; //（服务不可用）服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
        public final static int _504 = 504; //（网关超时）服务器作为网关或代理，但是没有及时从上游服务器收到请求。
        public final static int _505 = 505; //（HTTP 版本不受支持）服务器不支持请求中所用的 HTTP 协议版本。

    }

    private int code;
    private Object data;

    public ReturnResult() {}

    public ReturnResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
