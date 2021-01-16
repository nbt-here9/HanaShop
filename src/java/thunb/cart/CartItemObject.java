/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.cart;

import java.io.Serializable;

/**
 *
 * @author Banh Bao
 */
public class CartItemObject implements Serializable {

    private String itemName;
    private int quantity;
    private int price;

    public CartItemObject() {
        itemName = "";
        quantity = 1;
        price = 0;
    }

    public CartItemObject(String itemName,int price) {
        this.quantity = 1;
        this.itemName=itemName;
        this.price = price;
    }

    public CartItemObject(String itemName, int quantity, int price) {
        this.quantity = quantity;
        this.price = price;
        this.itemName=itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long total() {
        return this.price * this.quantity;
    }
}
