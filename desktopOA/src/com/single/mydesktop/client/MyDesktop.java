package com.single.mydesktop.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.state.CookieProvider;
import com.extjs.gxt.ui.client.widget.ComponentHelper;

public class MyDesktop extends Desktop {
	private CookieProvider cookieProvider=new CookieProvider(null,null,null,false);;
	private List<MyShortcut> myShortcuts=new ArrayList<MyShortcut>();

	public void addMyShortcut(MyShortcut shortcut){
		addMyShortcut(shortcut, true);
	}
	public void removeMyShortcut(MyShortcut shortcut){
		removeMyShortcut(shortcut, false);
	}
	/**
	 * 
	 * @param shortcut
	 * @param force 是否强制显示  true 强制显示  false 综合cookie与默认值显示
	 */
	public void addMyShortcut(MyShortcut shortcut,boolean force) {
		if(!myShortcuts.contains(shortcut)){
			if (shortcutEl != null) {
				myShortcuts.add(shortcut);
				shortcut.render(shortcutEl.dom);
				ComponentHelper.doAttach(shortcut);
			}
		}
		
		if(force){
			shortcut.show();
		}else{
			Boolean isShow=(Boolean) cookieProvider.get(shortcut.getCookie());
			if(isShow==null){
				if(!shortcut.getDefaultShow()){
					shortcut.hide();
				}
			}else{
				if(!isShow){
					shortcut.hide();
				}
			}
		}
		
	}

	/**
	 * 
	 * @param shortcut
	 * @param remove 是否真正删除
	 */
	public void removeMyShortcut(MyShortcut shortcut,boolean remove) {
		if(remove){
			if (shortcutEl != null) {
				myShortcuts.remove(shortcut);
				shortcutEl.dom.removeChild(shortcut.getElement());
				ComponentHelper.doDetach(shortcut);
			}
		}else{
			shortcut.hide();
		}
		
	}

	public List<MyShortcut> getMyShortcuts() {
		return myShortcuts;
	}

	@Override
	@Deprecated
	public void addShortcut(Shortcut shortcut) {
	}

	@Override
	@Deprecated
	public void removeShortcut(Shortcut shortcut) {
	}

	@Override
	@Deprecated
	public List<Shortcut> getShortcuts() {
		return null;
	}

	public CookieProvider getCookieProvider() {
		return cookieProvider;
	}

	public void setCookieProvider(CookieProvider cookieProvider) {
		this.cookieProvider = cookieProvider;
	}
}
