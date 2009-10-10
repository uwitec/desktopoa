package com.single.desktopoa.client.module.code;


import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.single.desktopoa.client.factory.ServiceFactory;

public class CodeItem extends TabItem {

	private AsyncCallback<ModelData> callback=new AsyncCallback<ModelData>(){
		public void onFailure(Throwable caught) {
		}
		public void onSuccess(ModelData result) {
			addText(result.<String>get("content"));
			layout();
			highlight(result.<String>get("name"));
		}
	};
	public CodeItem(String path){
		String name=path.substring(path.lastIndexOf("\\")+1);
		setText(name);
		setId(path);
		setScrollMode(Scroll.AUTO);
		setLayout(new FitLayout());
		setClosable(true);
		ServiceFactory.codeService.loadFile(path, callback);
	}
	
	private static native void highlight(String name)/*-{
		$wnd.dp.SyntaxHighlighter.HighlightAll(name);
	}-*/;
	
}
