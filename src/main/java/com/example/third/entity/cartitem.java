package com.example.third.entity;




public class cartitem {
   private product product;
    private int quantity;
    public float amout;


    public cartitem(){

    }

    
    public cartitem(int quantity, float amout, product product){
        this.quantity=quantity;
        this.amout=amout;
        this.product=product;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public product getProduct(){
        return product;
    }
    public void setProduct(product product){
        this.product=product;

    }

    public float getAmout(){
        return amout;
    }
    public void setAmount(float amout){
        this.amout=amout;
    }

}
