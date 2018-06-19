package com.etiot.service;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

	private Logger log=LoggerFactory.getLogger(this.getClass());
	
	private Session session;
	private static CopyOnWriteArraySet<WebSocket> websocketSet=new CopyOnWriteArraySet<>();
	
	@OnOpen
	public void onOpen(Session session){
		this.session=session;
		websocketSet.add(this);
		log.info("【wecsocket消息】 有新的连接，总数：{}",websocketSet.size());
	}
	
	@OnClose
	public void onClose(){
		websocketSet.remove(this);
		log.info("【wecsocket消息】 连接断开，总数：{}",websocketSet.size());
	}
	
	@OnMessage
	public void onMessage(String message){
		log.info("【wecsocket消息】 收到客户端发来的消息：{}",message);
	}
	
	public void sendMessage(String message){
		for(WebSocket webSocket : websocketSet){
			log.info("【wecsocket消息】 广播消息，message={}",message);
			try {
				webSocket.session.getBasicRemote().sendText(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public static CopyOnWriteArraySet<WebSocket> getWebsocketSet() {
		return websocketSet;
	}

	public static void setWebsocketSet(CopyOnWriteArraySet<WebSocket> websocketSet) {
		WebSocket.websocketSet = websocketSet;
	}
	
}
