/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.dao;

import com.rockon.koboldcamp.model.Asset;
import java.util.List;

/**
 *
 * @author pculp
 */
public interface DaoAsset {
    
    //Create
    public Asset addAsset(Asset asset);
    
    //Read
    public Asset getAssetByID(Integer assetID);
    public List<Asset> getAllAssets();
    public List<Asset> searchRentals(DaoCategory dCat, Integer assetID, 
            String category);
    public List<Asset> searchAssets (DaoCategory dCat,
            String category, String descirption, String status, String member);
    
    //Update
    public Asset updateAsset(Asset asset);
    
    //Delete
    public void deleteAsset(Integer assetID);
}
