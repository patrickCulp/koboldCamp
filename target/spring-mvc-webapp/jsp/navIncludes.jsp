<%-- 
    Document   : navIncludes
    Created on : Nov 1, 2016, 6:54:58 PM
    Author     : pculp
--%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default" role="navigation" align="center">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#" text="bold">Equipment Rental</a>
        </div>
        <div style="float:right">
            <c:if test="${not pageContext.request.userPrincipal.authenticated}">
            <form method="post" action="j_spring_security_check">
                <button style="float:right; display:inline-block; width: 15%; margin-left: 5px; margin-top: 10px" 
                        type="submit" id="edit-button" tabindex="3" class="btn btn-default">Login</button> 
                <input style="float:right; display:inline-block; width: 30%; margin-left: 5px; margin-top: 10px" 
                       type="password" tabindex="2" class="form-control" id="password" name="j_password" placeholder="*******" />
                <input style="float:right; display:inline-block; width: 30%; margin-top: 10px" 
                       type="text" tabindex="1" class="form-control" id="username" name="j_username" placeholder="Username" />
            </form>
            </c:if>
        </div>
        <c:if test="${pageContext.request.userPrincipal.authenticated}">
            <div style="float:right">
                <span id="welcomeText"></span>
                <a href="j_spring_security_logout" style="float:right; display:inline-block; margin-left: 5px; margin-top: 10px" 
                        type="submit" id="edit-button" class="btn btn-default">Log Out</a> 
                <span style="float:right; margin-top: 17px; margin-right: 15px;">Welcome,  ${pageContext.request.userPrincipal.name}</span>
                
                
            </div>
        </c:if>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"></sec:authorize>
                <sec:authorize access="hasRole('ROLE_EMPLOYEE')" var="isEmployee"></sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')" var="isUser"></sec:authorize>
                <c:choose>
                    <c:when test="${pageName == 'home'}">
                        <li class="active"><a href="/KoboldCamp/home">Home</a></li>
                        <li><a href="/KoboldCamp/rentals">Rentals</a></li>                                             
                            <c:if test="${isEmployee}">
                                <li><a href="/KoboldCamp/assets">Assets</a></li>
                                <li><a href="/KoboldCamp/members">Members</a></li>
                            </c:if>
                            <c:if test="${isAdmin}">
                                <li><a href="/KoboldCamp/admin">Admin</a></li>
                            </c:if>
                            <c:if test="${isUser}">
                                <li><a href="/KoboldCamp/profile">Profile</a></li>
                            </c:if>
                    </c:when>
                    <c:when test="${pageName == 'rentals'}">
                        <li><a href="/KoboldCamp/home">Home</a></li>
                        <li class="active"><a href="/KoboldCamp/rentals">Rentals</a></li>                                             
                            <c:if test="${isEmployee}">
                                <li><a href="/KoboldCamp/assets">Assets</a></li>
                                <li><a href="/KoboldCamp/members">Members</a></li>
                            </c:if>
                            <c:if test="${isAdmin}">
                                <li><a href="/KoboldCamp/admin">Admin</a></li>
                            </c:if>
                            <c:if test="${isUser}">
                                <li><a href="/KoboldCamp/profile">Profile</a></li>
                            </c:if>
                    </c:when>
                    <c:when test="${pageName == 'assets'}">
                        <li><a href="/KoboldCamp/home">Home</a></li>
                        <li><a href="/KoboldCamp/rentals">Rentals</a></li>                                             
                            <c:if test="${isEmployee}">
                                <li class="active"><a href="/KoboldCamp/assets">Assets</a></li>
                                <li><a href="/KoboldCamp/members">Members</a></li>
                            </c:if>
                            <c:if test="${isAdmin}">
                                <li><a href="/KoboldCamp/admin">Admin</a></li>
                            </c:if>
                            <c:if test="${isUser}">
                                <li><a href="/KoboldCamp/profile">Profile</a></li>
                            </c:if>
                    </c:when>
                    <c:when test="${pageName == 'home'}">
                        <li class="active"><a href="/KoboldCamp/home">Home</a></li>
                        <li><a href="/KoboldCamp/rentals">Rentals</a></li>
                        <li><a href="/KoboldCamp/assets">Assets</a></li>
                        <li><a href="/KoboldCamp/members">Members</a></li>
                        <li><a href="/KoboldCamp/admin">Admin</a></li>
                        <li><a href="/KoboldCamp/profile">Profile</a></li>
                    </c:when>    
                    <c:when test="${pageName == 'members'}">
                        <li><a href="/KoboldCamp/home">Home</a></li>
                        <li><a href="/KoboldCamp/rentals">Rentals</a></li>                                             
                            <c:if test="${isEmployee}">
                                <li><a href="/KoboldCamp/assets">Assets</a></li>
                                <li class="active"><a href="/KoboldCamp/members">Members</a></li>
                            </c:if>
                            <c:if test="${isAdmin}">
                                <li><a href="/KoboldCamp/admin">Admin</a></li>
                            </c:if>
                            <c:if test="${isUser}">
                                <li><a href="/KoboldCamp/profile">Profile</a></li>
                            </c:if>
                    </c:when>
                    <c:when test="${pageName == 'admin'}">
                        <li><a href="/KoboldCamp/home">Home</a></li>
                        <li><a href="/KoboldCamp/rentals">Rentals</a></li>
                        <li><a href="/KoboldCamp/assets">Assets</a></li>
                        <li><a href="/KoboldCamp/members">Members</a></li>
                        <li class="active"><a href="/KoboldCamp/admin">Admin</a></li>
                        <li><a href="/KoboldCamp/profile">Profile</a></li>
                    </c:when>
                    <c:when test="${pageName == 'profile'}">
                        <li><a href="/KoboldCamp/home">Home</a></li>
                        <li><a href="/KoboldCamp/rentals">Rentals</a></li>                                             
                            <c:if test="${isEmployee}">
                                <li><a href="/KoboldCamp/assets">Assets</a></li>
                                <li><a href="/KoboldCamp/members">Members</a></li>
                            </c:if>
                            <c:if test="${isAdmin}">
                                <li><a href="/KoboldCamp/admin">Admin</a></li>
                            </c:if>
                            <c:if test="${isUser}">
                                <li class="active"><a href="/KoboldCamp/profile">Profile</a></li>
                            </c:if>
                    </c:when>
                    <c:otherwise>
                       <li><a href="/KoboldCamp/home">Home</a></li>
                       <li><a href="/KoboldCamp/rentals">Rentals</a></li>
                       <li><a href="/KoboldCamp/assets">Assets</a></li>
                       <li><a href="/KoboldCamp/members">Members</a></li>
                       <li><a href="/KoboldCamp/admin">Admin</a></li>
                       <li><a href="/KoboldCamp/profile">Profile</a></li>
                     </c:otherwise>
                </c:choose>
                
            </ul>  
        </div><!-- /.navbar-collapse -->

    </div><!-- /.container-fluid -->
</nav>
        