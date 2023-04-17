package com.techelevator.dao;

import com.techelevator.model.GroceryList;

import java.util.List;

public interface GroceryListDao {

    List<GroceryList> getAllListItems(int userId);

    void addItemsToGroceryList(int userId, List<GroceryList> itemsFromFront);

    void clearGroceryList(int userId);
}
