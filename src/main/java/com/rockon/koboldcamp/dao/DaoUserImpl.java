/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.dao;

import com.rockon.koboldcamp.model.KCUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author pculp
 */
public class DaoUserImpl implements DaoUser {
    
    private static final String defaultPassword = "kobolds-r-great";
            
    
    private static JdbcTemplate jdbcTemplate;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }    
    
    //Create
    private static final String SQL_INSERT_USER 
            = "INSERT INTO users (username, password, enabled) VALUES (?,?,?)";
    private int enabled = 1;
    
    @Override 
    public KCUser addUser(KCUser user) {
        jdbcTemplate.update(SQL_INSERT_USER,
                user.getUserName(),
                defaultPassword,
                enabled);
        Integer generatedId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()",
                Integer.class);
        user.setUserID(generatedId);
        
        return user;
    }
    
    private static final String SQL_INSERT_USER_PROFILE
            = "INSERT INTO user_profiles (user_id, first_name, last_name, "
            + "email, phone) VALUES (?,?,?,?,?)";
    
    @Override
    public KCUser addUserProfile(KCUser user){
        
        user = addUser(user);
        jdbcTemplate.update(SQL_INSERT_USER_PROFILE,
                user.getUserID(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone());

        return user;
    }
    
    private static final String SQL_ADD_AUTHORITY
            = "INSERT INTO `authorities` (`username`, `authority`) VALUES (?,?)";
    
    @Override
    public void addAuthority(KCUser user, List<String> authorities){
        for(String authority: authorities){
            jdbcTemplate.update(SQL_ADD_AUTHORITY,
                    user.getUserName(),
                    authority);
        }
    }
    

    
    //Read 
    private static final String SQL_GET_USER_PROFILES_BY_ID = 
            "SELECT * FROM users LEFT JOIN user_profiles ON "
            + "user_profiles.user_id = users.user_id WHERE users.user_id = ?;";
    
    @Override
    public KCUser getUserByID(Integer userID){
        return jdbcTemplate.queryForObject(SQL_GET_USER_PROFILES_BY_ID, 
                new UserMapper(), userID);
    }
    
    private static final String SQL_GET_USER_BY_USERNAME = 
            "SELECT users.user_id, users.password, users.enabled, \n" +
            "user_profiles.first_name, user_profiles.last_name, \n" +
            "user_profiles.email, user_profiles.phone, users.username \n" +
            "FROM users\n" +
            "JOIN user_profiles ON user_profiles.user_id = users.user_id \n" +
            "WHERE users.username = ?";
    @Override
    public KCUser getUserByUsername(String userName){
        return jdbcTemplate.queryForObject(SQL_GET_USER_BY_USERNAME, 
                new UserMapper(), userName);
    }
    //String name = authentication.getName();
    //^^this will get me the username from spring security.
    
    
    private static final String SQL_GET_AUTHORITIES_BY_USER_NAME = 
            "SELECT authority FROM authorities WHERE username = ?";
    @Override
    public List<String> getAthoritiesByUserID(Integer userID){
        List<String> authorities = jdbcTemplate.query(
                SQL_GET_AUTHORITIES_BY_USER_NAME,                
                new AuthorityMapper(), this.getUserByID(userID).getUserName());
                
        return authorities;
    }
    
    
    private static final String SQL_GET_ALL_USER_PROFILES =
        "SELECT * FROM users LEFT JOIN user_profiles ON "
        + "user_profiles.user_id = users.user_id";
    
    @Override
    public List<KCUser> getAllUsers(){
        
        List<KCUser> allUsers = jdbcTemplate.query(SQL_GET_ALL_USER_PROFILES, 
                new UserMapper());
        
        return allUsers;
        
    } 
    //Read - Search functionality
    @Override
    public List<KCUser> searchMembers(Integer memberID, String memberName){
        List<KCUser> foundMembers = null;
        String SQL_SEARCH_MEMBERS =
        "SELECT * FROM users LEFT JOIN user_profiles ON "
        + "user_profiles.user_id = users.user_id ";
        
        if(memberID==null && (memberName==null || memberName=="")){
            //Senario: Both Fields Empty
            foundMembers = jdbcTemplate.query(SQL_SEARCH_MEMBERS, 
                new UserMapper());
        }else{
            //Senario: ever6ything else
            if(memberID!=null && (memberName!=null && memberName!="")){
                //Senario: Both have Values
                if(memberName.indexOf(" ")!=-1){
                    //Senario: Has both a 1st & Last Name value
                    String[] names = memberName.split(" ");
                    SQL_SEARCH_MEMBERS = SQL_SEARCH_MEMBERS + "WHERE "
                            + "users.user_id = ? AND user_profiles.first_name " 
                            + "LIKE '%"+names[0]+"%' AND user_profiles.last_name LIKE "
                            + "'%"+names[1]+"%'";
                    foundMembers = jdbcTemplate.query(SQL_SEARCH_MEMBERS, 
                    new UserMapper(), memberID);
                }else{
                    //Senario: has only one name to search
                    SQL_SEARCH_MEMBERS = SQL_SEARCH_MEMBERS + "WHERE "
                            + "users.user_id = ? AND user_profiles.first_name " 
                            + "LIKE '%"+memberName+"%' OR user_profiles.last_name LIKE "
                            + "'%"+memberName+"%'";
                    foundMembers = jdbcTemplate.query(SQL_SEARCH_MEMBERS, 
                    new UserMapper(), memberID);
                }
            }else if(memberID!=null && (memberName==null || memberName=="")){
                //Senario: MemberID has value, name does not.
                SQL_SEARCH_MEMBERS = SQL_SEARCH_MEMBERS + "WHERE users.user_id = ?";
                foundMembers = jdbcTemplate.query(SQL_SEARCH_MEMBERS, 
                new UserMapper(), memberID);
            }else if(memberID==null && (memberName!=null && memberName!="")){
                if(memberName.indexOf(" ")!=-1){
                    //Senario: Has both a 1st & Last Name value
                    String[] names = memberName.split(" ");
                    SQL_SEARCH_MEMBERS = SQL_SEARCH_MEMBERS + "WHERE "
                            + "user_profiles.first_name LIKE '%"+names[0]+"%' AND "
                            + "user_profiles.last_name LIKE '%"+names[1]+"%'";
                    foundMembers = jdbcTemplate.query(SQL_SEARCH_MEMBERS, 
                    new UserMapper());
                }else{
                    //Senario: has only one name to search
                   SQL_SEARCH_MEMBERS = SQL_SEARCH_MEMBERS + "WHERE "
                            + "user_profiles.first_name LIKE '%"+memberName+"%' OR "
                            + "user_profiles.last_name LIKE '%"+memberName+"%'";
                    foundMembers = jdbcTemplate.query(SQL_SEARCH_MEMBERS, 
                    new UserMapper());
                }
            }
            
        }
        
        
        return foundMembers;
        
    }
    
    private static final String SQL_CHECK_USERNAME_EXISTS =
        "SELECT * FROM users LEFT JOIN user_profiles ON "
        + "user_profiles.user_id = users.user_id WHERE users.username = ?";
    
    @Override
    public boolean checkUserNameExists(String username){
        boolean yea = false;
        List<KCUser> foundUsername = jdbcTemplate.query(SQL_CHECK_USERNAME_EXISTS,
                new UserMapper(), username);
        if(foundUsername.size()>0){
            yea = true;
        }
        return yea;
    }
    
    
    //will return: member,employee or admin
    @Override 
    public String getAuthority(Integer userID){
        List<String> authorities = getAthoritiesByUserID(userID); 
        return topClearance(authorities);
    }
    
    private String topClearance(List<String> authorities){
        for(String auth: authorities){
            if(auth.equals("ROLE_ADMIN")){
                return "Admin";
            }else if(auth.equals("ROLE_EMPLOYEE")){
                return "Employee";
            }else if(auth.equals("ROLE_USER")){
                return "Member";
            }    
        }return "";
    }
    
    //Update 
    private static final String SQL_UPDATE_USER 
            = "UPDATE users SET username = ?, password = ?, enabled = ? "
            + "WHERE user_id = ?";
        
    @Override 
    public KCUser updateUser(KCUser user) {
        jdbcTemplate.update(SQL_UPDATE_USER, 
                user.getUserName(),
                user.getPassword(),       
                user.getEnable(),
                user.getUserID()
        );
                        
        return user;
    }
    
    private static final String SQL_UPDATE_USER_PROFILE
            = "UPDATE user_profiles SET first_name = ?, last_name = ?, email = ?,"
            + " phone = ? WHERE user_id = ?";
    
    @Override 
    public KCUser updateUserProfile(KCUser user) {
        jdbcTemplate.update(SQL_UPDATE_USER_PROFILE,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),          
                user.getPhone(),
                user.getUserID()
        );
        
        return user;
    }
    
    //Delete
    private static final String SQL_DELETE_ASSET_RECORD
            = "DELETE FROM asset_records where member_id = ?";
    private static final String SQL_DELETE_AUTHORITIES 
            = "DELETE FROM authorities WHERE username = ?";
    private static final String SQL_DELETE_USER_PROFILE
            = "DELETE FROM user_profiles where user_id = ?";
    private static final String SQL_DELETE_USER
            = "DELETE FROM users where user_id = ?";
    
    @Override 
    public void deleteUser(Integer userID) {
        KCUser user = new KCUser();
        user = getUserByID(userID);
        String username = user.getUserName();
        jdbcTemplate.update(SQL_DELETE_ASSET_RECORD, userID);
        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, username);
        jdbcTemplate.update(SQL_DELETE_USER_PROFILE, userID);
        jdbcTemplate.update(SQL_DELETE_USER, userID);    
    }
    
    
    private static final class UserMapper implements RowMapper<KCUser> {
        
        @Override
        public KCUser mapRow(ResultSet rs, int i) throws SQLException {
            
            KCUser newUser = new KCUser();
            
            newUser.setUserID(rs.getInt("user_id"));
            newUser.setUserName(rs.getString("username"));
            newUser.setPassword(rs.getString("password"));
            newUser.setEnable(rs.getInt("enabled"));
            newUser.setFirstName(rs.getString("first_name"));
            newUser.setLastName(rs.getString("last_name"));
            newUser.setEmail(rs.getString("email"));
            newUser.setPhone(rs.getString("phone"));
            
            return newUser;
        }   
    }    
    
    private static final class AuthorityMapper implements RowMapper<String> {
        
        @Override
        public String mapRow(ResultSet rs, int i) throws SQLException {
            
            String authority = (rs.getString("authority"));
            
            return authority;
        }   
    }    
}
