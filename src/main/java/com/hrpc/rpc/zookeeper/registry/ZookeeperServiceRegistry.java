package com.hrpc.rpc.zookeeper.registry;

import com.github.zkclient.IZkClient;
import com.github.zkclient.ZkClient;
import com.hrpc.rpc.exception.GlobalException;
import com.hrpc.rpc.model.ServiceRegistry;
import com.hrpc.rpc.spring.RpcServiceBean;
import com.hrpc.rpc.zookeeper.ZookeeperConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by changqi.hcq on 2017/7/11.
 */
public class ZookeeperServiceRegistry implements ServiceRegistry {
    private IZkClient zkClient;

    public ZookeeperServiceRegistry(String addr){
        zkClient = new ZkClient(addr);
    }

    @Override
    public boolean register(String nodePath) throws GlobalException{
        if(StringUtils.isEmpty(nodePath)){
            throw new GlobalException("service to be registered can not be null");
        }
        if(!removeNode(nodePath)){
            return false;
        }
        return addNode(nodePath);
    }

    @Override
    public boolean unRegister(String nodePath) {
        if(StringUtils.isEmpty(nodePath)){
            throw new GlobalException("service to be unregistered can not be null");
        }
        return removeNode(nodePath);
    }

    private boolean addNode(String nodePath){
        String path = ZookeeperConstant.ZKROOT + "/" + nodePath;

        if(!zkClient.exists(path)){
            zkClient.createPersistent(path);
        }

        return true;
    }
    private boolean removeNode(String nodePath){
        String path = ZookeeperConstant.ZKROOT + "/" + nodePath;

        if(!zkClient.exists(path)){
            return zkClient.delete(path);
        }

        return false;
    }
}
