/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.dao;

import com.rockon.koboldcamp.model.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author pculp
 */
public class DaoCategoryImpl implements DaoCategory {
    
    private JdbcTemplate jdbcTemplate;
        
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }    
    
    //Create
    private static final String SQL_INSERT_CATAGORY
            = "INSERT INTO category (name) VALUES (?)";
    
    public Category addCategory(Category category) {
        jdbcTemplate.update(SQL_INSERT_CATAGORY,
                category.getCategoryName());
        Integer generatedId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()",
                Integer.class);
        category.setCategoryID(generatedId);
        return category;
    }
    
    //Read 
    
    private static final String SQL_GET_CATEGORY_BY_ID = 
            "SELECT * FROM categories WHERE category_id = ?";
    
    @Override
    public Category getCategoryById(Integer categoryID){
        Category cat = jdbcTemplate.queryForObject(SQL_GET_CATEGORY_BY_ID, 
                new CategoryMapper(), categoryID);
        return cat;
    }
    //SELECT * FROM categories WHERE name = ?
    // ex.'Sleeping Bags'
    
    private static final String SQL_GET_CATEGORYID_BY_NAME = 
            "SELECT * FROM categories WHERE name = ?";
    
    public Integer getCategoryIdByName(String catName){
        Category cat = jdbcTemplate.queryForObject(SQL_GET_CATEGORYID_BY_NAME, 
                new CategoryMapper(), catName);
        Integer catID = cat.getCategoryID();
        return catID;
    }

    
    private static final String SQL_GET_ALL_CATEGORIES = 
            "SELECT * FROM categories";
    
    @Override
    public List<Category> getAllCategories(){
        List<Category> listCat = jdbcTemplate.query(
                SQL_GET_ALL_CATEGORIES, new CategoryMapper());
        
        return listCat;
    }
    
    //Update 
    private static final String SQL_UPDATE_CATEGORY 
        = "UPDATE categories SET category_name = ? WHERE category_id = ?";
    
    public Category updateCategory(Category category) {
        jdbcTemplate.update(SQL_UPDATE_CATEGORY,
                category.getCategoryName()
        );
        return category;
    }
  
    //Delete
    
    private static final String SQL_DELETE_CATEGORY
            = "DELETE FROM categories WHERE category_id = ?";
    
    public void deleteCategory(Integer categoryID) {
        jdbcTemplate.update(SQL_DELETE_CATEGORY,categoryID);
    }
    
    private static final class CategoryMapper implements RowMapper<Category> {
        
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category cat = new Category();
            
            cat.setCategoryID(rs.getInt("category_id"));
            cat.setCategoryName(rs.getString("name"));
            
            return cat;
        }    
        
    }    
    
}
