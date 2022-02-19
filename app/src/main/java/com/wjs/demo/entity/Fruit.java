package com.wjs.demo.entity;

import java.io.Serializable;

public class Fruit implements Serializable {

    private int fruitImageId;
    private String fruitName1;
    private String fruitName2;
    private String fruitBtn;

    public Fruit(int fruitImageId, String fruitName1, String fruitName2, String fruitBtn) {
        this.fruitImageId = fruitImageId;
        this.fruitName1 = fruitName1;
        this.fruitName2 = fruitName2;
        this.fruitBtn = fruitBtn;
    }

    public int getFruitImageId() {
        return fruitImageId;
    }

    public String getFruitName1() {
        return fruitName1;
    }

    public String getFruitName2() {
        return fruitName2;
    }

    public String getFruitBtn() {
        return fruitBtn;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "fruitImageId=" + fruitImageId +
                ", fruitName1='" + fruitName1 + '\'' +
                ", fruitName2='" + fruitName2 + '\'' +
                ", fruitBtn='" + fruitBtn + '\'' +
                '}';
    }
}
