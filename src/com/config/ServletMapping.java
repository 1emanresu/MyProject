package com.config;

/**
 * servlet-mapping对象
 * @author blackworm
 *
 */
public class ServletMapping {


private String name;
private String url;
public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public String getUrl() {
return url;
}
public void setUrl(String url) {
this.url = url;
}
public ServletMapping(String name, String url) {
super();
this.name = name;
this.url = url;
}
public ServletMapping() {
super();
}
@Override
public String toString() {
return "ServletMapping [name=" + name + ", url=" + url + "]";
}

}
