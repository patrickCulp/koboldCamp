/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.dao;

import com.rockon.koboldcamp.model.Category;
import java.util.List;

/**
 *
 * @author pculp
 */
public interface DaoCategory {
    
    //Create
    public Category addCategory(Category category);
    
    //Read
    public Category getCategoryById(Integer categoryID);
    public Integer getCategoryIdByName(String catName);
    public List<Category> getAllCategories();
    
    //Update
    public Category updateCategory(Category category);
    
    //Delete
    public void deleteCategory(Integer categoryID);
    
}
