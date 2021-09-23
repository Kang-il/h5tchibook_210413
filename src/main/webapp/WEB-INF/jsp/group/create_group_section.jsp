<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<section class="timeline-section">
	<section class="create-group-input-section">
		<h4>그룹 만들기</h4>
		
		<div class="group-master-box">
			<c:if test="${user.profileImagePath eq null}">
				<img src="/static/images/no_profile_image.png"/> 
			</c:if>
			<c:if test="${user.profileImagePath ne null}">
				<img src="${user.profileImagePath}"/>
			</c:if>
			<div>
				<span class="user-id">${user.loginId}</span>
				<span class="user-role">관리자</span>
			</div>
		</div>
		<input type="text" class="group-name-input form-control" placeholder="그룹 이름"/>
		<div class="text-danger d-none duplicate-group-name-alert">그룹이름이 중복됩니다.</div>
		<button type="button" class="btn create-group-btn" disabled="disabled">그룹 만들기</button>
	</section>
</section>