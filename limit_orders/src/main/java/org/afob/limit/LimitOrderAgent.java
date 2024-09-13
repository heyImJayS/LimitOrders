package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.afob.prices.PriceListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class LimitOrderAgent implements PriceListener {
    private ExecutionClient executorClient;
    public LimitOrderAgent(final ExecutionClient executorClient) {
        this.executorClient= executorClient;

    }

    @Override
    public int priceTick(String productId, BigDecimal price) {
        try {
            BigDecimal mktPrice = getCurrentMarketPrice(productId);
            if(mktPrice.compareTo(price) < 0){
                return 1;
            }
            return -1;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;

    }

    public String executeOrder(Order order) {
        System.out.println("Executing order: " + order);

        try{
            ReentrantLock rl = new ReentrantLock();
            rl.lock();
            if(order.getBuyOrSell()){
                executorClient.buy(order.getProductId(), order.getAmount()) ;
                rl.unlock();
                return "Below Order Successfully Bought.";
            }else{
                executorClient.sell(order.getProductId(), order.getAmount());
                rl.unlock();
                return "Below Order Successfully Sold.";
            }

        }catch(ExecutionClient.ExecutionException e){
            System.out.println(e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "Something went wrong with order.\n" +order.toString();

    }

    public BigDecimal getCurrentMarketPrice(String productId) throws Exception{
        //Here we have to call API/DB anything to get the Market price
        //Below is the fetched market price of IBM
        try {
            if (productId.equalsIgnoreCase("IBM")) {
                return new BigDecimal(98.02);
            }
            return new BigDecimal(1000.09);
        }catch(Exception e){
            e.printStackTrace();
        }
        throw new Exception("Something went wrong in fetching Market Data");
    }
}
