/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import thunb.dtos.ProductsDTO;

/**
 *
 * @author Banh Bao
 */
public class CartObject implements Serializable {
    private Map<Integer,CartItemObject> items;

    public Map<Integer, CartItemObject> getItems() {
        return this.items;
    }

    public void addToCart(ProductsDTO product){
        if(this.items==null)this.items=new HashMap<>();
        
        if(!this.items.containsKey(product.getProductID())){
            CartItemObject item = new CartItemObject(product.getProductName(), product.getPrice());
            this.items.put(product.getProductID(),item);
        }else{
            CartItemObject item = items.get(product.getProductID());
            item.setQuantity(item.getQuantity()+1);
            this.items.put(product.getProductID(), item);
        }
    }
    
    public void updateCart(int productID,int quantity){
        if(this.items!=null&&!this.items.isEmpty()){
            if(this.items.containsKey(productID)){
                CartItemObject item = this.items.get(productID);
                item.setQuantity(quantity);
                this.items.put(productID, item);
                if(quantity==0)
                    removeFromCart(productID);
            }
        }
    }

    public void removeFromCart(int productID){
        if(this.items!=null&&!this.items.isEmpty()){
            if(this.items.containsKey(productID)){
                this.items.remove(productID);
            }
        }
        if(this.items.isEmpty())this.items=null;    
    }
    
    public long total(){
        long total = 0;
        if(this.items!=null&&!this.items.isEmpty()){
            for (Map.Entry<Integer, CartItemObject> entry : items.entrySet()) {
                CartItemObject value = entry.getValue();
                total+= value.total();
            }
        }
        return total;
    }
    
    public  int getNumOfProductInCart() {
        int num = 0;
        if(this.items!=null&&!this.items.isEmpty()){
            for (Map.Entry<Integer, CartItemObject> entry : items.entrySet()) {
                int numOfOne = entry.getValue().getQuantity();
                num+= numOfOne;
            }
        }
        return num;
    }
}