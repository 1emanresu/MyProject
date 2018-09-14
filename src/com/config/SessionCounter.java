package com.config;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.vo.Logrecord;
import com.vo.Person;

public class SessionCounter implements HttpSessionListener {

	/* Session创建事件 */
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	/* Session失效事件 */
	public void sessionDestroyed(HttpSessionEvent se) {
		Logrecord log =  (Logrecord)se.getSession().getAttribute("logrecord");
		Person per = (Person)se.getSession().getAttribute("session");
		if(log!=null&&per!=null){
			log.set("exittime", new Date());
			log.update();
			per.set("iflogin", 2);
			per.update();
			se.getSession().removeAttribute("logrecord");
			se.getSession().removeAttribute("session");
		}
	}
}