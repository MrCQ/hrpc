package com.hrpc.rpc.spring.parser;

import com.hrpc.rpc.spring.RpcServiceBean;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@NoArgsConstructor
public class RpcServiceBeanParser implements BeanDefinitionParser{
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RpcServiceBean.class);

        String interfaceName = element.getAttribute(ParserConstant.InterfaceName);
        String ref = element.getAttribute(ParserConstant.Ref);

        builder.addPropertyValue(ParserConstant.InterfaceName, interfaceName);
        builder.addPropertyValue(ParserConstant.Ref, ref);

        return builder.getBeanDefinition();
    }
}
