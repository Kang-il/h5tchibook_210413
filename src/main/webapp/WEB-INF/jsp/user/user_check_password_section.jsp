<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    <section class="user-check-password-section">
    	<div class="user-check-password-box">
    		<div class="box-title">개인정보 수정</div>
    		<hr class="m-0">
    		<div class="user-profile-img-box">
	    		<div class="user-profile-img-item">
	    			<c:if test="${user.profileImagePath eq null}">
		    			<img src="/static/images/no_profile_image.png"/>
	    			</c:if>
	    			<c:if test="${user.profileImagePath ne null}">
		    			<img src="${user.profileImagePath}"/>
	    			</c:if>
	    		</div>
    		</div>
    		<h4 class="mt-3 text-center">${user.loginId}</h4>
    		<div class="password-input-box">
    			<input type="password" class="form-control" id="editProfilePassword" placeholder="패스워드를 입력해 주세요."/>
    			<div class="password-alert-box">
    				<span class="text-danger wrong-password-alert d-none">비밀번호가 틀렸습니다.</span>
    				<span class="text-danger blank-password-alert d-none">빈칸을 채워주세요</span>
    			</div>
    			<button type="button" class="btn profile-password-submit-btn" disabled="disabled" data-user-login-id="${user.loginId}">제출</button>
    		</div>
    	</div>
    </section>   