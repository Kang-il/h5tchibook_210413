<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="create-group-post-modal-section d-none">
	<div class="create-group-post-modal-box">
		<div class="modal-title-box">
			<h4>그룹 게시물 만들기</h4>
			<button type="button" class="material-icons-outlined group-post-modal-close-btn">close</button>
		</div>
		
		<hr>
		
		<div class="create-group-post-profile-box">
			<c:if test="${user.profileImagePath eq null}">
				<img src="/static/images/no_profile_image.png" alt="프로필" />			
			</c:if>
			<c:if test="${user.profileImagePath ne null}">
				<img src="${user.profileImagePath}" alt="프로필" />			
			</c:if>
			<div class="create-group-post-select-box">
				<span>${user.loginId}</span>
			</div>
		</div>
			
		<div class="create-group-post-content-box">
			<div class="create-group-post-image-box d-none">
				<button class="material-icons-outlined delete-group-img-btn d-none">close</button>
				<img src="/static/images/dummy_profile.jpg" class="create-group-post-img d-none" alt="포스트"/>
			</div>
			<textarea class="form-control create-group-text-form" placeholder="${user.loginId}님, 무슨 생각을 하고 계신가요?" rows="6"></textarea>
		</div>
			
		<div class="add-group-photo-box"> 
			<div>게시물에 추가</div>

			<label for="loadGroupImageInput" class="load-group-post-photo-btn">
				<span class="material-icons">photo_library</span>사진
			</label>
				
			<input type="file" id="loadGroupImageInput" class="create-file-input d-none"/>
		</div>
			
		<div>
			<button type="button" class="btn create-group-post-btn" disabled="disabled" data-group-id="${group.id}">게시</button>
		</div>
		
	</div>
</div>