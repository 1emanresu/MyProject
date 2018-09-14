package com.config;

import java.util.ArrayList;
import java.util.List;

/**
 * web-app对象
 * @author blackworm
 *
 */
public class WebApp {


private List<ServletMapping> servletMappings=new ArrayList<ServletMapping>();

public List<ServletMapping> getServletMappings() {
return servletMappings;
}
public void setServletMappings(List<ServletMapping> servletMappings){
this.servletMappings = servletMappings;
}
// 供Digester调用的方法
    public void addServletMapping(ServletMapping servletMapping) {
        this.servletMappings.add(servletMapping);
    }
public WebApp(List<ServletMapping> servletMappings) {
super();
this.servletMappings = servletMappings;
}
public WebApp() {
super();
}
@Override
public String toString() {
return "WebApp [servletMappings=" + servletMappings + "]";
}

}
