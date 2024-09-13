package org.afob.limit;

import org.afob.execution.ExecutionClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DriverClass {
    public static void main(String[] args){
        OrderService orderService= new OrderService();
        //Order1
        String IBM_PRODUCT_ID = "IBM";
        double IBM_LIMIT_PRICE = 100.0;
        int IBM_ORDER_AMOUNT = 1000;
        boolean IBM_BUY_OR_SELL=true;

        orderService.addOrder(IBM_BUY_OR_SELL, IBM_PRODUCT_ID, IBM_ORDER_AMOUNT, IBM_LIMIT_PRICE);

        //Order2
        String MSFT_PRODUCT_ID = "IBM";
        double MSFT_LIMIT_PRICE = 100.0;
        int MSFT_ORDER_AMOUNT = 1000;
        boolean MSFT_BUY_OR_SELL=true;
        orderService.addOrder(MSFT_BUY_OR_SELL, MSFT_PRODUCT_ID, MSFT_ORDER_AMOUNT, MSFT_LIMIT_PRICE);

        orderService.executeOrders();
    }
}
