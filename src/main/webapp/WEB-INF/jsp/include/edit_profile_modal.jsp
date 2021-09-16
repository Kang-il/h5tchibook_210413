<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="edit-profile-pic-modal-section d-none">
    <div class="edit-profile-pic-modal-box">
    	<div class="title-box">
		    <div class="modal-title">프로필 변경</div>
		    <div class="material-icons-outlined profile-modal-close-btn">close</div>
	    </div>
	    <hr class="m-0"> 
	    <div class="edit-profile-item-section">
		    <div class="edit-profile-item">
			    <c:if test="${user.profileImagePath eq null}">
			  	  <img src="/static/images/no_profile_image.png"  class="edit-profile-img"/>
			  	  <input type="hidden" class="profile-img-value-box" value="/static/images/no_profile_image.png"/>
 			    </c:if>
			     <c:if test="${user.profileImagePath ne null}"> 
			  	  <img src="${user.profileImagePath}" class="edit-profile-img"/>
			  	   <input type="hidden" class="profile-img-value-box" value="${user.profileImagePath}"/>
			    </c:if>
		    </div>
	    </div>
	    <div class="edit-btn-box">
	    	<button type="button" class="change-basic-img">기본 이미지로 변경하기</button>
	    	
	    </div>
	    <div class="edit-btn-box"> 
	    	<button type="button" class="addapt-profile-img btn" disabled="disabled" data-user-login-id="${user.loginId}">적용하기</button>
	    </div>
	    	<input type="file" class="edit-profile-img-input d-none"/>
    </div>
</div>

<div class="edit-profile-pic-modal-section">
	<div class="edit-profile-pic-modal-box">
		<div class="title-box">
		    <div class="modal-title">소개글 수정</div>
		    <div class="material-icons-outlined profile-modal-close-btn">close</div>
	    </div>
		    <hr class="m-0">
		    <div>
		    	<textarea class="introduce-text-area"></textarea>
		    </div>
	</div>
</div>

<div class="edit-profile-background-section d-none">
	<div class="basic-background-img-btn-box">
		<button type="button" class="basic-background-img-btn btn">기본이미지로 변경</button>
	</div>
	<div class="change-background-img-btn-box">
		<button type="button" class="change-background-btn btn">이미지 변경</button>
		<button type="button" class="change-background-action-btn btn" disabled="disabled">이미지 적용</button>
		<span class="material-icons-outlined change-background-close-btn">close</span>
	</div>
</div>
