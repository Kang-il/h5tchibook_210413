<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="group-profile-box">
	<div class="group-profile-background">
			
		<c:choose>
			<c:when test="${group.groupCoverImagePath eq null}">
				<img src="/static/images/no_background_image.jpg" class="group-background"/>
				<input type="hidden" class="group-cover-image-path" value="${group.groupCoverImagePath}"/>					
			</c:when>
			<c:otherwise>
				<img src="${group.groupCoverImagePath}" class="group-background"/>
				<input type="hidden" class="group-cover-image-path" value="${group.groupCoverImagePath}"/>	
		</c:otherwise>
		</c:choose>
		<c:if test="${groupOwnerCheck eq true }">
			<button class="background-edit-btn" data-group-id="${group.id}"><span class="material-icons">photo_camera</span> 커버사진 편집</button>		
			<input type="file" class="d-none" id="editGroupCoverImage"/>		
		</c:if>
	</div>
	<div class="group-introduce-box">
		<h3 class="group-id">${group.groupName}</h3>
		<span class="group-member-count">그룹 멤버 ${groupMemberCount} 명</span>
		<hr>
	</div>
	<nav class="profile-nav-box">
		<div class="profile-nav-item">
			<c:if test="${userView eq 'group/group_feed_section'}">
				<a href="/feed/group/${group.groupName}" class="item-link-in-use">게시물 </a>
				<a href="/feed/group/${group.groupName}?category=member" class="item-link">멤버</a>
				<a href="/feed/group/${group.groupName}?category=photo" class="item-link">사진</a>
			</c:if>
			<c:if test="${userView eq 'group/group_feed_member_section'}">
				<a href="/feed/group/${group.groupName}" class="item-link">게시물 </a>
				<a href="/feed/group/${group.groupName}?category=member" class="item-link-in-use">멤버</a>
				<a href="/feed/group/${group.groupName}?category=photo" class="item-link">사진</a>
			</c:if>
			<c:if test="${userView eq 'group/group_feed_photo_section'}">
				<a href="/feed/group/${group.groupName}" class="item-link">게시물 </a>
				<a href="/feed/group/${group.groupName}?category=member" class="item-link">멤버</a>
				<a href="/feed/group/${group.groupName}?category=photo" class="item-link-in-use">사진</a>
			</c:if>
		</div>
		<c:choose>
			<c:when test="${user.id eq group.groupManagerId}">
				<button type="button" class="edit-group-btn" data-group-name="${group.groupName}"><span class="material-icons">edit</span> &nbsp; 그룹 관리</button>
			</c:when>
					 
			<c:when test="${groupJoinRequest eq null && groupMemberCheck eq null }">
				<button type="button" class="join-group-btn" data-group-id="${group.id}"><span class="material-icons">group_add</span> &nbsp; 그룹 가입</button>
			</c:when>
					
			<c:when test="${groupJoinRequest ne null }">
				<button type="button" class="progress-join-group-btn" data-group-id="${group.id}"><span class="material-icons">group_add</span> &nbsp; 가입 승인 대기중</button>
			</c:when>
			
			<c:when test="${groupMemberCheck ne null}">
				<button type="button" class="edit-group-join-btn" data-group-id="${group.id}" data-group-member-id="${user.id}"><span class="material-icons">groups</span> &nbsp; 그룹 회원</button>
			</c:when> 
		</c:choose> 
		
		

	</nav>
</div> 