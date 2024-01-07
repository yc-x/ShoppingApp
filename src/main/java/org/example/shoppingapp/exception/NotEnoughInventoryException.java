package org.example.shoppingapp.exception;

public class NotEnoughInventoryException extends Exception{
    public NotEnoughInventoryException(String s){
        super(s);
    }
}