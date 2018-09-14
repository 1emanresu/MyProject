package com.tool;

/* 
 * Created on 2005-6-5 
 * Author stephen 
 * Email zhoujianqiang AT gmail DOT com 
 * CopyRight(C)2005-2008 , All rights reserved. 
 */  
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

import com.pay.yeepay.server.Configuration;
  
/** 
 * 与系统相关的一些常用工具方法. 
 *  
 * @author stephen 
 * @version 1.0.0 
 */  
public class SystemTool {   
  
    /** 
     * 获取当前操作系统名称. 
     * return 操作系统名称 例如:windows xp,linux 等. 
     */  
    public static String getOSName() {   
        return System.getProperty("os.name").toLowerCase();   
    }   
       
    /** 
     * 获取unix网卡的mac地址. 
     * 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法. 
     * @return mac地址 
     */  
    public static String getUnixMACAddress() {   
        String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ifconfig eth0");// linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息  
            bufferedReader = new BufferedReader(new InputStreamReader(process   
                    .getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {   
                index = line.toLowerCase().indexOf("hwaddr");// 寻找标示字符串[hwaddr]  
                if (index >= 0) {// 找到了  
                    mac = line.substring(index +"hwaddr".length()+ 1).trim();//  取出mac地址并去除2边空格  
                    break;   
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
  
        return mac;   
    }   
  
    /** 
     * 获取widnows网卡的mac地址. 
     * @return mac地址 
     */  
    public static String getWindowsMACAddress() {   
    	InetAddress ia;
    	StringBuffer sb = null;
		try {
			ia = InetAddress.getLocalHost();
		//获取本地IP对象
    	//获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        
        //下面代码是把mac地址拼装成String
        sb = new StringBuffer();
        
        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("-");
            }
            //mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }   
    
    public static boolean ifMAC(){
    	String os = getOSName();   
        System.out.println(os);
        String mac = "";
        String ma = Configuration.getInstance().getValue("mac");
        boolean boo = false;
        if(os.startsWith("windows")){   
            //本地是windows  
            mac = getWindowsMACAddress();   
            if(mac==null){
            	mac="";
            }
            System.out.println(mac);   
        }else{   
            //本地是非windows系统 一般就是unix  
            mac = getUnixMACAddress();   
            if(mac==null){
            	mac="";
            }
            System.out.println(mac);   
        }
        if(mac.equals(ma)){
        	boo = true;
        }
    	return boo;
    } 
  
    /** 
     * 测试用的main方法. 
     *  
     * @param argc 
     *            运行参数. 
     */  
    public static void main(String[] argc) {   
        String os = getOSName();   
        System.out.println(os);   
        if(os.startsWith("windows")){   
            //本地是windows  
            String mac = getWindowsMACAddress();   
            System.out.println(mac);   
        }else{   
            //本地是非windows系统 一般就是unix  
            String mac = getUnixMACAddress();   
            System.out.println(mac);   
        }   
    }   
}  