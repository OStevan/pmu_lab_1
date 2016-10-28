package com.example.os130004.test;

import java.io.Serializable;



class Controller implements Serializable {

    interface Refreshable {
        void refresh();
    }

    private transient Refreshable refreshable;

    private Model model;

    Controller(Refreshable context) {
        this.refreshable = context;
        model = new Model();
    }

    void setRefreshable(Refreshable refreshable) {
        this.refreshable = refreshable;
    }

    void next() {
        model.next();
        refreshable.refresh();
    }

    void previous() {
        model.previous();
        refreshable.refresh();
    }

    void switchToBuyer() {
        model.switchToName();
        refreshable.refresh();
    }

    void switchToArticle() {
        model.switchToArticle();
        refreshable.refresh();
    }

    void addOrder(String name, String article, int quantity) {
        model.add(name, article, quantity);
        refreshable.refresh();
    }

    Order[] current() {
        return model.current();
    }

    boolean buttonAddState() {
        return model.getButtonState();
    }

    void enableButtonAdd() {
        model.setButtonState(true);
    }

    void disableButtonAdd() {
        model.setButtonState(false);
    }

    boolean searchType() {
        return model.getSearchType();
    }

    boolean previousButtonState() {
        return !model.isPreviousDisabled();
    }

    boolean nextButtonState() {
        return !model.isNextDisabled();
    }

}
