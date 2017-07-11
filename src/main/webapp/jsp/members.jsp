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
                <h3 align="left">Kobold Camp Members</h3>
                <div class="col-md-6">
                    <div class="row">
                    <form action="searchMembers" method="POST" >
                        <fieldset>
                            <legend>
                                Search
                            </legend>
                            <input class="form-control" 
                                   style="float:left; display:inline-block; width: 25%; margin-left: 20px; margin-top: 5px" 
                                   type="text" name="memberID" id="memberID" placeholder="  Member Id">
                            <input class="form-control" 
                                   style="float:left; display:inline-block; width: 25%; margin-left: 20px; margin-top: 5px" 
                                   type="text" name="memberName" 
                                   id="memberName" placeholder="  Name">
                            <input type="submit" value="Search" class="btn"
                                   style="float:left; display:inline-block; width:15%; margin-left: 40px"/>
                        </fieldset>
                    </form>
                    </div>
                    <div class="row">
                        <table class="table table-hover"
                                    style="float:left; margin-top: 25px"/>
                                 
                                 <thead>
                                   <tr>
                                     <th>Member ID</th>
                                     <th>Name</th>
                                     <th>Email</th>
                                     <th>Phone Number</th>
                                   </tr>
                                 </thead>
                                 <tbody>
                                     <c:forEach items="${memberList}" var="member">
                                         <tr>
                                           <td>${member.getUserID()}</td>
                                           <td>${member.getFullName()}</td>
                                           <td>${member.getEmail()}</td>
                                           <td>${member.getPhone()}</td>
                                         </tr>
                                     </c:forEach>  
                    </tbody>
                         </table>
                    </div>
                </div>
                    <div class="col-md-6" style="border:1px solid gray;">
                        <form action="members" method="POST">
                            <fieldset>
                                <h3 align="left">Add Member:</h3>
                                <p>${message}</p>
                                <div class="col-md-6">
                                    </br>
                                    <span>UserName:</span><input class="form-control" 
                                           style="float:right; display: inline-block; width: 65%; 
                                           margin-bottom: 0px" 
                                           type="text" name="username" id="username" 
                                           placeholder="  Username">
                                    </br></br>
                                    <span>First Name:</span><input class="form-control" 
                                           style="float:right; display: block; width: 65%; 
                                           margin-bottom: 0px" 
                                           type="text" name="firstName" id="firstName" 
                                           placeholder="  Name">
                                    </br></br>
                                    <span>Last Name:</span><input class="form-control" 
                                           style="float:right; display: block; width: 65%; 
                                            margin-bottom: 0px" 
                                           type="text" name="lastName" id="lastName" 
                                           placeholder="  Name">
                                    </br></br>
                                    <span>Email:</span><input class="form-control" 
                                           style="float:right; display: block; width: 65%; 
                                           margin-bottom: 0px"  
                                           type="text" name="email" id="email" 
                                           placeholder="  Email">
                                    </br></br>
                                    <span>Phone:</span><input class="form-control" 
                                           style="float:right; display: block; width: 65%; 
                                           margin-bottom: 0px" 
                                           type="text" name="phone" id="phone" 
                                           placeholder="  Phone #">
                                    </br></br>
                                    <input type="submit" value="Add Member" class="btn"
                                           style="float:left; display: block; width: 50%; margin-top: 15px; margin-left: 100px"/> 
                                </div>
                                <div class="col-md-6">
                                    </br>
                                    <input style="float:left; margin-left:100px; margin-right:10px;" type="checkbox" name="employee"> Employee?
                                    <br></br>
                                    <input style="float:left; margin-left:100px;margin-right:10px;" type="checkbox" name="admin"> Admin?<br>
                                </div>
                            </fieldset>
                        </form>
                    </div>
            </div>
		<script src="js/jquery-1.12.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
        </body>
</html>

