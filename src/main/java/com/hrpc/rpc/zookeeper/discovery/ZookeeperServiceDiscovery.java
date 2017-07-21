package com.hrpc.rpc.zookeeper.discovery;

import com.github.zkclient.IZkClient;
import com.github.zkclient.ZkClient;
import com.hrpc.rpc.zookeeper.ZookeeperConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by changqi.hcq on 2017/7/11.
 */
public class ZookeeperServiceDiscovery {
    private IZkClient zkClient;

    public ZookeeperServiceDiscovery(String zkAddr){
        zkClient = new ZkClient(zkAddr);
    }

    public String discover(String serviceName){
        String path = ZookeeperConstant.ZKROOT + "/" + serviceName;
        if(zkClient.exists(path)){
            List<String> list = zkClient.getChildren(path);
            if(!list.isEmpty()){
                return list.get(list.size()-1);
            }
        }

        return null;
    }
}
