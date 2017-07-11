/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.dao;

import com.rockon.koboldcamp.model.Record;
import com.rockon.koboldcamp.model.KCUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author pculp
 */
public class DaoRecordImpl implements DaoRecord {
    
    private JdbcTemplate jdbcTemplate;
      
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }    
    
    //Create
    private static final String SQL_INSERT_RECORD 
            = "INSERT INTO asset_records (asset_id, employee_id, member_id, "
            + "status_id, record_date, note) VALUES (?,?,?,?,?,?)";
    
    public Record addRecord(Record record) {
        jdbcTemplate.update(SQL_INSERT_RECORD,
                record.getAssetID(),
                record.getEmployeeID(),
                record.getMemberID(),
                record.getStatusID(),
                record.getDateOfRecord(),
                record.getNote());
        Integer generatedId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()",
                Integer.class);
        record.setRecordID(generatedId);
        
    return record;
    }
    
    //Read 
    private static final String SQL_GET_RECORDS_BY_ID = 
            "SELECT * FROM asset_records WHERE record_id = ?";  
    
    @Override
    public Record getRecordByID(Integer recordID){
        Record record = jdbcTemplate.queryForObject(SQL_GET_RECORDS_BY_ID,  
                new RecordMapper(), recordID);
        return record;
    }
    
    private static final String SQL_GET_STATUS_BY_ID = 
            "SELECT status FROM asset_statuses WHERE status_id = ?";    
    
    @Override
    public String getStatusByID(Integer statusID){
        String status= jdbcTemplate.queryForObject(SQL_GET_STATUS_BY_ID,  
                new StatusMapper(), statusID);
        return status;
    }
    
    
    private static final String SQL_GET_ALL_RECORDS_PROFILES =
        "SELECT * FROM asset_records";
    
    @Override
    public List<Record> getAllRecords(){
        List<Record> allRecords = jdbcTemplate.query(
                SQL_GET_ALL_RECORDS_PROFILES, new RecordMapper());
        
        return allRecords;
    }
    
    private static final String SQL_GET_ALL_RECORDS_BY_ASSETID =
        "SELECT * FROM asset_records WHERE asset_id = ?";
    
    @Override
    public List<Record> getAllRecordsByAssetID(Integer assetID){
        List<Record> allRecords = jdbcTemplate.query(
                SQL_GET_ALL_RECORDS_BY_ASSETID,
                new RecordMapper(),
                assetID);
        
        return allRecords;
    }
    
    private static final String SQL_GET_ALL_RECORDS_BY_USERID =
        "SELECT * FROM asset_records WHERE member_id = ?";
    
    @Override
    public List<Record> getAllRecordsByUserID(Integer userID){
        List<Record> allRecords = jdbcTemplate.query(
                SQL_GET_ALL_RECORDS_BY_USERID,
                new RecordMapper(),
                userID);
        
        return allRecords;
    }
    
    
    private static final String SQL_GET_LATEST_RECORD_BY_ASSETID =
        "SELECT * FROM asset_records WHERE asset_id = ? ORDER By "
            + "record_date desc limit 1";
    
    @Override
    public Record getLatestRecord(Integer assetID){
        List<Record> records = jdbcTemplate.query(
                SQL_GET_LATEST_RECORD_BY_ASSETID,
                new RecordMapper(),
                assetID);
                if(records.size()<1){
                    return null;
                }                        
        return records.get(0);
    }
    
    //Update 
    private static final String SQL_UPDATE_RECORD
            = "UPDATE asset_record SET asset_id = ?, employee_id = ?, "
            + "member_id = ?, status_id = ?, record_date = ?, note = ?"
            + " WHERE userID = ?";
    
    @Override 
    public Record updateRecord(Record record) {
        jdbcTemplate.update(SQL_UPDATE_RECORD, 
                record.getAssetID(),
                record.getEmployeeID(),
                record.getMemberID(),
                record.getStatusID(),
                record.getDateOfRecord(),
                record.getNote(),   
                record.getRecordID()
        );

        return record;
    }
    
    //Delete
    private static final String SQL_DELETE_USER_PROFILE
            = "DELETE FROM asset_records where record_id = ?";
    
    public void deleteRecord(Integer recordID) {
        jdbcTemplate.update(SQL_DELETE_USER_PROFILE,recordID);
    }
    
    private static final class RecordMapper implements RowMapper<Record> {
        
        @Override
        public Record mapRow(ResultSet rs, int i) throws SQLException {
            
            Record record = new Record();
            
            record.setRecordID(rs.getInt("record_id"));
            record.setAssetID(rs.getInt("asset_id"));
            record.setEmployeeID(rs.getInt("employee_id"));
            record.setMemberID(rs.getInt("member_id"));
            record.setStatusID(rs.getInt("status_id"));
            record.setDateOfRecord(rs.getDate("record_date"));  
            record.setNote(rs.getString("note"));
        
            return record;
        }    
        
    }    
    
    private static final class StatusMapper implements RowMapper<String> {
        
        @Override
        public String mapRow(ResultSet rs, int i) throws SQLException {
            
            String status = (rs.getString("status"));
        
            return status;
        }    
        
    }
}
