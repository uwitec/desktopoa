package com.single.desktopoa.client.module.notice;

import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.fx.FxConfig;
import com.extjs.gxt.ui.client.util.Point;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.InfoConfig;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.desktopoa.client.model.ClientNotice;

public class NoticeBoard extends Info {

	
	private ToolButton close;
	public static void display(ClientNotice notice){
		InfoConfig config=new InfoConfig(notice.getSubject(),notice.getContent());
		config.width=notice.getWidth();
		config.height=notice.getHeigth();
		display(config);
	}
	
	public static void display(InfoConfig config) {
		new NoticeBoard().show(config);
	}
	public static void display(String title, String text) {
		display(new InfoConfig(title, text));
	}
	public static void display(String title, String text,int width,int height) {
		InfoConfig config=new InfoConfig(title,text);
		config.width=width;
		config.height=height;
		display(config);
	}
	public NoticeBoard(){
		super();
		close=new ToolButton("x-tool-close",new SelectionListener<IconButtonEvent>(){
			public void componentSelected(IconButtonEvent ce) {
				NoticeBoard.this.hide();
			}
		});
		getHeader().addTool(close);
	}

	@Override
	protected void onShowInfo() {
		RootPanel.get().add(this);
		el().makePositionable(true);

		setHeading(config.title);
		addText(config.text);


		Point p = position();
		el().setLeftTop(p.x, p.y-config.height);
		setSize(config.width, config.height);

		el().setXY(p.x, p.y, FxConfig.NONE);
	}

	@Override
	protected Point position() {
		Size s = XDOM.getViewportSize();
		int left = s.width - config.width - 10 + XDOM.getBodyScrollLeft();
		int top =  10 + XDOM.getBodyScrollTop();
		return new Point(left, top);
	}
}
