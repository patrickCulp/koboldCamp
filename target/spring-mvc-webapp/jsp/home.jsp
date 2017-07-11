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

        <!-- SWC Icon -->
        <link rel="shortcut icon" 
              href="${pageContext.request.contextPath}/img/koboldShieldIcon.png">

    </head>
	<body>
            <div class="container">
                <%@include file="navIncludes.jsp" %>   
                <h3 align="left">Welcome to Kobold Camp Equipment 
                    Rental Management System</h3>
                
                <!-- Roles display -->
                
                <p align="left">Street art jianbing leggings, forage tumeric 
                    lumbersexual prism flexitarian. Sustainable deep v poke 
                    distillery, whatever fanny pack drinking vinegar. 
                    Sustainable lumbersexual chicharrones try-hard, jean shorts 
                    shabby chic pork belly. Narwhal bitters messenger bag banjo, 
                    hot chicken stumptown 8-bit irony selfies taxidermy 
                    church-key. Paleo lyft shoreditch polaroid, kinfolk four 
                    loko hexagon. Vape wayfarers thundercats aesthetic truffaut 
                    hot chicken. Man braid yuccie literally, cray tilde 
                    stumptown selfies blog.</p>
            </div>
		<script src="js/jquery-1.12.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
        </body>
</html>

