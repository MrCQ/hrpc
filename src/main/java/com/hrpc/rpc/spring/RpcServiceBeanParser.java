package com.hrpc.rpc.spring;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@NoArgsConstructor
public class RpcServiceBeanParser implements BeanDefinitionParser{
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return null;
    }
}
