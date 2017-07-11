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
                <div class="col-md-3">
                    <h3 align="left">Kobold Camp Assets</h3>
                    <span>Asset Tag:</span><span>${asset.getAssetID()}</span><br/>
                    <span>Category:</span><span>${asset.getCategoryName()}</span><br/>
                    <span>Description:</span><span>${asset.getDescription()}</span><br/>
                    <span>Current Status:</span><span></span><br/>
                </div>
                <table class="table table-hover"
                       style="float:left; margin-top: 25px"/>
                    <legend>
                        Rental History
                    </legend>
                    <thead>
                      <tr>
                        <th>Date</th>
                        <th>Employee</th>
                        <th>Status</th>
                        <th>Member Name</th>
                        <th>Notes</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${recordsList}" var="record">
                            <tr>
                              <td>${record.getRecordDateString()}</td>
                              <td>${record.getEmployeeName()}</td>
                              <td>${record.getStatus()}</td>
                              <td>${record.getMemberName()}</td>
                              <td>${record.getNote()}</td>
                            </tr>
                        </c:forEach>  
                    </tbody>  
            </table>
            </div>
		<script src="js/jquery-1.12.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
        </body>
</html>

