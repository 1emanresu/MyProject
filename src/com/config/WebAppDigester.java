package com.config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;

//
//import com.lske.entity.ServletMapping;
//import com.lske.entity.WebApp;


/**
 * web.xml解析类
 * @author blackworm
 *
 */
public class WebAppDigester {


/**
* web.xml解析方法
* @param src
* @return 
*/
public static WebApp digester(String src){
WebApp webApp=null;
Digester digester = new Digester();
    digester.setValidating(false);
    digester.addObjectCreate("web-app", WebApp.class);
    digester.addObjectCreate("web-app/servlet-mapping", ServletMapping.class);
    digester.addBeanPropertySetter("web-app/servlet-mapping/servlet-name","name");
    digester.addBeanPropertySetter("web-app/servlet-mapping/url-pattern","url");
    //当移动到下一个标签中时的动作
    digester.addSetNext("web-app/servlet-mapping", "addServletMapping");
    try {
webApp=digester.parse(new File(src));
} catch (IOException e) {
e.printStackTrace();
} catch (SAXException e) {
e.printStackTrace();
}
return webApp;
}
}
