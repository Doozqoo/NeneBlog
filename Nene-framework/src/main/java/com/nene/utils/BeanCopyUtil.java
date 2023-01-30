package com.nene.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ayachi Nene
 */
public class BeanCopyUtil {

    public BeanCopyUtil() {
    }

    public static <T> T beanCopy(Object source, Class<T> clazz) {

        if(source == null){
            return null;
        }

        T target;
        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return target;
    }

    public static <T> List<T> beanListCopy(List<?> list, Class<T> clazz) {

        if(list == null){
            return null;
        }

        return list.stream()
                .map(iter -> beanCopy(iter, clazz))
                .collect(Collectors.toList());
    }
}
