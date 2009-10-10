package com.single.desktopoa.client.module.set;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.ListViewEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.desktopoa.client.model.AutoRunModel;
import com.single.desktopoa.client.model.ShortcutModel;
import com.single.desktopoa.client.model.ShortcutModel.ShortcutWapper;
import com.single.desktopoa.client.mvc.AppView;

public class Preferences extends Window {

	private static Preferences preferences;
	
	private LayoutContainer main;
	private LayoutContainer shortcutSet;
	private LayoutContainer autoRunSet;
	private LayoutContainer backgroundImageSet;
	
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
		createBackgroundImagePanel();
		layout.setActiveItem(main);
	}
	
	private void createBackgroundImagePanel() {
		backgroundImageSet=new LayoutContainer();
		
		add(backgroundImageSet);
		
		backgroundImageSet.setId("images-view");
		ListView<ModelData> view = new ListView<ModelData>();
		view.setItemSelector("div.thumb-wrap");
		view.setTemplate(getTemplate());
		ListStore<ModelData> store = new ListStore<ModelData>();

		ModelData blank=new BaseModelData();
		blank.set("name", "空白");
		blank.set("path", "");
		blank.set("shortName", "空白");
		store.add(blank);
		
		ModelData blue=new BaseModelData();
		blue.set("name", "蓝色");
		blue.set("path", "gxt/desktop/wallpapers/desktop.jpg");
		blue.set("shortName", "蓝色");
		store.add(blue);
		
		ModelData water=new BaseModelData();
		water.set("name", "水滴");
		water.set("path", "gxt/desktop/wallpapers/fresh-morning.jpg");
		water.set("shortName", "水滴");
		store.add(water);
		
		ModelData flower=new BaseModelData();
		flower.set("name", "花朵");
		flower.set("path", "gxt/desktop/wallpapers/summer.jpg");
		flower.set("shortName", "花朵");
		store.add(flower);
		
		ModelData cloud=new BaseModelData();
		cloud.set("name", "野景");
		cloud.set("path", "gxt/desktop/wallpapers/colorado-farm.jpg");
		cloud.set("shortName", "野景");
		store.add(cloud);
		
		ModelData green=new BaseModelData();
		green.set("name", "绿色");
		green.set("path", "gxt/desktop/wallpapers/curls-on-green.jpg");
		green.set("shortName", "绿色");
		store.add(green);
		
		
		view.setStore(store);
		
		view.addListener(Events.OnClick, new Listener<ListViewEvent<ModelData>>(){
			public void handleEvent(ListViewEvent<ModelData> be) {
				ModelData data=be.getModel();
				RootPanel.getBodyElement().setAttribute("background", GWT.getHostPageBaseURL()+data.get("path"));
			}
		});
		
		backgroundImageSet.add(view);
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
		Button bgImage=new Button("背景图片",new SelectionListener<ButtonEvent>(){
			public void componentSelected(ButtonEvent ce) {
				layout.setActiveItem(backgroundImageSet);
			}
		});
		main.add(shortcut);
		main.add(autoRun);
		main.add(bgImage);
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
		shortcutSet.setScrollMode(Scroll.AUTO);
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
	private native String getTemplate() /*-{
		return ['<tpl for=".">',
		'<div class="thumb-wrap" id="{name}" style="border: 1px solid white">',
		'<div class="thumb"><img src="{path}" title="{name}" width=128 height=128></div>',
		'<span class="x-editable">{shortName}</span></div>',
		'</tpl>',
		'<div class="x-clear"></div>'].join("");
	}-*/;
}
