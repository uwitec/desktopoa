package com.single.desktopoa.client.mvc.module.file;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.single.desktopoa.client.event.AppEvents;
import com.single.desktopoa.client.model.ClientFile;
import com.single.desktopoa.client.module.file.FileManager;
import com.single.desktopoa.client.mvc.AppView;
import com.single.desktopoa.module.file.model.File;

public class FileView extends View {

	private static FileManager fileManager;
	
	public FileView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if(event.getType()==AppEvents.FILE){
			Desktop desktop=Registry.get(AppView.DESKTOP);
			if(!desktop.getWindows().contains(getFileWindow())){
				desktop.addWindow(getFileWindow());
			}
			if(getFileWindow()!=null&&!getFileWindow().isVisible()){
				getFileWindow().show();
			}else{
				getFileWindow().toFront();
			}
		}else if(event.getType()==AppEvents.FILE_read){
			fileManager.read(event.<ClientFile>getData());
		}else if(event.getType()==AppEvents.FILE_edit){
			fileManager.edit(event.<ClientFile>getData());
		}
	}
	
	public static Window getFileWindow(){
		if(fileManager==null){
			fileManager=new FileManager();
		}
		return fileManager;
	}
}
