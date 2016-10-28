package com.example.os130004.test;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.TreeMap;


class Model implements Serializable {

    private TreeMap<String, LinkedList<Order>> buyerOrderMap;
    private TreeMap<String, LinkedList<Order>> articleNameOrderMap;

    private boolean buttonState = true;
    private String currentBuyer = null;
    private String currentArticleName = null;
    private boolean showArticleOrders = false;

    Model() {
        buyerOrderMap = new TreeMap<>();
        articleNameOrderMap = new TreeMap<>();
    }

    // Add an order
    void add(String buyer, String article, int quantity) {
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
    }

    /**
     * Public interface used for getting orders.
     */


    Order[] current() {
        if (showArticleOrders)
            return getArticleOrders();
        return getBuyerOrders();
    }

    void next() {
        if (showArticleOrders) {
            currentArticleName = findNextArticle();
            return;
        }
        currentBuyer = findNextBuyer();

    }

    void previous() {
        if (showArticleOrders) {
            currentArticleName = findPreviousArticle();
            return;
        }
        currentBuyer = findPreviousBuyer();
    }

    boolean isNextDisabled() {
        if (showArticleOrders) {
            return currentArticleName == null || articleNameOrderMap.higherKey(currentArticleName) == null;
        }
        return currentBuyer == null || buyerOrderMap.higherKey(currentBuyer) == null;
    }

    boolean isPreviousDisabled() {
        if (showArticleOrders) {
            return currentArticleName == null || articleNameOrderMap.lowerKey(currentArticleName) == null;
        }
        return currentBuyer == null || buyerOrderMap.lowerKey(currentBuyer) == null;
    }
    /**
     * Switching list display
     */

    void switchToName() {
        showArticleOrders = false;
    }

    void switchToArticle() {
        showArticleOrders = true;
    }

    /**
     * Button states.
     */

    void setButtonState(boolean buttonState) {
        this.buttonState = buttonState;
    }

    boolean getButtonState() {
        return buttonState;
    }

    boolean getSearchType() {
        return showArticleOrders;
    }

    /**
     * Helper methods for data access.
     */


    private String findNextBuyer() {
        if (currentBuyer == null || buyerOrderMap.higherKey(currentBuyer) == null)
            return null;
        return buyerOrderMap.higherKey(currentBuyer);
    }

    private String findNextArticle() {
        if (currentArticleName == null || articleNameOrderMap.higherKey(currentArticleName) == null)
            return null;
        return articleNameOrderMap.higherKey(currentArticleName);
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
