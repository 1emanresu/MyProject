package com.pay.yeepay.client.service;

import com.vo.Order;

public class YeepayThread implements Runnable {
    private String orderid = "";
    
    public YeepayThread(String orderid){
        this.orderid = orderid;
    }

    @Override
    public void run() {
        Order order = null;
        int r1_Code = 0;
        boolean boo = true;
        while(boo){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            order = Order.dao.findById(orderid);
            r1_Code = order.getInt("r1_Code");
            if(r1_Code==1){
                YeepayService.service.asynchronous(orderid);
                boo = false;
            }else if(r1_Code==3){
                boo = false;
            }else if(r1_Code==4){
                boo = false;
            }
        }
    }
}
