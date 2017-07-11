<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Kobold Camp Home</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <!-- SWC Icon -->
        <link rel="shortcut icon" 
              href="${pageContext.request.contextPath}/img/koboldCampIcon.png">

    </head>
    <body>
        <div class="container">
            <%@include file="navIncludes.jsp" %>
            <form action="editstatus" method="POST">
                <div class="col-md-6">
                        <fieldset>
                            <h3 align="left">Equipment Status: Tag# ${asset.getAssetID()}</h3>
                            </br></br>
                            <p>${message}</p>
                            <span>Member Id:</span>
                            <input class="form-control" 
                                   style="display: inline-block; width: 45%; 
                                   margin-bottom: 0px" 
                                   type="text" name="memberID" id="memberID" >

                            </br></br>
                            <!--TODO: THIS TAG IS NOT DYNAMIC YET. FIX ME!!!!!!-->
                            <select class="form-control" style="display: inline-block; width: 60%; 
                                   margin-bottom: 0px" name="status" id="category">
                                <option value="">Select Status</option>
                                <option value="2">Checked Out</option>
                                <option value="1">Available</option>
                                <option value="4">Lost</option>
                                <option value="3">Broken</option>
                                <option value="5">In Repairs</option>
                            </select>                      
                        </fieldset>
                        <input type="hidden" value="${asset.getAssetID()}" name="assetID">                    
                </div>
                <div class="col-md-6">
                    Extra Notes:
                            <br/>
                            <textarea name="extraNotes" class="form-control" 
                                      style="margin-top: 5px" rows="4" cols="45"></textarea>
                            <input type="submit" value="Submit" class="btn"
                                   style="display: block; width: 25%; margin-top: 15px;"/>
                </div>    
            </form>
                <script src="js/jquery-1.12.2.min.js"></script>
                <script src="js/bootstrap.min.js"></script>
        </div>    
    </body>
</html>

