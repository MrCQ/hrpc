package com.hrpc.rpc.spring.parser;

import com.hrpc.rpc.exception.ExceptionConstant;
import com.hrpc.rpc.exception.GlobalException;
import com.hrpc.rpc.spring.RpcRegistryBean;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by changqi on 2017/7/11.
 */

@NoArgsConstructor
public class RpcRegistryBeanParser implements BeanDefinitionParser{

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RpcRegistryBean.class);

        String ipAddress = element.getAttribute(ParserConstant.IpAddress);
        String port = element.getAttribute(ParserConstant.Port);
        String protocol = element.getAttribute(ParserConstant.Protocol);

        if(StringUtils.isEmpty(ipAddress) || StringUtils.isEmpty(port)){
            throw new GlobalException(ExceptionConstant.ZookeeperConfigException);
        }
        if(StringUtils.isEmpty(protocol)){
            protocol = ParserConstant.DefaultProtocol;
        }
        builder.setLazyInit(false);
        builder.addPropertyValue(ParserConstant.IpAddress, ipAddress);
        builder.addPropertyValue(ParserConstant.Port, port);
        builder.addPropertyValue(ParserConstant.Protocol, protocol);

        parserContext.getRegistry().registerBeanDefinition("registry", builder.getBeanDefinition());

        return builder.getBeanDefinition();
    }
}
