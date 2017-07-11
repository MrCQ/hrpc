package com.hrpc.rpc.netty;

import com.hrpc.rpc.model.MessageRequest;
import com.hrpc.rpc.model.MessageResponse;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by changqi on 2017/7/11.
 */
public class MessageRecvInitializeTask implements Callable<Boolean> {
    private Map<String, Object> handlerMap;
    private MessageRequest request;
    private MessageResponse response;

    MessageRecvInitializeTask(Map<String, Object> map, MessageRequest request, MessageResponse response){
        this.handlerMap = map;
        this.request = request;
        this.response = response;
    }

    @Override
    public Boolean call() throws Exception {
        response.setMessageId(request.getMessageId());
        try {
            response.setReult(reflect(request));
            return true;
        } catch(Exception e){
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        return false;
    }

    private Object reflect(MessageRequest request) throws Throwable{
        String methodName = request.getMethodName();
        Object bean = handlerMap.get(request.getClassName());
        Object[] params = request.getParametersVal();

        return MethodUtils.invokeMethod(bean, methodName, params);

        // 避免使用 Java 反射带来的性能问题，我们使用 CGLib 提供的反射 API
//        FastClass serviceFastClass = FastClass.create(bean.getClass());
//        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, request.getTypeParameters());
//        Object result = serviceFastMethod.invoke(bean, params);
//        return result;
    }
}
