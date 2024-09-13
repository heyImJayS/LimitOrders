package org.afob.limit;

import org.afob.execution.ExecutionClient;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class OrderService {

    LimitOrderAgent limitOrderAgent;
    private static BlockingQueue<Order> orders;

    public OrderService() {
        this.orders = new LinkedBlockingDeque<>();
        limitOrderAgent = new LimitOrderAgent(new ExecutionClient());
    }

    public void addOrder(boolean buyOrSell, String productId, int amount, Double limit){
        //Logic for checking the current price of product in Market
        Order newOrder = new Order(buyOrSell, productId, amount, limit);
        orders.offer(newOrder);
    }
    public void executeOrders() {
        while(!orders.isEmpty()) {
            Order order= orders.poll();
            BigDecimal priceInBD= new BigDecimal(order.getAmount());
            if(limitOrderAgent.priceTick(order.getProductId(), priceInBD) == 1 ) {
                System.out.println(limitOrderAgent.executeOrder(order));
            }else {
                //Put the current order to the end of the queue
                //Because in future it might be possible that the price may go down
                orders.offer(order);
            }
        }
    }


}
