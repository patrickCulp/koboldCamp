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
                <div class="col-md-6">
                <form  action="editProfile" method="POST">
                    <fieldset>
                        <h3 align="left">Edit Information:</h3>
                        </br>                       
                        <input class="form-control" 
                               style="float:right; display: block; width: 45%; 
                               margin-right: 200px; margin-bottom: 0px" 
                               type="text" name="firstName" id="firstName" 
                               value="${defaultUser.getFirstName()}">First Name:
                        </br></br>
                        <input class="form-control" 
                               style="float:right; display: block; width: 45%; 
                               margin-right: 200px; margin-bottom: 0px" 
                               type="text" name="lastName" id="lastName" 
                               value="${defaultUser.getLastName()}">Last Name:
                        </br></br>
                        <input class="form-control" 
                               style="float:right; display: block; width: 45%; 
                               margin-right: 200px; margin-bottom: 0px"  
                               type="text" name="email" id="email" 
                               value="${defaultUser.getEmail()}">Email:
                        </br></br>
                        <input class="form-control" 
                               style="float:right; display: block; width: 45%; 
                               margin-right: 200px; margin-bottom: 0px" 
                               type="text" name="phone" id="phone" 
                               value="${defaultUser.getPhone()}">Phone:
                        </br></br>                        
                        <input type="submit" value="Update Information" class="btn"
                               style="float:left; display: block; width: 30%; margin-top: 15px; margin-left: 100px"/>                        
                    </fieldset>
                </form>
                <form  action="newPassword" method="POST">
                    <fieldset>
                            <h3 align="left">Change Password:</h3>
                            </br>                       
                            <input class="form-control" 
                                   style="float:right; display: block; width: 45%; margin-right: 170px; margin-bottom: 0px" 
                                   type="password" name="password" id="newPassword" 
                                   placeholder="*****">New Password:
                            </br></br>
                            <input class="form-control" 
                                   style="float:right; display: block; width: 45%; margin-right: 170px; margin-bottom: 0px" 
                                   type="password" name="matchPassword" id="matchPassword" 
                                   placeholder="*****">Repeat Password:
                            </br></br>   
                            <input type="button" value="Update Password" class="btn" onclick="showUpdatePasswordConfirmationModal()"
                                   style="float:left; display: block; width: 30%; margin-top: 15px; margin-left: 100px"/>                        
                    </fieldset>
                </form>
                </div>
                <div class="col-md-6">
                <table class="table table-hover"
                       style="float:left; margin-top: 25px"/>
                    <thead>
                      <tr>
                        <th>Date</th>
                        <th>Employee</th>
                        <th>Status</th>
                        <th>Tag #</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${history}" var="record">
                            <tr>
                              <td>${record.getRecordDateString()}</td>
                              <td>${record.getEmployeeName()}</td>
                              <td>${record.getStatus()}</td>
                              <td>${record.getAssetID()}</td>  
                            </tr>
                        </c:forEach>  
                    </tbody>
                </div>
            </div>
                        
            <div id="PasswordPromptModal" class="modal fade" role="dialog">
              <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Are you sure you want to change your password?</h4>
                  </div>
                  <div class="modal-body"></div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="executeUpdatePassword()">Yes</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                  </div>
                </div>
              </div>
            </div>
            
            <script type="text/javascript"> 
                function showUpdatePasswordConfirmationModal(){
                    
                    var password = $('#newPassword').val();
                    var matchPassword = $('#matchPassword').val();
                    
                    console.log(password + " | " + matchPassword);
                    
                    if(password === matchPassword){
                        $('#PasswordPromptModal').modal('show');  
                    }else{
                        alert("Passwords do not match");
                    }
                            
                }
                
                function executeUpdatePassword(){
                    // do ajax web service call here
                    var password = $('#newPassword').val();
                    var matchPassword = $('#matchPassword').val();
                    
                     $.ajax({
                        url: 'newPassword',
                        type: 'POST',
                        data: {password:password, matchPassword:matchPassword}
                    }).success(function(incomingCompliment){
                        alert("Your password has been updated");
                    }).error(function(err){console.log(err)})
                     
                }
            </script>
                        
            <script src="js/jquery-1.12.2.min.js"></script>
            <script src="js/bootstrap.min.js"></script>
        </body>
</html>

