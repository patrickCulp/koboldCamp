/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.model;

import com.rockon.koboldcamp.dao.DaoCategory;
import com.rockon.koboldcamp.dao.DaoCategoryImpl;

/**
 *
 * @author pculp
 */
public class Asset {
    
    private Integer assetID;
    private Integer categoryID;
    private String brand;
    private String description;
    private String categoryName;
    private String status;
    private String memberName;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAssetID() {
        return assetID;
    }
    
    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setAssetID(Integer assetID) {
        this.assetID = assetID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
