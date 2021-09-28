<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="group-timeline-info-section">
		<div class="group-timeline-user-profile" data-user-login-id="${user.loginId}">
			<c:choose>
				<c:when test="${user.profileImagePath eq null}">
					<img src="/static/images/no_profile_image.png" alt="프로필" class="group-timeline-info-profile-img"/>
				</c:when>
				<c:otherwise>
					<img src="${user.profileImagePath}" alt="프로필" class="group-timeline-info-profile-img"/>
				</c:otherwise>
			</c:choose>
			 
			<span>${user.loginId}</span>
		</div>
		
		<div class="group-timeline-info-menu" id="groupSectionMyTimeLineBtn">
			<span class="material-icons-outlined">burst_mode</span>
			<span>내 타임라인</span>
		</div>
		
		<div class="create-new-group">
			<span> +&nbsp;</span><span> 새 그룹 만들기</span> 
		</div>
		
		<div class="edit-my-group">
			<span class="material-icons edit-icon">settings</span><span> 내 그룹 관리</span> 
		</div>
		
		<hr>
		
		<h5 class="my-shortcuts-title">가입한 그룹</h5>
		<div class="group-timeline-group-box"> 
			<c:forEach var="group" items="${groupList}">
				<div class="group-timeline-group-item" data-group-name="${group.groupName}">
					<c:if test="${group.groupProfileImagePath eq null }">
						<img src="/static/images/no_profile_image.png" alt="그룹 이미지" class="group-timeline-group-profile-img"/>
					</c:if>
					<c:if test="${group.groupProfileImagePath ne null}">
						<img src="${group.groupProfileImagePath}" alt="그룹 이미지" class="group-timeline-group-profile-img"/>
					</c:if>
					<span>${group.groupName}</span>
				</div>
			</c:forEach>
		</div>
		<footer>
			<jsp:include page="../include/footer.jsp"/>
		</footer>
	</section>