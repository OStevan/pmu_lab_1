package com.example.os130004.test;

import android.content.Context;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NavigableMap;
import java.util.TreeMap;


public class Model implements Serializable {

    private TreeMap<String, LinkedList<Order>> buyerOrderMap;
    private TreeMap<String, LinkedList<Order>> articleNameOrderMap;

    private boolean buttonState = true;
    private transient Context context;
    private String currentBuyer = null;
    private String currentArticleName = null;
    private boolean showArticleOrders = false;

    public Model(Context context) {
        this.context = context;
        buyerOrderMap = new TreeMap<>();
        articleNameOrderMap = new TreeMap<>();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Order[] add(String buyer, String article, int quantity) {
        Order newOrder = new Order(buyer, article, quantity);

        // Inserting a Order in a buyer's order list
        if (buyerOrderMap.containsKey(buyer)) {
            LinkedList<Order> buyerList = buyerOrderMap.get(buyer);
            buyerList.add(newOrder);
        } else {
            LinkedList<Order> buyerList = new LinkedList<>();
            buyerList.add(newOrder);
            buyerOrderMap.put(buyer, buyerList);
        }

        //inserting an Order to article's order list
        if (articleNameOrderMap.containsKey(article)) {
            LinkedList<Order> orderLinkedList = articleNameOrderMap.get(article);
            orderLinkedList.add(newOrder);
        } else {
            LinkedList<Order> orderLinkedList = new LinkedList<>();
            orderLinkedList.add(newOrder);
            articleNameOrderMap.put(article, orderLinkedList);
        }
        currentBuyer = buyer;
        currentArticleName = article;
        return current();
    }

    /**
     * Public interface used for getting orders.
     */


    public Order[] current() {
        if (showArticleOrders)
            return getArticleOrders();
        return getBuyerOrders();
    }

    public Order[] next() {
        if (showArticleOrders) {
            currentArticleName = findNextArticle();
        } else {
            currentBuyer = findNextBuyer();
        }
        return current();
    }

    public Order[] previous() {
        if (showArticleOrders) {
            currentArticleName = findPreviousArticle();
        } else {
            currentBuyer = findPreviousBuyer();
        }
        return current();
    }

    public boolean isNextDisabled() {
        if (showArticleOrders) {
            return currentArticleName == null || articleNameOrderMap.higherKey(currentArticleName) == null;
        } else {
            return currentBuyer == null || buyerOrderMap.higherKey(currentBuyer) == null;
        }
    }

    public boolean isPreviousDisabled() {
        if (showArticleOrders) {
            return currentArticleName == null || articleNameOrderMap.lowerKey(currentArticleName) == null;
        } else {
            return currentBuyer == null || buyerOrderMap.lowerKey(currentBuyer) == null;
        }
    }
    /**
     * Switching list display
     */

    public Order[] switchToName() {
        showArticleOrders = false;
        return current();
    }

    public Order[] switchToArticle() {
        showArticleOrders = true;
        return current();
    }

    /**
     * Button states.
     */

    public void setButtonState(boolean buttonState) {
        this.buttonState = buttonState;
    }

    public boolean getButtonState() {
        return buttonState;
    }

    public boolean getSearchType() {
        return showArticleOrders;
    }

    /**
     * Helper methods for data access.
     */


    private String findNextBuyer() {
        if (currentBuyer == null || buyerOrderMap.higherKey(currentBuyer) == null)
            return null;
        return buyerOrderMap.lowerKey(currentBuyer);
    }

    private String findNextArticle() {
        if (currentArticleName == null || articleNameOrderMap.higherKey(currentArticleName) == null)
            return null;
        return articleNameOrderMap.lowerKey(currentArticleName);
    }

    private String findPreviousArticle() {
        if (currentArticleName == null || articleNameOrderMap.lowerKey(currentArticleName) == null)
            return null;
        return articleNameOrderMap.lowerKey(currentArticleName);
    }

    private String findPreviousBuyer() {
        if (currentBuyer == null || buyerOrderMap.lowerKey(currentBuyer) == null)
            return null;
        return buyerOrderMap.lowerKey(currentBuyer);
    }


    private Order[] getBuyerOrders() {
        if (currentBuyer == null)
            return new Order[0];
        LinkedList<Order> orders = buyerOrderMap.get(currentBuyer);
        return orders.toArray(new Order[orders.size()]);
    }

    private Order[] getArticleOrders() {
        if (currentArticleName == null)
            return new Order[0];
        LinkedList<Order> orders = articleNameOrderMap.get(currentArticleName);
        return orders.toArray(new Order[orders.size()]);
    }
}
