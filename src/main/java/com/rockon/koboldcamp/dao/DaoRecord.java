/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.dao;

import com.rockon.koboldcamp.model.Record;
import java.util.List;

/**
 *
 * @author pculp
 */
public interface DaoRecord {
    
    
    //Create
    public Record addRecord(Record record);
    
    //Read
    public Record getRecordByID(Integer recordID);
    public List<Record> getAllRecords();
    public List<Record> getAllRecordsByAssetID(Integer assetID);
    public String getStatusByID(Integer statusID);   
    public List<Record> getAllRecordsByUserID(Integer userID);
    public Record getLatestRecord(Integer assetID);
            
    //Updates
    public Record updateRecord(Record record);
    
    //Delete
    public void deleteRecord(Integer recordID);
}
