package com.kwok.util.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 相同类的实例基本类型的属性复制。
 * 亦可以使用 Apache Commons BeanUtils。
 * @author Kwok
 */
public class SimpleBeanUtil {

	/**
	 * 复制Map键值到目标对象同名的属性值，区分大小写
	 * @author Kwok
	 */
	public static void copyPropertiesFromMap(Map<String, Object> map, Object dest) throws Exception{
		List<Field> fieldList = getOwnAndSuperNotStaticField(dest.getClass());
		for (Field field : fieldList) {
			if(map.containsKey(field.getName())){
				field.setAccessible(true);
				field.set(dest, map.get(field.getName()));
			}
		}
	}
	
	
	/**
	 * 复制源对象给定字段列表的属性值到目标对象
	 * @author Kwok
	 */
	public static void copyPropertiesByField(Object orig, Object dest, List<Field> destfieldList) throws Exception{
		if(dest.getClass() != orig.getClass()){
			throw new Exception("要求源对象与目标对象是同一个类的实例！");
		}
		for (Field field : destfieldList) {
			field.setAccessible(true);
			field.set(dest, field.get(orig));
		}
	}
	
	
	/**
	 * 复制源对象给定字段名列表的属性值到目标对象
	 * @author Kwok
	 */
	public static void copyPropertiesByFieldName(Object orig, Object dest, List<String> destfieldNameList) throws Exception{
		copyPropertiesByField(orig, dest, getFieldByFieldName(orig.getClass(), destfieldNameList));
	}
	
	
	/**
	 * 复制源对象的所有属性值到目标对象
	 * @author Kwok
	 */
	public static void copyProperties(Object orig, Object dest) throws Exception{
		copyPropertiesByField(orig, dest, getOwnAndSuperNotStaticField(orig.getClass()));
	}
	
	
	/**
	 * 对象的字段列表 转 该对象的字段名列表
	 * @author Kwok
	 */
	public static List<String> getFieldNameByField(List<Field> fieldList){
		return fieldList.stream().map(Field::getName).collect(Collectors.toList());
	}
	
	/**
	 * 对象的字段名列表 转 该对象的字段列表
	 * @author Kwok
	 */
	public static List<Field> getFieldByFieldName(Class<?> destCls, List<String> fieldNameList){
		return getOwnAndSuperNotStaticField(destCls).stream().filter(field -> fieldNameList.contains(field.getName())).collect(Collectors.toList());
	}
	
	
	/**
	 * 获取该类自己的非静态字段
	 * @author Kwok
	 */
	public static List<Field> getOwnNotStaticField(Class<?> cls){
		List<Field> fieldList = new ArrayList<Field>();
			for (Field field : cls.getDeclaredFields()) {
				if(!Modifier.isStatic(field.getModifiers())){
					fieldList.add(field);
				}
		}
		return fieldList;
	}
	
	
	/**
	 * 获取该类自己及父类的非静态字段
	 * @author Kwok
	 */
	public static List<Field> getOwnAndSuperNotStaticField(Class<?> cls){
		List<Field> fieldList = new ArrayList<Field>();
		for (Class<?> tempcls = cls; tempcls != Object.class; tempcls = tempcls.getSuperclass()) {
			for (Field field : tempcls.getDeclaredFields()) {
				if(!Modifier.isStatic(field.getModifiers())){
					fieldList.add(field);
				}
			}
		}
		return fieldList;
	}
	
	
	/**
	 * 把 Bean 实体对象非静态字段转化为 HashMap
	 * @author Kwok
	 */
	public static Map<String, Object> bean2Map(Object bean, boolean ignoreNullVal, List<String> ignoreAttrList) throws Exception{
		Map<String, Object> reslutMap = new HashMap<String, Object>();
		
		if(ignoreAttrList == null){
			ignoreAttrList = Collections.emptyList();
		}
		
		Class<?> beanClass = bean.getClass(); 
		List<Field> ownAndSuperNotStaticField = getOwnAndSuperNotStaticField(beanClass);
		for (Field field : ownAndSuperNotStaticField) {
			field.setAccessible(true);
			Object value = field.get(bean);
			if ((value != null || !ignoreNullVal) && !ignoreAttrList.contains(field.getName())) {
				reslutMap.put(field.getName(), value);
			}
		}
		
		return reslutMap;
	}
	
}
