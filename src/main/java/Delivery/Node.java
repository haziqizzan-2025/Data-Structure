/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Delivery;

/**
 *
 * @author ammar
 */
class Node {
    String foodName;
    int price;  
    Node left, right;
    
    Node(String foodName, int price){
    this.foodName = foodName;
    this.price = price;
    this.left = null;
    this.right = null;
    }
}

