package com.single.desktopoa.module.code.serviceimpl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.tools.ant.taskdefs.condition.Os;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.single.desktopoa.module.BaseService;
import com.single.desktopoa.module.code.service.CodeService;

public class CodeServiceImpl extends BaseService implements CodeService {
	private File root;
	private FilenameFilter filter;
	private String path;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		path=config.getServletContext().getRealPath("/")+"/src";
		root = new File(path);
		filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return !name.startsWith(".");
			}
		};
	}

	@Override
	public List<ModelData> getChildren(ModelData data) {
		File[] files = null;
		if (data == null) {
			files = root.listFiles(filter);
		} else {
			File f = new File(path+data.<String> get("path"));
			files = f.listFiles(filter);
		}

		List<ModelData> models = new ArrayList<ModelData>();
		for (File f : files) {
			ModelData m = new BaseModelData();
			m.set("name", f.getName());
			m.set("path", f.getAbsolutePath().substring(path.length()-1));
			if (f.isDirectory()) {
				m.set("folder", true);
			} else {
				m.set("folder", false);
			}
			models.add(m);
		}

		Collections.sort(models, new Comparator<ModelData>() {
			public int compare(ModelData o1, ModelData o2) {
				return o1.<String> get("name").compareTo(
						o2.<String> get("name"));
			}
		});
		return models;
	}

	@Override
	public ModelData loadFile(String relativePath) {
		File file=new File(path+relativePath);
		String result=null;
		InputStream is=null;
		try {
			is=new BufferedInputStream(new FileInputStream(file));
			long length=file.length();
			ByteArrayOutputStream os=new ByteArrayOutputStream(length>0?(int)length:1024);
			byte[] buffer=new byte[4096];
			int len;
			while((len=is.read(buffer))>0){
				os.write(buffer,0,len);
			}
			os.close();
			result=os.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//保留字替换
		result=result.replaceAll("<", "&lt;")
					.replaceAll(">", "&gt;");
		//设置代码类型
		if(file.getName().endsWith(".java")){
			result="<pre name='"+file.getName()+"' class='java'>"+result+"</pre>";
		}else if(file.getName().endsWith(".xml")){
			result="<pre name='"+file.getName()+"' class='xml'>"+result+"</pre>";
		}
		
		ModelData data=new BaseModelData();
		data.set("name", file.getName());
		data.set("path", relativePath);
		data.set("content", result);
		
		return data;
	}

}
