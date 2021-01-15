package org.study.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenyao
 * @date 2020/12/24 14:52
 * @description
 */
@Service
public class ModelMapper {
    @Autowired
    private PocAdminSubConfigDao pocAdminSubConfigDao;

    private Map<String, List<String>> classFieldMap = new ConcurrentHashMap<>();

    public <T> List<T> getModelResult(Class<T> t) {

        List<String> classField = getClassField(t);

        List<Map<String, Object>> modelResult = pocAdminSubConfigDao.getModelResult(classField);

        return paseModelResult(modelResult, t);
    }

    private <T> List<T> paseModelResult(List<Map<String, Object>> modelResult, Class<T> t) {
        if (CollectionUtils.isEmpty(modelResult)) return null;
        List<T> resList = new ArrayList<>();
        for (Map<String, Object> reMap : modelResult) {
            try {
                T beanInstance = t.newInstance();
                Field[] fields = beanInstance.getClass().getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (reMap.containsKey(f.getName())) {
                        setValue2Field(f, reMap.get(f.getName()), beanInstance);
                    }
                }
                resList.add(beanInstance);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }

    private void setValue2Field(Field f, Object value, Object bean) throws IllegalAccessException, ParseException {
        if (value == null) return;
        String val = value.toString();
        switch (f.getType().getSimpleName()) {
            case "String":
                f.set(bean, val);
                break;
            case "Integer":
                f.set(bean, Integer.valueOf(val));
                break;
            case "Long":
                f.set(bean, Long.valueOf(val));
                break;
            case "Byte":
                f.set(bean, Byte.valueOf(val));
                break;
            case "BigDecimal":
                f.set(bean, new BigDecimal(val));
                break;
            case "Date":
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                f.set(bean, sf.parse(val));
                break;
            default:
                break;
        }
    }


    private List<String> getClassField(Class<?> t) {
        String simpleName = t.getSimpleName();
        if (!classFieldMap.containsKey(simpleName)) {
            Field[] fields = t.getDeclaredFields();
            List<String> fieldList = new ArrayList<>();
            for (Field f : fields) {
                fieldList.add(f.getName());
            }
            classFieldMap.put(simpleName, fieldList);
            return fieldList;
        }
        return classFieldMap.get(simpleName);
    }
}
