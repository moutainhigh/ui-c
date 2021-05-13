package com.sep.distribution.service.impl;

import com.google.common.collect.Maps;
import com.sep.distribution.service.EnumService;
import com.sep.distribution.vo.xcx.EnumItemVo;
import com.sep.distribution.vo.xcx.EnumVo;
import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.EnumName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnumServiceImpl implements EnumService {

    @Value("${distribution.enum.bash.path}")
    private String bashPath;

    private final Map<String, EnumVo> enums = Maps.newHashMap();

    /**
     * 加载枚举信息
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    @PostConstruct
    public void init() throws ClassNotFoundException, NoSuchMethodException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(CommonEnum.class));
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(bashPath)) {
            Class clazz = Class.forName(beanDefinition.getBeanClassName());
            EnumName enumName = (EnumName) clazz.getAnnotation(EnumName.class);
            Object[] objects = clazz.getEnumConstants();
            Method getCode = clazz.getMethod("getCode");
            Method getMessage = clazz.getMethod("getDescription");
            List<EnumItemVo> enumItemVos = Arrays.stream(objects).map(o -> {
                EnumItemVo vo = new EnumItemVo();
                try {
                    vo.setId((Integer) getCode.invoke(o));
                    vo.setText((String) getMessage.invoke(o));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return vo;
            }).collect(Collectors.toList());
            EnumVo enumVo = new EnumVo();
            enumVo.setId(enumName.id());
            enumVo.setText(enumName.name());
            enumVo.setItems(enumItemVos);
            enums.put(enumName.id(), enumVo);
        }
    }

    @Override
    public EnumVo find(String type) {
        return enums.get(type);
    }

    @Override
    public Collection<EnumVo> find() {
        return enums.values();
    }

}