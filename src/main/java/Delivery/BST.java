/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Delivery;

/**
 *
 * @author ammar
 */
class BST {
    Node root;
    
    void insert(String foodName, int price){
        root = insertRec(root, foodName, price);
    }
    
    Node insertRec(Node node, String foodName, int price){
        if(node == null){
            return new Node(foodName, price);
        }else if(price < node.price){
            node.left = insertRec(node.left, foodName, price);
        }else{
            node.right  =insertRec(node.right, foodName, price);
        }
        return node;
    }
    
    boolean search(int price){ 
        return searchRec(root, price);
    }
    
    boolean searchRec(Node node, int price){
        if(node == null){
            return false;
        }else if(price == node.price){
            return true;
        }else if(price < node.price){
             return searchRec(node.left, price);
        }else{
            return searchRec(node.right, price);
        }
    }
    
    void searchByName(Node node, String foodQuery) {
        if (node == null) {
            return;
        }
        // Checks if the food item name contains your search keyword (case-insensitive)
        if (node.foodName.toLowerCase().contains(foodQuery.toLowerCase())) {
            System.out.println("-> Found: " + node.foodName + " | Price: RM " + node.price);
        }
        searchByName(node.left, foodQuery);
        searchByName(node.right, foodQuery);
    }
    
    void inorder(){
         inorderRec(root);
         
    }
    
    void inorderRec(Node node){
        if(node == null){
            return;
        }
        inorderRec(node.left);
        System.out.println(node.foodName + " RM " + node.price);
        inorderRec(node.right);
    }
}
