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

    </head>
    <body>
            <div class="container">                
                <%@include file="navIncludes.jsp" %>
                <h3 align="left">Kobold Camp Admin</h3>
                <div class="col-md-6">                    
                    <form action="searchMembersAdmin" method="POST">
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
                    
                    <table class="table table-hover"
                       style="float:left; margin-top: 25px"/>
                        <thead>
                          <tr>
                            <th>Member Id</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th></th>
                            <th></th>
                          </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${memberList}" var="member">
                                <tr>
                                  <td>${member.getUserID()}</td>
                                  <td>${member.getFullName()}</td>
                                  <td>${member.getAuthority()}</td>
                                  <td>
                                         <a href=
                                            "/KoboldCamp/deleteMember/${member.getUserID()}"
                                            >Delete</a>
                                  </td>  
                                  <td>
                                      <a href="/KoboldCamp/resetpw/${member.getUserID()}"
                                         >Reset Password</a>
                                  </td>  
                                  </td>  
                                </tr>
                            </c:forEach>  
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <form method="POST" action="/KoboldCamp/addEquipment">
                        <fieldset>
                            <h3 align="left">Add Equipment:</h3>
                            </br>
                            <select name="category" class="form-control" style="float:right; display: inline-block; width: 45%; 
                                   margin-right: 200px; margin-bottom: 0px" id="category">
                                <option value = "">-Category-</option>
                                <c:forEach items="${categories}" var="category"> 
                                    <option value="${category.getCategoryName()}">${category.getCategoryName()}</option> 
                                </c:forEach> 
                            </select>Category:
                            </br></br>
                            <input class="form-control" 
                                   style="float:right; display: block; width: 45%; 
                                   margin-right: 200px; margin-bottom: 0px" 
                                   type="text" name="brand" id="brand" 
                                   placeholder="  brand">Brand:
                            </br></br>
                            Description:
                            <br/>
                            <textarea name="description" class="form-control" style="margin-top: 5px" rows="4" cols="45"></textarea>

                            <input type="submit" value="Add Equipment" class="btn"
                                   style="float:left; display: block; width: 25%; margin-top: 15px; margin-left: 105px"/>                        
                        </fieldset>
                    </form>
                </form>
                </div>
                
                
            </div>
		<script src="js/jquery-1.12.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
        </body>
	
</html>

