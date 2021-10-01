<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>H5tchibook</title>
<link rel="stylesheet"href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>        
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">

<link rel="stylesheet" href="/static/css/style.css?${currentTime}" type="text/css"/>
<script src="/static/javascript/h5tchibook.js?${currentTime}" type="text/javascript"></script>
</head>


<body>
	<jsp:include page="../include/group_like_list_modal.jsp"/>
	<jsp:include page="../include/like_list_modal.jsp"/>
	<jsp:include page="../include/secession_group_modal.jsp"/>
	<jsp:include page="../include/delete_group_modal.jsp"/>
	<jsp:include page="../include/delete_group_post_modal.jsp"/>
	<jsp:include page="../include/delete_user_post_modal.jsp"/>
	<jsp:include page="../include/edit_group_cover_image_modal.jsp"/>
	<jsp:include page="../include/delete_group_member_modal.jsp"/>
	<jsp:include page="../include/friend_modal.jsp"/>
	<jsp:include page="../include/delete_group_comment_modal.jsp"/>
	<jsp:include page="../include/delete_comment_modal.jsp"/>
	<jsp:include page="../include/create_group_post_modal.jsp"/> 
	<jsp:include page="../include/create_post_modal.jsp"/>
	<jsp:include page="../include/group_member_modal.jsp"/>
	<jsp:include page="../include/gnb.jsp"/>
	<jsp:include page="../${userView}.jsp"/>
</body>
</html>