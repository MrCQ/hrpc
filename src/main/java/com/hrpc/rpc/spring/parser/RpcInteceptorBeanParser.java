package com.hrpc.rpc.spring.parser;

import com.hrpc.rpc.spring.RpcInteceptorBean;
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
public class RpcInteceptorBeanParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RpcInteceptorBean.class);

        String interfaceName = element.getAttribute(ParserConstant.InterfaceName);
        String ref = element.getAttribute(ParserConstant.Ref);
        builder.setLazyInit(false);
        builder.addPropertyValue(ParserConstant.InterfaceName, interfaceName);
        builder.addPropertyValue(ParserConstant.Ref, ref);

        return builder.getBeanDefinition();
    }
}
