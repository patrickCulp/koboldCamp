<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Kobold Camp Asset Management</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        
        <link rel="shortcut icon" 
              href="${pageContext.request.contextPath}/img/koboldCampIcon.png">

    </head>
	<body>
            <div class="container">
                <%@include file="navIncludes.jsp" %>   
                <h3>
                    Kobold Camp Rental Equipment Available
                </h3>
                <form  action="searchRentals" method="POST" >
                    <fieldset>
                        <legend>
                            Search
                        </legend>
                        <input class="form-control" 
                               style="display:inline-block; width: 20%" 
                               type="text" name="assetTag" id="assetTag" 
                               placeholder="Asset Tag">
                        <select class="form-control" 
                                style="display:inline-block; width: 20%" 
                                name="category" id="category">
                            <option value = "">-Category-</option>
                            <c:forEach items="${categories}" var="category"> 
                                    <option value="${category.getCategoryName()}">${category.getCategoryName()}</option> 
                            </c:forEach> 
                        <input type="submit" value="Search" class="btn"/>
                    </fieldset>
                </form>
                <br/>                
                <table class="table table-hover">
                    <thead>
                      <tr>
                        <th>Asset Tag</th>
                        <th>Category</th>
                        <th>Brand</th>
                        <th>Description</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${assetList}" var="asset">
                            <tr>
                              <td>${asset.getAssetID()}</td>
                              <td>${asset.getCategoryName()}</td>
                              <td>${asset.getBrand()}</td>
                              <td>${asset.getDescription()}</td>
                            </tr>
                        </c:forEach>  
                            
                    </tbody>
                </table>
            </div>
		<script src="js/jquery-1.12.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
        </body>
</html>

