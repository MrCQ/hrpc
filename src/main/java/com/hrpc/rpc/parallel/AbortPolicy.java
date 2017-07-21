package com.hrpc.rpc.parallel;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by changqi on 2017/7/15.
 */
public class AbortPolicy extends ThreadPoolExecutor.AbortPolicy {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        super.rejectedExecution(r, e);
    }
}
