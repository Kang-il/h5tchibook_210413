<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="timeline-section">
	<section class="timeline-info-section">
		<div class="timeline-user-profile" data-user-login-id="${user.loginId}">
			<c:if test="${user.profileImagePath eq null}">
				<img src="/static/images/no_profile_image.png" alt="프로필" class="timeline-info-profile-img"/>
			</c:if>
			<c:if test="${user.profileImagePath ne null}">
				<img src="${user.profileImagePath}" alt="프로필" class="timeline-info-profile-img"/>
			</c:if>
			<span>${user.loginId}</span>
		</div>
		
		<div class="timeline-info-menu" id="friendViewBtn" data-user-login-id="${user.loginId}">
			<span class="material-icons-outlined">group</span>
			<span>친구</span>
		</div>
		
		<div class="timeline-info-menu" id="groupTimeLineBtn">
			<span class="material-icons-outlined">groups</span>
			<span>그룹</span>
		</div>
		
		<hr>
		
		<h5 class="my-shortcuts-title">내 바로가기</h5>
		<div class="timeline-group-box"> 
			<c:forEach var="group" items="${groupList}">
				<div class="timeline-group-item" data-group-name="${group.groupName}">
					<c:if test="${group.groupProfileImagePath eq null}">
						<img src="/static/images/no_profile_image.png" alt="그룹 이미지" class="timeline-group-profile-img"/>
					</c:if>
					
					<c:if test="${group.groupProfileImagePath ne null}">
						<img src="${group.groupProfileImagePath}" alt="그룹 이미지" class="timeline-group-profile-img"/>
					</c:if>
					<span>${group.groupName}</span>
				</div>
			</c:forEach>
		</div>
		<footer>
			<jsp:include page="../include/footer.jsp"/>
		</footer>
	</section>
	
	<section class="timeline-post-section">
		<jsp:include page="../post/timeline_post_section.jsp"/>
	</section>
</section>