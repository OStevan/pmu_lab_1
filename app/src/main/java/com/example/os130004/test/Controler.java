package com.example.os130004.test;

import android.content.Context;

import java.io.Serializable;



public class Controler implements Serializable {

    private transient Context context;

    private Model model;

    public Controler(Context context) {
        this.context = context;
        model = new Model(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String[] next() {
        return model.next();
    }

    public String[] previous() {
        return model.previous();
    }

    public String[] switchToName() {
        return model.switchToName();
    }

    public String[] switchToArticle() {
        return model.switchToArticle();
    }

    public void addOrder(String name, String article, String quantity) {
        model.add(name, article, quantity);
    }

    public String[] current() {
        return model.current();
    }

    public boolean getButtonEnabled() {
        return model.getButtonState();
    }

    public void setButtonState(boolean state) {
        model.setButtonState(state);
    }

    public boolean getSearchType() {
        return model.getSearchType();
    }

    public interface RefreshState {
        void changedList(String[] stringArray);

        void disablePrevious();

        void disableNext();

    }
}
