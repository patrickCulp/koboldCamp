package com.rockon.koboldcamp;

import com.rockon.koboldcamp.dao.DaoAsset;
import com.rockon.koboldcamp.dao.DaoCategory;
import com.rockon.koboldcamp.dao.DaoRecord;
import com.rockon.koboldcamp.dao.DaoUser;
import com.rockon.koboldcamp.model.Asset;
import com.rockon.koboldcamp.model.Category;
import com.rockon.koboldcamp.model.Record;
import com.rockon.koboldcamp.model.KCUser;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author pculp
 */
@Controller

public class KoboldController {

    private DaoAsset dAsset;
    private DaoCategory dCat;
    private DaoRecord dRec;
    private DaoUser dUser;
    private Integer userId;
    private KCUser defaultUser;

    @Inject
    public KoboldController(DaoAsset dAsset, DaoCategory dCat,
            DaoRecord dRec, DaoUser dUser) {
        this.dAsset = dAsset;
        this.dCat = dCat;
        this.dRec = dRec;
        this.dUser = dUser;
    }

    //region PAGES 
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String displayMain(Map<String, Object> model, Model mo) {
        mo.addAttribute("pageName", "home");
        return "home";
    }

    // NOTE: use this method for reference with deleteUser and resetpassword
    @RequestMapping(value = {"/assets_record/{assetID}"},
            method = RequestMethod.GET)
    public String displayAssetsRecord(@PathVariable("assetID") Integer assetId,
            Model mo) {
        List<Record> recordsList = dRec.getAllRecordsByAssetID(assetId);

        for (Record record : recordsList) {
            record.setStatus(dRec.getStatusByID(record.getStatusID()));

            KCUser employee = dUser.getUserByID(record.getEmployeeID());
            KCUser member = dUser.getUserByID(record.getMemberID());

            String employeeFullName = employee.getFirstName() + " "
                    + employee.getLastName();
            String memberFullName = member.getFirstName() + " "
                    + member.getLastName();

            record.setEmployeeName(employeeFullName);
            record.setMemberName(memberFullName);
        }

        mo.addAttribute("recordsList", recordsList);
        mo.addAttribute("pageName", "asssets");
        mo.addAttribute("asset", dAsset.getAssetByID(assetId));
        return "assets_record";
    }

    @RequestMapping(value = {"/assets_status/{assetID}"},
            method = RequestMethod.GET)
    public String displayAssetsStatus(@PathVariable("assetID") Integer assetId,
            Model mo) {
        mo.addAttribute("pageName", "asssets");
        mo.addAttribute("asset", dAsset.getAssetByID(assetId));
        return "assets_status";
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public String displayRentals(Map<String, Object> model, Model mo) {
        List<Category> categories = dCat.getAllCategories();
        List<Asset> assetList = dAsset.getAllAssets();

        for (Asset asset : assetList) {
            Category cat = dCat.getCategoryById(asset.getCategoryID());
            asset.setCategoryName(cat.getCategoryName());
        }
        mo.addAttribute("pageName", "rentals");
        mo.addAttribute("assetList", assetList);
        mo.addAttribute("categories", categories);

        return "rentals";
    }

    @RequestMapping(value = "searchRentals", method = RequestMethod.POST)
    public String searchRentalAssets(HttpServletRequest req, Model mo) {
        String assetID = req.getParameter("assetTag").trim();
        String category = req.getParameter("category");
        Integer intAssetID = null;
        List<Asset> assetList = dAsset.getAllAssets();
        List<Category> categories = dCat.getAllCategories();
        if (assetID.equals("")) {
        } else {
            intAssetID = Integer.parseInt(assetID);
        }

        assetList = dAsset.searchRentals(
                dCat, intAssetID, category);

        for (Asset asset : assetList) {
            Category cat = dCat.getCategoryById(asset.getCategoryID());
            asset.setCategoryName(cat.getCategoryName());
        }

        mo.addAttribute("assetList", assetList);
        mo.addAttribute("pageName", "rentals");
        mo.addAttribute("categories", categories);

        return "rentals";
    }

    @RequestMapping(value = "/assets", method = RequestMethod.GET)
    public String displayAssets(Map<String, Object> model, Model mo) {
       
        mo.addAttribute("pageName", "assets");
        mo.addAttribute("equipmentItems", this.getEquipmentList());
        return "assets";
    }
    
    private List<Asset> getEquipmentList(){
        List<Asset> equipmentItems = dAsset.getAllAssets();
        List<Category> categories = dCat.getAllCategories();
        for (Asset asset : equipmentItems) {
            //Setting Asset Status
            Record record = dRec.getLatestRecord(asset.getAssetID());
            String status;
            if (record != null) {
                status = dRec.getStatusByID(record.getStatusID());
            } else {
                status = "";
            }
            asset.setStatus(status);

            //Setting Member Name
            String memberName;
            if (record != null) {
                KCUser user = dUser.getUserByID(record.getMemberID());
                memberName = user.getFullName();
            } else {
                memberName = "";
            }
            asset.setMemberName(memberName);

            //Setting Catagory Name
            Category cat = dCat.getCategoryById(asset.getCategoryID());
            asset.setCategoryName(cat.getCategoryName());
        }
        return equipmentItems;
    }

    @RequestMapping(value = "/searchAssets", method = RequestMethod.POST)
    public String displayAssetsResultSet(HttpServletRequest req, Model mo) {
        List<Category> categories = new ArrayList<>();
        List<Asset> equipmentItems = dAsset.getAllAssets();
        String assetTag = req.getParameter("assetTag").trim();
        String category = req.getParameter("category").trim();
        String description = req.getParameter("description").trim();
        String status = req.getParameter("status").trim();
        String member = req.getParameter("member").trim();
        Integer assetID = null;  
        String sMember = null;  
        String sCategory = null;
        String sStatus = null;
        String sDescription = null;
        
        Asset asset = new Asset();
        
        if (assetTag.equals("")) {
        } else {
            assetID = Integer.parseInt(assetTag);
        }

        if (category.equals("")) {
        } else {
            sCategory = category;
        }
        
        if (description.equals("")) {
        } else {
            sDescription = description;
        }
        
        if (status.equals("")) {
        } else {
            sStatus = status;
        }
        
        if (member.equals("")) {
        } else {
            sMember = member;
        }

        boolean ranFullSearch = false;
        List<Asset> assetResultSet = new ArrayList<>();
        categories = dCat.getAllCategories();
        if (assetID == null && sMember == null && sCategory == null
                && sStatus == null && sDescription == null) {
            assetResultSet = dAsset.getAllAssets();
        } else if (assetID != null) {
            asset = dAsset.getAssetByID(assetID);
            assetResultSet.add(asset);
        } else {
            ranFullSearch = true;
            assetResultSet = dAsset.searchAssets(dCat, sCategory, sDescription, 
                    sStatus, sMember);
        }
        
        equipmentItems = assetResultSet;
        
        if(!ranFullSearch){
            for (Asset asset2 : equipmentItems) {
                //Setting Asset Status
                Record record = dRec.getLatestRecord(asset2.getAssetID());
                if (record != null) {
                    sStatus = dRec.getStatusByID(record.getStatusID());
                } else {
                    sStatus = "";
                }
                asset2.setStatus(sStatus);

                //Setting Member Name
                String memberName;
                if (record != null) {
                    KCUser user = dUser.getUserByID(record.getMemberID());
                    memberName = user.getFullName();
                } else {
                    memberName = "";
                }
                asset2.setMemberName(memberName);

                //Setting Catagory Name
                Category cat = dCat.getCategoryById(asset2.getCategoryID());
                asset2.setCategoryName(cat.getCategoryName());
            }
        }
        
        
        mo.addAttribute("pageName", "assets");
        mo.addAttribute("equipmentItems", equipmentItems);
        
        return "assets";
    }
    

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public String displayMembers(Map<String, Object> model, Model mo) {
        List<KCUser> memberList = dUser.getAllUsers();
        mo.addAttribute("pageName", "members");
        mo.addAttribute("memberList", memberList);
        return "members";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String displayAdmin(Map<String, Object> model, Model mo) {
        List<KCUser> memberList = dUser.getAllUsers();
        List<Category> categories = dCat.getAllCategories();
        for (KCUser member : memberList) {
            member.setAuthority(dUser.getAuthority(member.getUserID()));
        }
        mo.addAttribute("pageName", "admin");
        mo.addAttribute("memberList", memberList);
        mo.addAttribute("categories", categories);
        return "admin";
    }

    @RequestMapping(value = "/addEquipment", method = RequestMethod.POST)
    public String addEquipment(HttpServletRequest req, Model mo) {
        String category = req.getParameter("category");
        String brand = req.getParameter("brand");
        String description = req.getParameter("description");
        Integer categoryID = dCat.getCategoryIdByName(category);

        Asset asset = new Asset();
        asset.setBrand(brand);
        asset.setCategoryID(categoryID);
        asset.setDescription(description);

        dAsset.addAsset(asset);
        mo.addAttribute("message", "Success");
        List<KCUser> memberList = dUser.getAllUsers();
        for (KCUser member : memberList) {
            member.setAuthority(dUser.getAuthority(member.getUserID()));
        }
        mo.addAttribute("pageName", "admin");
        mo.addAttribute("memberList", memberList);
        return "admin";

    }

    @RequestMapping(value = "/deleteMember/{memberID}", method = RequestMethod.GET)
    public String deleteMember(@PathVariable String memberID,
            Map<String, Object> model, Model mo) {
        dUser.deleteUser(Integer.parseInt(memberID));
        List<KCUser> memberList = dUser.getAllUsers();
        for (KCUser member : memberList) {
            member.setAuthority(dUser.getAuthority(member.getUserID()));
        }
        mo.addAttribute("pageName", "admin");
        mo.addAttribute("memberList", memberList);
        return "admin";
    }

    @RequestMapping(value = "/resetpw/{memberID}", method = RequestMethod.GET)
    //TODO Use a REST endpoint here.
    public String resetPW(@PathVariable String memberID,
            Map<String, Object> model, Model mo) {
        KCUser user = dUser.getUserByID(Integer.parseInt(memberID));
        user.setPassword("kobolds-r-great");
        dUser.updateUser(user);
        List<KCUser> memberList = dUser.getAllUsers();
        for (KCUser member : memberList) {
            member.setAuthority(dUser.getAuthority(member.getUserID()));
        }
        mo.addAttribute("pageName", "admin");
        mo.addAttribute("memberList", memberList);
        return "admin";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String displayProfile(Map<String, Object> model, Model mo) {
        List<Record> history = dRec.getAllRecords();
        for (Record record : history) {
            record.setStatus(dRec.getStatusByID(record.getStatusID()));
            KCUser user = dUser.getUserByID(record.getEmployeeID());
            String employeeName = user.getFullName();
            record.setEmployeeName(employeeName);
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //^^accesses spring security to get current user loggin info via Object object. 
        String username = "";
            if (principal instanceof User) {
                username = ((User)principal).getUsername();
                //^^casting "principal" to type user to call getUserName method.
            } else {
                username = principal.toString();
            } //^^cateches if for some reason "principal" was not an instance of type User.
        KCUser user = dUser.getUserByUsername(username);
        userId = user.getUserID();
        defaultUser = dUser.getUserByID(userId);
        mo.addAttribute("defaultUser", defaultUser);
        mo.addAttribute("pageName", "profile");
        mo.addAttribute("history", history);
        return "profile";
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String editProfile(HttpServletRequest req, Model mo) {
        //TODO display only current user records
        //Edit profile vs. view profile
        List<Record> history = dRec.getAllRecords();
        for (Record record : history) {
            record.setStatus(dRec.getStatusByID(record.getStatusID()));
            KCUser user = dUser.getUserByID(record.getEmployeeID());
            String employeeName = user.getFullName();
            record.setEmployeeName(employeeName);
        }
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //^^accesses spring security to get current user loggin info via Object object. 
        String username = "";
            if (principal instanceof User) {
                username = ((User)principal).getUsername();
                //^^casting "principal" to type user to call getUserName method.
            } else {
                username = principal.toString();
            } //^^cateches if for some reason "principal" was not an instance of type User.
        KCUser user = dUser.getUserByUsername(username);
        userId = user.getUserID();
        defaultUser = dUser.getUserByID(userId);
        KCUser newUser = new KCUser();
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setUserID(defaultUser.getUserID());

        dUser.updateUserProfile(newUser);
        defaultUser = dUser.getUserByID(userId);

        mo.addAttribute("message", "Success");
        mo.addAttribute("defaultUser", defaultUser);
        mo.addAttribute("history", history);

        return "profile";

    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.POST)
    public String changePassword(HttpServletRequest req, Model mo) {
        List<Record> history = dRec.getAllRecords();
        defaultUser = dUser.getUserByID(userId);
        KCUser newUser = new KCUser();
        String entryOne = req.getParameter("password");
        String entryTwo = req.getParameter("matchPassword");

        if (entryOne.equals(entryTwo)) {
            newUser.setUserName(defaultUser.getUserName());
            newUser.setPassword(entryOne);
            newUser.setEnable(defaultUser.getEnable());
            newUser.setUserID(userId);
        } else {
            String passwordFail
                    = "Passowrd entries do not match. Is caps lock on? "
                    + "Please Try again.";
        }

        newUser.setUserID(defaultUser.getUserID());

        dUser.updateUser(newUser);
        defaultUser = dUser.getUserByID(userId);

        mo.addAttribute("message", "Success");
        mo.addAttribute("defaultUser", defaultUser);
        mo.addAttribute("history", history);

        return "profile";

    }

    //endregion PAGES 
    //FORM SUBMITION
    @RequestMapping(value = "/editstatus", method = RequestMethod.POST)
//    @ResponseBody @ResponseStatus(HttpStatus.CREATED)
    public String editStatus(HttpServletRequest req, Model mo) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //^^accesses spring security to get current user loggin info via Object object. 
        String username = "";
            if (principal instanceof User) {
                username = ((User)principal).getUsername();
                //^^casting "principal" to type user to call getUserName method.
            } else {
                username = principal.toString();
            } //^^cateches if for some reason "principal" was not an instance of type User.
        KCUser user = dUser.getUserByUsername(username);
        String memberID = req.getParameter("memberID");
        Integer status = Integer.parseInt(req.getParameter("status"));
        String extraNotes = req.getParameter("extraNotes");
        Integer assetID = Integer.parseInt(req.getParameter("assetID"));

        Record record = new Record();
        record.setMemberID(Integer.parseInt(memberID));
        record.setStatusID(status);
        record.setNote(extraNotes);
        record.setAssetID(assetID);
        record.setEmployeeID(user.getUserID());
        record.setDateOfRecord(new Date());

        dRec.addRecord(record);
        mo.addAttribute("message", "Success");
        mo.addAttribute("equipmentItems", getEquipmentList());
        return "assets";
    }
    
    
    
//    @RequestMapping (value = "/home", method = RequestMethod.POST)
//    public Boolean checkUser (HttpServletRequest req, Model mo) {
//        Boolean isUser = false;
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        User userPrime = null;
//        for(User user : dUser.getAllUsers()){
//            if(user.getUserName().equals(username)){
//                userPrime = user;
//                if(userPrime.getPassword().equals(password)){
//                    isUser = true;
//                }               
//            }
//        }
//        return isUser;
//    }

    @RequestMapping(value = "/members", method = RequestMethod.POST)
    public String addMember(HttpServletRequest req, Model mo) {
        String username = req.getParameter("username");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String employee = req.getParameter("employee");
        String admin = req.getParameter("admin");

        if (dUser.checkUserNameExists(username) == false) {

            List<String> authorities = new ArrayList<>();
            if (admin != null && admin.equals("on")) {
                authorities.add("ROLE_ADMIN");
            }
            if (employee != null && employee.equals("on")) {
                authorities.add("ROLE_EMPLOYEE");
            }
            authorities.add("ROLE_USER");

            KCUser member = new KCUser();
            member.setUserName(username);
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setEmail(email);
            member.setPhone(phone);
            member.setEnable(1);

            dUser.addUserProfile(member);
            dUser.addAuthority(member, authorities);
        } else {
            mo.addAttribute(
                    "message", "This user name is taken. Please select another.");
        }

        List<KCUser> memberList = dUser.searchMembers(
                null, null);
        mo.addAttribute("pageName", "members");
        mo.addAttribute("memberList", memberList);

        return "members";
    }

    //SEARCH FUNCTIONALLITY
    

    @RequestMapping(value = "searchMembers", method = RequestMethod.POST)
    public String searchMembers(HttpServletRequest req, Model mo) {
        String memberID = req.getParameter("memberID").trim();
        String memberName = req.getParameter("memberName").trim();
        Integer intMemberID = null;
        if (memberID == "") {
        } else {
            intMemberID = Integer.parseInt(memberID);
        }
        List<KCUser> memberList = dUser.searchMembers(
                intMemberID, memberName);
        mo.addAttribute("memberList", memberList);
        mo.addAttribute("pageName", "members");

        return "members";
    }

    @RequestMapping(value = "searchMembersAdmin", method = RequestMethod.POST)
    public String searchMembersAdmin(HttpServletRequest req, Model mo) {
        String memberID = req.getParameter("memberID").trim();
        String memberName = req.getParameter("memberName").trim();
        Integer intMemberID = null;
        if (memberID == "") {
        } else {
            intMemberID = Integer.parseInt(memberID);
        }
        List<KCUser> memberList = dUser.searchMembers(
                intMemberID, memberName);
        mo.addAttribute("memberList", memberList);
        mo.addAttribute("pageName", "admin");
        return "admin";
    }

}
