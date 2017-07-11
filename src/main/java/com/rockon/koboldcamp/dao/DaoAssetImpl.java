/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.dao;

import com.rockon.koboldcamp.model.Asset;
import com.rockon.koboldcamp.model.AssetSearchResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author pculp
 */
public class DaoAssetImpl implements DaoAsset {
    
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;
        
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }    
        
//    public void setNamedParamJdbcTemplate(NamedParameterJdbcTemplate namedParamJdbcTemplate) {
//        this.namedParamJdbcTemplate = namedParamJdbcTemplate;
//    }    
    
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    //Create
    
    private static final String SQL_INSERT_ASSET
        = "INSERT INTO assets (category_id, brand, description) VALUES (?,?,?)";
    
    @Override
    public Asset addAsset(Asset asset) {
        jdbcTemplate.update(SQL_INSERT_ASSET,
                asset.getCategoryID(),
                asset.getBrand(),
                asset.getDescription());
        Integer generatedId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()",
                Integer.class);
        asset.setAssetID(generatedId);
        
        return asset;
    }
        
    //Read 
    
    private static final String SQL_GET_ASSET_BY_ID = 
            "SELECT * FROM assets WHERE asset_id = ?";  
    
    @Override
    public Asset getAssetByID(Integer assetID){
        Asset a = jdbcTemplate.queryForObject(SQL_GET_ASSET_BY_ID,  
                new AssetMapper(), assetID);
        return a;
    }
    
    private static final String SQL_GET_ALL_ASSETS = "SELECT * FROM assets";
    
    @Override
    public List<Asset> getAllAssets(){
        List<Asset> assetList = jdbcTemplate.query(SQL_GET_ALL_ASSETS, 
                new AssetMapper());
                
        return assetList;
    }
    
    private static final String SQL_GET_ALL_ASSETS_BY_CATEGORY = 
            "SELECT * FROM assets WHERE category_id = ?";
    
    public List<Asset> getAllAssetsByCategoryID(Integer categoryID){
        List<Asset> assetList = jdbcTemplate.query(
                SQL_GET_ALL_ASSETS_BY_CATEGORY, new AssetMapper(), categoryID);
                
        return assetList;
    }
    
    private String handleMemberNameSearch(String query, Map<String, Object> searchFields, String memberText){
        // String.format(query, "query where clause from method")
        // member field are going here
        // method : add to query string : where clause for first name & last
        // method also: add respective values to search fields: 
        // condition1:(space in member string) first/last 
        // condition2:(no space) use same string for both
       if(memberText.contains(" ")){
           String firstName;
           String lastName;
           String[] nameArray = memberText.split(" ");
           firstName = nameArray[0];           
           lastName = nameArray[1]; 
           searchFields.put("firstName", "%"+firstName+"%");
           searchFields.put("lastName", "%"+lastName+"%");
           query = String.format(query, "(user_profiles.first_name LIKE :firstName AND user_profiles.last_name LIKE :lastName)");
       }else{
           query = String.format(query, "(user_profiles.first_name LIKE :firstName OR user_profiles.last_name LIKE :lastName)");
           searchFields.put("firstName", "%"+memberText+"%");
           searchFields.put("lastName", "%"+memberText+"%");

       }
       return query;
    }
        
    String query = "";
    
    @Override
    public List<Asset> searchAssets (DaoCategory dCat,
            String category, String description, String status, String member){
        
        namedParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        
        List<Asset> assetResultSet = new ArrayList<>();
        Map<String,Object> searchFields = new HashMap<>();
        String assetFields = "assets.asset_id, categories.name AS CategoryName,"
                + " assets.brand, assets.description, asset_statuses.status, "
                + "user_profiles.first_name, user_profiles.last_name ";
        if(category != null){
            if(description != null){
                if(status != null){
                    if(member != null){
                        // your case of all field being filled except assetID
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE categories.name = :categoryName "
                                + "AND assets.description LIKE :description "
                                + "AND %1s "
                                + "AND asset_statuses.status = :status";
                        searchFields.put("categoryName", category);
                        searchFields.put("description", "%"+description+"%");
                        searchFields.put("status", status);
                        
                        query = handleMemberNameSearch(query, searchFields, member);
                                                      
                    }else{
                        // all filled in except Member
                        query = "SELECT "+ assetFields +"  FROM assets "+
                            "JOIN categories ON categories.category_id = assets.category_id \n" +
                            "JOIN asset_records ON asset_records.asset_id = assets.asset_id \n" +
                            "JOIN user_profiles ON user_profiles.user_id = asset_records.member_id \n" +
                            "JOIN asset_statuses ON asset_statuses.status_id = asset_records.status_id \n" +
                            "WHERE categories.name = :categoryName \n" +
                            "AND assets.description LIKE :description \n" +
                            "AND asset_statuses.status = :status";
                        searchFields.put("categoryName", category);
                        searchFields.put("description", "%"+description+"%");
                        searchFields.put("status", status);
                                
                    }
                }else{ //status ==  null
                    // category & description filled in but NOT status
                    // check member
                    if(member != null){
                        query = "SELECT "+ assetFields +"  FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE categories.name = :categoryName "
                                + "AND assets.description LIKE :description "
                                + "AND %1s";
                        searchFields.put("categoryName", category);
                        searchFields.put("description", "%"+description+"%");
                        
                        query = handleMemberNameSearch(query, searchFields, member);
                    }else{ //member == null
                        query = "SELECT "+ assetFields +"  FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE categories.name = :categoryName "
                                + "AND assets.description LIKE :description";
                        
                        searchFields.put("categoryName", category);
                        searchFields.put("description", "%"+description+"%");
                    }
                }
            }else{ //description == null
                // category filled in but not description
                // check status and member
                if(status != null){
                    if(member != null){
                        // Only description is null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE categories.name = :categoryName "
                                + "AND %1s "
                                + "AND asset_statuses.status = :status";
                        searchFields.put("categoryName", category);
                        searchFields.put("status", status);
                        
                        query = handleMemberNameSearch(query, searchFields, member);                  
                    }else{ // member == null
                        // description and member are null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE categories.name = :categoryName "
                                + "AND asset_statuses.status = :status";
                        searchFields.put("categoryName", category);
                        searchFields.put("status", status);
                    }
                }else{ //status == null
                    // category filled in but NOT status
                    // check member
                    if(member != null){
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE categories.name = :categoryName "
                                + "AND %1s ";
                        searchFields.put("categoryName", category);
                        
                        query = handleMemberNameSearch(query, searchFields, member);
                    }else{ // member == null
                           // Only Category is !null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE categories.name = :categoryName ";
                        searchFields.put("categoryName", category);
                    }
                }
            }
        }else{ //category == null
            if(description != null){
                if(status != null){
                    if(member != null){// Only Category Null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE assets.description LIKE :description "
                                + "AND %1s "
                                + "AND asset_statuses.status = :status";
                        searchFields.put("description", "%"+description+"%");
                        searchFields.put("status", status);
                        
                        query = handleMemberNameSearch(query, searchFields, member);
                    }else{// Category and Member are null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE assets.description LIKE :description "
                                + "AND asset_statuses.status = :status";
                        searchFields.put("description", "%"+description+"%");
                        searchFields.put("status", status);
                    }
                }else{ //Status is null
                    //description !null
                    // check member
                    if(member != null){
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE assets.description LIKE :description "
                                + "AND %1s ";
                        searchFields.put("description", "%"+description+"%");
                        
                        query = handleMemberNameSearch(query, searchFields, member);
                    }else{ //Only description !null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE assets.description LIKE :description ";
                        searchFields.put("description", "%"+description+"%");
                    }
                }
            }else{ //description null
                // category filled in but not description
                // check status and member
                if(status != null){
                    if(member != null){// category & description are null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE %1s "
                                + "AND asset_statuses.status = :status";
                        searchFields.put("status", status);
                        
                        query = handleMemberNameSearch(query, searchFields, member);
                    }else{// Only Status !null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE asset_statuses.status = :status";
                        searchFields.put("status", status);
                    }
                }else{ //Category Status and description are null
                    // category filled in but NOT status
                    // check member
                    if(member != null){ //only member !null
                        query = "SELECT "+ assetFields +" FROM assets JOIN categories ON "
                                + "categories.category_id = "
                                + "assets.category_id "
                                + "JOIN asset_records ON asset_records.asset_id "
                                + "= assets.asset_id "
                                + "JOIN user_profiles ON user_profiles.user_id "
                                + "= asset_records.member_id "
                                + "JOIN asset_statuses ON "
                                + "asset_statuses.status_id = "
                                + "asset_records.status_id "
                                + "WHERE  %1s ";
                        query = handleMemberNameSearch(query, searchFields, member);
                    }else{ 
                        // all null handle under getAssetByID method.
                    }
                }
            }
        }
//        Object[] searchFieldsArray = searchFields.toArray();
        assetResultSet = namedParamJdbcTemplate.query(query, searchFields, new AssetSearchResultSetMapper());
        return assetResultSet;
    }
    
    @Override
    public List<Asset> searchRentals(DaoCategory dCat, Integer assetID, 
            String category){
        Asset asset = new Asset();
        List<Asset> assetResults = new ArrayList<>();
        
            if(assetID == null && (category == null || category.equals("")) ){
                // return all
                assetResults = getAllAssets();
            }else if(assetID != null){
                // has assetID w/||w/o category
                asset = getAssetByID(assetID);  
                assetResults.add(asset);
            }else if(assetID == null 
                    && (category != null && !category.equals("")) ){
                // has category but no assetID
                Integer catID = dCat.getCategoryIdByName(category);
                assetResults = getAllAssetsByCategoryID(catID);
            }
        return assetResults;
    }
    
    //Update 
    
    private static final String SQL_UPDATE_ASSET
            = "UPDATE assets SET category_id = ?, brand = ?, description = ? "
            + "WHERE userID = ?";
    
    @Override 
    public Asset updateAsset(Asset asset) {
        jdbcTemplate.update(SQL_UPDATE_ASSET,
                asset.getCategoryID(),
                asset.getBrand(),
                asset.getDescription());
        
        return asset;
    }
    
    //Delete
    
    private static final String SQL_DELETE_ASSET = 
            "DELETE FROM assets WHERE asset_id = ?";
    
    public void deleteAsset(Integer assetID) {
        jdbcTemplate.update(SQL_DELETE_ASSET, assetID);
    }
    
    private static final class AssetMapper implements RowMapper<Asset> {

        public Asset mapRow(ResultSet rs, int i) throws SQLException {
            
            Asset asset = new Asset();
            
            asset.setAssetID(rs.getInt("asset_id"));
            asset.setCategoryID(rs.getInt("category_id"));
            asset.setBrand(rs.getString("brand"));
            asset.setDescription(rs.getString("description"));
        
            return asset;
        }    
        
    }    
    
    private static final class AssetSearchResultSetMapper implements RowMapper<Asset> {

        public Asset mapRow(ResultSet rs, int i) throws SQLException {
            
            Asset aSr = new Asset();
            
            aSr.setAssetID(rs.getInt("asset_id"));
            aSr.setCategoryName(rs.getString("CategoryName"));
            aSr.setBrand(rs.getString("brand"));
            aSr.setDescription(rs.getString("description"));
            aSr.setStatus(rs.getString("status"));
            aSr.setMemberName(rs.getString("first_name") + " " + rs.getString("last_name"));
        
            return aSr;
        }    
        
    }    
    
}
    

