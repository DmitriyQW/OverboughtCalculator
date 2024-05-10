package com.tradecalc.lernjava;

public class Stock {
    String mainTitle,secondTitle,cost;
    String imageSrcUrl;

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImageSrcUrl() {
        return imageSrcUrl;
    }

    public void setImageSrcUrl(String imageSrcUrl) {
        this.imageSrcUrl = imageSrcUrl;
    }

    public Stock(String mainTitle, String secondTitle, String cost, String imageSrcUrl) {
        this.mainTitle = mainTitle;
        this.secondTitle = secondTitle;
        this.cost = cost;
        this.imageSrcUrl = imageSrcUrl;
    }
}
