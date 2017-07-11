package com.hrpc.rpc.spring.parser;

import com.hrpc.rpc.spring.RpcReferenceBean;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by changqi on 2017/7/11.
 */

@NoArgsConstructor
public class RpcReferenceBeanParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RpcReferenceBean.class);

        String interfaceName = element.getAttribute(ParserConstant.InterfaceName);

        builder.addPropertyValue(ParserConstant.InterfaceName, interfaceName);

        return builder.getBeanDefinition();
    }
}
