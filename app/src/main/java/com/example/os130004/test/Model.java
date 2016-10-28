package com.example.os130004.test;

import android.content.Context;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;



public class Model implements Serializable {

    private transient Context context;
    private transient Comparator<Pair<String, String>> comparator = new Comparator<Pair<String, String>>() {
        @Override
        public int compare(Pair<String, String> o1, Pair<String, String> o2) {
            return o1.first.compareTo(o2.first);
        }
    };

    private boolean buttonState = true;
    private boolean whichType = false;
    private int currentArticlePosition;
    private int currentBuyerPosition;
    private ArrayList<Pair<String, String>> ordersByArticle = new ArrayList<>();
    private ArrayList<Pair<String, String>> ordersByBuyer = new ArrayList<>();

    public Model(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public String[] next() {
        if (whichType) {
            currentBuyerPosition = findNextBuyer();
            return getBuyerOrders();
        }
        currentArticlePosition = findNextArticle();
        return getArticleOrders();
    }

    private int findNextBuyer() {
        String buyer = ordersByBuyer.get(currentBuyerPosition).first;
        int i = currentBuyerPosition;
        for (; i < ordersByBuyer.size() && ordersByBuyer.get(i).first.equals(buyer); i++) ;
        if (ordersByBuyer.get(i).first.equals(buyer))
            return 0;
        buyer = ordersByBuyer.get(i).first;
        for (; i < ordersByBuyer.size() && ordersByBuyer.get(i).first.equals(buyer); i++) ;
        return i;

    }

    private int findNextArticle() {
        String order = ordersByArticle.get(currentBuyerPosition).first;
        int i = currentArticlePosition;
        for (; i < ordersByArticle.size() && ordersByArticle.get(i).first.equals(order); i++) ;
        if (ordersByBuyer.get(i).first.equals(order))
            return currentBuyerPosition;
        order = ordersByArticle.get(i).first;
        for (; i < ordersByArticle.size() && ordersByArticle.get(i).first.equals(order); i++) ;
        return i;
    }

    public String[] previous() {
        if (whichType) {
            currentBuyerPosition = findPreviousBuyer();
            return getBuyerOrders();
        }
        currentArticlePosition = findPreviousArticle();
        return getArticleOrders();
    }


    private int findPreviousArticle() {
        String order = ordersByArticle.get(currentBuyerPosition).first;
        int i = currentArticlePosition;
        for (; i > 0 && ordersByArticle.get(i).first.equals(order); i--) ;
        if (ordersByBuyer.get(i).first.equals(order))
            return 0;
        order = ordersByArticle.get(i).first;
        for (; i > 0 && ordersByArticle.get(i).first.equals(order); i--) ;
        return i;
    }

    private int findPreviousBuyer() {
        String buyer = ordersByBuyer.get(currentBuyerPosition).first;
        int i = currentBuyerPosition;
        for (; i > 0 && ordersByBuyer.get(i).first.equals(buyer); i--) ;
        if (ordersByBuyer.get(i).first.equals(buyer))
            return 0;
        buyer = ordersByBuyer.get(i).first;
        for (; i > 0 && ordersByBuyer.get(i).first.equals(buyer); i--) ;
        return i;
    }

    public String[] switchToName() {
        whichType = false;
        currentBuyerPosition = 0;
        Collections.sort(ordersByBuyer, comparator);
        return getBuyerOrders();
    }

    public String[] switchToArticle() {
        whichType = false;
        currentBuyerPosition = 0;
        Collections.sort(ordersByArticle, comparator);
        return getArticleOrders();
    }


    public String[] getBuyerOrders() {
        LinkedList<String> list = new LinkedList<>();
        if (ordersByBuyer.size() == 0)
            return new String[0];
        String buyer = ordersByBuyer.get(currentBuyerPosition).first;
        for (int i = currentBuyerPosition; i < ordersByBuyer.size() && buyer.equals(ordersByBuyer.get(i).first); i++)
            list.add(ordersByBuyer.get(i).second);
        return list.toArray(new String[]{});
    }

    public String[] getArticleOrders() {
        LinkedList<String> list = new LinkedList<>();
        if (ordersByArticle.size() == 0)
            return new String[0];
        String buyer = ordersByArticle.get(currentArticlePosition).first;
        for (int i = currentArticlePosition; i < ordersByArticle.size() && buyer.equals(ordersByArticle.get(i).first); i++)
            list.add(ordersByArticle.get(i).second);
        return list.toArray(new String[]{});
    }

    public String[] add(String name, String article, String quantity) {
        ordersByArticle.add(new Pair<>(article, name + " " + article + " " + quantity));
        ordersByBuyer.add(new Pair<>(name, name + " " + article + " " + quantity));
        if (!whichType){
            Collections.sort(ordersByBuyer, comparator);
            int i;
            for(i=0; i < ordersByBuyer.size() && !name.equals(ordersByBuyer.get(i).first); i++);
            currentBuyerPosition = i;
            return getBuyerOrders();
        } else {
            Collections.sort(ordersByArticle, comparator);
            int i;
            for(i=0; i < ordersByArticle.size() && !article.equals(ordersByArticle.get(i).first); i++);
            currentArticlePosition = i;
            return getArticleOrders();
        }
    }

    public void setButtonState(boolean buttonState) {
        this.buttonState = buttonState;
    }

    public boolean getButtonState() {return buttonState;}

    public boolean getSearchType(){
        return whichType;
    }

    public String[] current() {
        if (!whichType) {
            return getBuyerOrders();
        }
        return getArticleOrders();
    }
}
