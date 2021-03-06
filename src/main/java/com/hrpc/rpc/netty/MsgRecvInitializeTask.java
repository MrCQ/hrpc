package com.hrpc.rpc.netty;

import com.hrpc.rpc.model.MsgRequest;
import com.hrpc.rpc.model.MsgResponse;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by changqi on 2017/7/11.
 */
public class MsgRecvInitializeTask implements Callable<Boolean> {
    private MsgRequest request;
    private MsgResponse response;

    public MsgRecvInitializeTask(MsgRequest request, MsgResponse response){
        this.request = request;
        this.response = response;
    }

    @Override
    public Boolean call() {
        response.setMessageId(request.getMessageId());
        try {
            Object res = invoke(request);
            response.setResult(res);
            return true;
        } catch(Exception e){
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        return false;
    }

    private Object invoke(MsgRequest request) throws Exception{
        String methodName = request.getMethodName();
        Object bean = MsgRecvExecutor.getInstance().getHandlerMap().get(request.getClassName());
        Object[] params = request.getParametersVal();

        return MethodUtils.invokeMethod(bean, methodName, params);

        // 避免使用 Java 反射带来的性能问题，我们使用 CGLib 提供的反射 API
//        FastClass serviceFastClass = FastClass.create(bean.getClass());
//        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, request.getTypeParameters());
//        Object result = serviceFastMethod.invoke(bean, params);
//        return result;
    }
}
