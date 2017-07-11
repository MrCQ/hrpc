package com.hrpc.rpc.spring.parser;

import com.hrpc.rpc.exception.ExceptionConstant;
import com.hrpc.rpc.exception.GlobalException;
import com.hrpc.rpc.spring.RpcRegistryBean;
import com.hrpc.rpc.spring.RpcServerBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by changqi on 2017/7/11.
 */
public class RpcServerBeanParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RpcServerBean.class);

        String port = element.getAttribute(ParserConstant.Port);
        String protocol = element.getAttribute(ParserConstant.Protocol);

        if(StringUtils.isEmpty(port)){
            throw new GlobalException(ExceptionConstant.ServerConfigException);
        }
        if(StringUtils.isEmpty(protocol)){
            protocol = ParserConstant.DefaultProtocol;
        }

        builder.addPropertyValue(ParserConstant.Port, port);
        builder.addPropertyValue(ParserConstant.Protocol, protocol);

        return builder.getBeanDefinition();
    }
}
