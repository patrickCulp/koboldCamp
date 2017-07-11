/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author pculp
 */
public class Record {
    
    
    private Integer recordID;
    private Integer assetID;
    private Integer employeeID;
    private Integer memberID;
    private int statusID;
    private Date dateOfRecord;
    private String note;
    private String status;
    private String memberName;
    private String employeeName;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    public Integer getAssetID() {
        return assetID;
    }

    public void setAssetID(Integer assetID) {
        this.assetID = assetID;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public int getStatusID() {
        return statusID;
    }
    
    public String getStatus(){
        
        return status;
    }
    
    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(Date dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }
    
    private String stringFromDate(Date date){
        String dString;
            //SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
            dString = formater.format(date);
        return dString;
    }
    
    public String getRecordDateString(){
        String RDS = stringFromDate(dateOfRecord);
        return RDS;
    }
}
