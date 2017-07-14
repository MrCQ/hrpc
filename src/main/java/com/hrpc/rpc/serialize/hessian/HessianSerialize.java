package com.hrpc.rpc.serialize.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.hrpc.rpc.serialize.RpcSerialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by changqi on 2017/7/14.
 */
public class HessianSerialize implements RpcSerialize {

    @Override
    public void serialize(OutputStream outputSteam, Object object) throws IOException {
        Hessian2Output output = new Hessian2Output(outputSteam);
        try {
            output.startMessage();
            output.writeObject(object);
            output.completeMessage();
            output.close();
            outputSteam.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object deserialize(InputStream inputStream) throws IOException {
        Hessian2Input input = new Hessian2Input(inputStream);
        try {
            input.startMessage();
            Object obj = input.readObject();
            input.completeMessage();
            input.close();
            inputStream.close();

            return obj;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
