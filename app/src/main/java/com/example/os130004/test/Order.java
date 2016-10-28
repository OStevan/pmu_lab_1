package com.example.os130004.test;

import java.io.Serializable;

/**
 * Created by Stevan Ognjanovic on 10/28/2016.
 */

public class Order implements Serializable {

    private String buyer;
    private String articleName;
    private int quantity;

    public Order(String buyer, String articleName, int quantity) {
        this.buyer = buyer;
        this.articleName = articleName;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return buyer + " " + articleName + " " + quantity;
    }
}
