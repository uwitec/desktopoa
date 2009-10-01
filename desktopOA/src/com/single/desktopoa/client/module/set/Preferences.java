package com.single.desktopoa.client.module.set;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutModel;
import com.single.desktopoa.client.model.ShortcutModel.ShortcutWapper;
import com.single.desktopoa.client.mvc.AppView;

public class Preferences extends Window {

	private static Preferences preferences;
	
	private LayoutContainer main;
	private LayoutContainer shortcutSet;
	private LayoutContainer autoRunSet;
	
	private List<FieldSet> moduleList;
	
	private CardLayout layout=new CardLayout();
	private ToolBar toolBar;
	private Button back;
	
	public static Preferences get(){
		if(preferences==null){
			preferences=new Preferences();
		}
		return preferences;
	}
	
	private Preferences(){
		setLayout(layout);
		setSize(400, 400);
		
		toolBar=new ToolBar();
		back=new Button("返回",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				layout.setActiveItem(main);
			}
		});
		toolBar.add(back);
		setBottomComponent(toolBar);
		
		
		createMainPanel();
		createShortcutPanel();
		createAutoRunPanel();
		layout.setActiveItem(main);
	}
	
	private LayoutContainer createMainPanel(){
		main=new LayoutContainer();
		main.addText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		Button shortcut=new Button("桌面快捷设置",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				layout.setActiveItem(shortcutSet);
			}
		});
		Button autoRun=new Button("自启动设置",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				layout.setActiveItem(autoRunSet);
			}
		});
		main.add(shortcut);
		main.add(autoRun);
		add(main);
		return main;
	}
	private Listener<ComponentEvent> shortcutChangeListener=new Listener<ComponentEvent>(){
		public void handleEvent(ComponentEvent be) {
			CheckBox box=be.getComponent();
			ShortcutWapper shortcutWapper=box.getData("shortcutWapper");
			Shortcut shortcut=shortcutWapper.getShortcut();
			Desktop desktop=Registry.get(AppView.DESKTOP);
			if(box.getValue()){
				if(!desktop.getShortcuts().contains(shortcut)){
					desktop.addShortcut(shortcut);
				}else{
					shortcut.show();
				}
				
			}else{
				if(desktop.getShortcuts().contains(shortcut)){
					shortcut.hide();
				}
			}
			shortcutWapper.setShow(box.getValue());
			AppView.cookieProvider.set(shortcutWapper.getCookieId(), shortcutWapper.isShow());
		}
	};
	private LayoutContainer createShortcutPanel(){
		shortcutSet=new LayoutContainer();
		
		moduleList=new ArrayList<FieldSet>();
		for(ShortcutModel model:AppView.shortcutList){
			FieldSet set=new FieldSet();
			set.setHeading(model.getName());
			for(final ShortcutWapper short1:model.getShorts()){
				final CheckBox checkBox=new CheckBox();
				checkBox.setFieldLabel(short1.getShortcut().getText());
				checkBox.setValue(short1.isShow());
				checkBox.setData("shortcutWapper", short1);
				checkBox.setBoxLabel(short1.getShortcut().getText());
				checkBox.addListener(Events.Change,shortcutChangeListener);
				set.add(checkBox);
			}
			shortcutSet.add(set);
		}
		
		
		add(shortcutSet);
		return shortcutSet;
	}
	
	private Listener<ComponentEvent> autoRunListener=new Listener<ComponentEvent>(){
		public void handleEvent(ComponentEvent be) {
			CheckBox checkBox=be.getComponent();
			AutoRunModel model=checkBox.getData("autoRun");
			model.setAutoRun(checkBox.getValue());
			AppView.cookieProvider.set(model.getCookieId(), checkBox.getValue());
		}
	};
	private LayoutContainer createAutoRunPanel(){
		autoRunSet=new LayoutContainer();
		
		for(AutoRunModel model:AppView.autoRunList){
			CheckBox checkBox=new CheckBox();
			checkBox.setBoxLabel(model.getName());
			checkBox.setData("autoRun", model);
			checkBox.setValue(model.isAutoRun());
			checkBox.addListener(Events.Change, autoRunListener);
			autoRunSet.add(checkBox);
		}
		
		add(autoRunSet);
		return autoRunSet;
	}
}
