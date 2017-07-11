/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp;

import com.rockon.koboldcamp.dao.DaoAsset;
import com.rockon.koboldcamp.dao.DaoCategory;
import com.rockon.koboldcamp.dao.DaoRecord;
import com.rockon.koboldcamp.dao.DaoUser;
import com.rockon.koboldcamp.model.Asset;
import com.rockon.koboldcamp.model.KCUser;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author pculp
 */
public class KoboldCampDaoTest {
    
    private DaoAsset dAsset;
    private DaoCategory dCat;
    private DaoRecord dRec;
    private DaoUser dUser;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dAsset = ctx.getBean("dAsset", DaoAsset.class);
        dCat = ctx.getBean("dCat", DaoCategory.class);
        dRec = ctx.getBean("dRec", DaoRecord.class);
        dUser = ctx.getBean("dUser", DaoUser.class);
        JdbcTemplate cleaner = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    //#1
    public void test001AddArtist(){
        List<KCUser> memberList = dUser.getAllUsers();
        System.out.println("List Item size: " + memberList.size());
        Assert.assertTrue("List Has Items", (memberList.size()>0));
        
    }
    
    @Test
    //#2
    public void test002SearchRentals(){
        List<Asset> assetResults = dAsset.getAllAssets();
        System.out.println("List Item size: " + assetResults.size());
        Assert.assertTrue("List has items", assetResults.size()>0);
        //Assert.assertTrue("List Size  = 3", assetResults.size()==3);
    }
    
    @Test
    //#3 
    public void test003GetUserIDbyUserName(){
        String admin = "test_admin";
        KCUser user = new KCUser();
        user = dUser.getUserByUsername(admin);
        Assert.assertTrue(user != null);
        Assert.assertTrue(user.getUserID()== 1);
    }
}
