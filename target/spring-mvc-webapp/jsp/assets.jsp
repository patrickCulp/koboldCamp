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
        
        <link rel="shortcut icon" 
              href="${pageContext.request.contextPath}/img/koboldCampIcon.png">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/koboldCSS.css">

    </head>
	<body>
            <div class="container">
                <%@include file="navIncludes.jsp" %> 
                <h3 align="left">Kobold Camp Assets</h3>
                <form action="searchAssets" method="POST">
                    <fieldset>
                        <legend>
                            Search
                        </legend>
                        <input class="form-control" 
                               style="float:left; display:inline-block; width: 15%; margin-left: 20px; margin-top: 5px" 
                               type="text" name="assetTag" id="assetTag" placeholder="  Asset Tag">
                        <input class="form-control" 
                               style="float:left; display:inline-block; width: 15%; margin-left: 20px; margin-top: 5px" 
                               type="text" name="category" 
                               id="category" placeholder="  Category">
                        <input class="form-control" 
                               style="float:left; display:inline-block; width: 15%; margin-left: 20px; margin-top: 5px" 
                               type="text" name="description" 
                               id="description" placeholder="  Description">
                        <input class="form-control" 
                               style="float:left; display:inline-block; width: 15%; margin-left: 20px; margin-top: 5px" 
                               type="text" name="status" 
                               id="status" placeholder="  Status">
                        <input class="form-control" 
                               style="float:left; display:inline-block; width: 15%; margin-left: 20px; margin-top: 5px" 
                               type="text" name="member" 
                               id="member" placeholder="  Member">
                        <input type="submit" value="Search" class="btn"
                               style="float:left; display:inline-block; width: 10%; margin-left: 40px"/>
                    </fieldset>
                </form>
                <table class="table table-hover"
                       style="float:left; margin-top: 25px"/>
                    <thead>
                      <tr>
                        <th>Asset Tag</th>
                        <th>Category</th>
                        <th>Brand</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Member</th>
                        <th>History</th>
                        <th>Check In/Out</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${equipmentItems}" var="asset">
                            <tr>
                              <td>${asset.getAssetID()}</td>
                              <td>${asset.getCategoryName()}</td>
                              <td>${asset.getBrand()}</td>
                              <td>${asset.getDescription()}</td>
                              <td>${asset.getStatus()}</td>
                              <td>${asset.getMemberName()}</td>
                              <td align="center"> 
                                  <a href="assets_record/${asset.getAssetID()}">View</a>
                              </td>
                              <td align="center">
                                  <!--<a href="assets_status/${asset.getAssetID()}">Edit Status</a>-->
                                  <a href="#" data-assetID="${asset.getAssetID()}" onclick="showStatusModal(this)" >Edit Status</a>
                              </td>
                              
                              
                            </tr>
                        </c:forEach>  
                            
                    </tbody>
                
            </div>
            
            <div id="AssetStatusModal" class="modal fade" role="dialog">
              <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="bold modal-title" align="left">Equipment Status: Tag# <span id="equipmentTagID"></span></h4>
                  </div>
                  <div class="modal-body">
                    <form action="editstatus" method="POST">
                        <div class="col-md-6">
                                <fieldset>
                                    <p>${message}</p>
                                    <span class="bold">Member Id:</span>
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
                                <input id="assetID" type="hidden" name="assetID">                    
                        </div>
                        <div class="col-md-6">
                            <span class="bold">Extra Notes:</span>
                                    <br/>
                                    <textarea name="extraNotes" class="form-control" 
                                              style="margin-top: 5px" rows="4" cols="45"></textarea>
                                    <input type="submit" value="Submit" class="btn"
                                           style="display: block; width: 25%; margin-top: 15px;"/>
                        </div>    
                    </form>
                        <div style="clear:both;"></div>
                  </div>
                  <div class="modal-footer">
                    <!--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>-->
                  </div>
                </div>

              </div>
            </div>
            
            <script type="text/javascript"> 
                function showStatusModal(rowLink){
                    $('#AssetStatusModal').modal('show');
                    var assetID = $(rowLink).attr("data-assetID")
                    $("#assetID").val(assetID)
                    $("#equipmentTagID").text(assetID)
                }
            </script>
            <script src="js/jquery-1.12.2.min.js"></script>
            <script src="js/bootstrap.min.js"></script>
        </body>
</html>

