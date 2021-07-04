package io.kimmking.rpcfx.cglib;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.netty.NettyHttpClient;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author lzg
 * @Date 2021-06-29 18:51
 */
public class CglibProxy implements MethodInterceptor {

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
    private final Class serviceClass;
    private final String url;
    private final Filter[] filters;

    public CglibProxy(Class target, String url, Filter... filters) {
        this.serviceClass = target;
        this.url = url;
        this.filters = filters;
    }

    public Object getProxyInstance(Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }


    //intercept() 方法中的参数：Object为由CGLib生成的代理类实例；Method为上文中实体类所调用的被代理的方法引用；
// Object[]为参数值列表；MethodProxy 为生成的代理类对方法的代理引用
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before invoke ");

        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(this.serviceClass.getName());
        request.setMethod(method.getName());
        request.setParams(objects);
        method.getName();

        //执行目标对象的方法
//        Object returnValue = method.invoke(target, objects);

        if (null != filters) {
            for (Filter filter : filters) {
                if (!filter.filter(request)) {
                    return null;
                }
            }
        }

//        RpcfxResponse response = post(request, url);

        //改造成用netty http client
        RpcfxResponse response = postByNettyClient(request, url);

        // 加filter地方之三
        // Student.setTeacher("cuijing");

        if (!response.isStatus()) {
            return new RuntimeException();
        }
        // 这里判断response.status，处理异常
        // 考虑封装一个全局的RpcfxException

        return JSON.parse(response.getResult().toString());


//        return returnValue;
    }


//    private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
//        String reqJson = JSON.toJSONString(req);
//        System.out.println("requst json: " + reqJson);
//        // 1.可以复用client
//        // 2.尝试使用httpclient或者netty client
//        OkHttpClient client = new OkHttpClient();
//        final Request request = new Request.Builder().url(url)
//                .post(RequestBody.create(JSONTYPE, reqJson))
//                .build();
//        String respJson = client.newCall(request).execute().body().string();
//        System.out.println("resp json: " + respJson);
//        return JSON.parseObject(respJson, RpcfxResponse.class);
//    }


    private RpcfxResponse postByNettyClient(RpcfxRequest req, String url) throws Exception {
        String reqJson = JSON.toJSONString(req);
        System.out.println("requst json: " + reqJson);
        NettyHttpClient nettyHttpClient = NettyHttpClient.getInstance();
        nettyHttpClient.connect(url);
        URI uri = new URI(url);
        String postResult = nettyHttpClient.getPostResult(uri, reqJson);
        return JSON.parseObject(postResult, RpcfxResponse.class);
    }
}

