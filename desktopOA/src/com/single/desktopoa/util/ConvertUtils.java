package com.single.desktopoa.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.HasAlignment;
import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.convert.Convert;
import com.single.desktopoa.convert.FileConvert;
import com.single.desktopoa.module.file.model.File;

public class ConvertUtils {

	private static String packagePath="com.single.desktopoa.convert";
	private static Map<Class, Convert<Object, Object>> map=new HashMap<Class, Convert<Object, Object>>();
	
	private static List<Convert> converts;
	
	private static FileConvert fileConvert=new FileConvert();
	
	@SuppressWarnings("unchecked")
	public static <X>X convertToClient(Object object) throws Exception{
		if(object instanceof List){
			List<Object> list=(List<Object>) object;
			List<Object> result=new ArrayList<Object>();
			for(Object obj:list){
				result.add(convertToClient(obj));
			}
			return (X)result;
		}else{
			String clazzName=packagePath+"."+object.getClass().getSimpleName()+"Convert";
			Class clazz=Class.forName(clazzName);
			if(map.get(clazz)==null){
				map.put(clazz, (Convert<Object, Object>)clazz.newInstance());
			}
			Convert<Object, Object> instance=map.get(clazz);
			return (X)instance.convertToClient(object);
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <X>X convertToModel(Object object) throws Exception{
		if(object instanceof List){
			List<Object> list=(List<Object>) object;
			List<Object> result=new ArrayList<Object>();
			for(Object obj:list){
				result.add(convertToModel(obj));
			}
			return (X)result;
		}else {
			String clazzName=packagePath+"."+object.getClass().getSimpleName().substring(6)+"Convert";
			Class clazz=Class.forName(clazzName);
			if(map.get(clazz)==null){
				map.put(clazz, (Convert<Object, Object>)clazz.newInstance());
			}
			Convert<Object, Object> instance=map.get(clazz);
			return (X)instance.convertToModel(object);
		}
		
	}
	
}
