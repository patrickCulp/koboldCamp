/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.dao;

import com.rockon.koboldcamp.model.KCUser;
import java.util.List;

/**
 *
 * @author pculp
 */
public interface DaoUser {
    
    //Create
    public KCUser addUser(KCUser user);
    public KCUser addUserProfile(KCUser user); 
    public void addAuthority(KCUser user, List<String> authorities);
    
    //Read
    public KCUser getUserByID(Integer userID);
    public KCUser getUserByUsername(String userName);
    //public Integer getUserIDbyUser(User user);
    public List<String> getAthoritiesByUserID(Integer userID);
    public List<KCUser> getAllUsers();
    public String getAuthority(Integer userID);
    
    //Read Search
    public List<KCUser> searchMembers(Integer memberID, String memberName);
    public boolean checkUserNameExists(String username);
    
    //Update
    public KCUser updateUser(KCUser user);   //FIX ME!!
    public KCUser updateUserProfile(KCUser user);  //FIX ME!!
    
    //Delete
    public void deleteUser(Integer userID);
    
}
