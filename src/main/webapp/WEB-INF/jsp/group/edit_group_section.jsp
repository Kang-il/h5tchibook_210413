<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<section>
	<jsp:include page="../include/user_group_info_section.jsp"/>
	
	<section class="edit-group-section">
		<div class="edit-group-box">
			<div class="group-title-box">
				<div class="material-icons-outlined backward-btn">arrow_back_ios</div>
				<div class="group-title-item">
					<h5 class="text-center group-title">${group.groupName}</h5> 
				</div>			 
			</div>
			
			<hr>
			
			<div class="edit-group-profile-img-box">
				<div class="group-img-item">
					<c:choose>
						<c:when test="${group.groupProfileImagePath eq null }">
							<img src="/static/images/no_profile_image.png"/>
						</c:when>
						<c:otherwise>
							<img src="${group.groupProfileImagePath}"/>
						</c:otherwise> 
					</c:choose>
					<button class="material-icons group-profile-img-edit-btn">photo_camera</button>
					<div class="edit-group-profile-menu-btn d-none">
						<div class="change-group-profile-btn" data-group-id="${group.id}">이미지 변경</div>
						<div class="basic-group-profile-btn" data-group-id="${group.id}">기본 이미지 변경</div>
					</div>
					<input type="file" id="editGroupProfileInput" class="d-none"/>
				</div>
			</div>
			<hr>
			<p class="edit-group-title">가입 신청 목록</p>
			<hr class="mt-2">
			
			<c:if test="${fn:length(joinRequestList) eq 0}">
				<div class="no-group-join-request-box">
					<div>받은 요청이 없습니다.</div>			
				</div>
			</c:if>
			
			<c:if test="${fn:length(joinRequestList) ne 0}">
				<div class="group-join-request-box">
					<c:forEach var="request" items="${joinRequestList}">
						<div class="group-join-request-item"> 
							<div class="request-img-box">
								<c:choose>
									<c:when test="${request.userProfileImagePath eq null}">
										<img src="/static/images/no_profile_image.png"/>
									</c:when>
									<c:otherwise>
										<img src="${request.userProfileImagePath}"/>
									</c:otherwise>
								</c:choose>
							<a href="/feed/${request.userLoginId}">${request.userLoginId}</a>
							</div>
							<div class="request-btn-box">
								<button type="button" class="btn group-join-accept-btn" data-group-id="${group.id}" data-user-id="${request.userId}">수락</button>
								<button type="button" class="btn group-join-refuse-btn" data-group-id="${group.id}" data-user-id="${request.userId}">거절</button>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
			
			<hr>
			<p class="edit-group-title">그룹 멤버 목록</p>
			<hr class="mt-2">
			
			<div class="group-join-member-list">
				<div class="group-join-member-list-box">
					<c:forEach var="member" items="${groupMemberList}">
						<div class="group-member-item"> 
							<div class="member-img-box">
								<c:choose>
									<c:when test="${member.groupMemberProfileImagePath eq null}">
										<img src="/static/images/no_profile_image.png"/>
									</c:when>
									<c:otherwise>
										<img src="${member.groupMemberProfileImagePath}"/>
									</c:otherwise>
								</c:choose>
							<a href="/feed/${member.groupMemberLoginId}">${member.groupMemberLoginId}</a>
							</div>
							<c:if test="${group.groupManagerId eq member.groupMemberId }">
								<button type="button" class="btn group-manager-btn">그룹장</button>
							</c:if>
							
							<c:if test="${group.groupManagerId ne member.groupMemberId }">
								<div class="member-btn-box">
									<button type="button" class="btn group-member-delete-btn" 
									data-user-profile-image-path="${member.groupMemberProfileImagePath}" 
									data-user-id="${member.groupMemberId}" 
									data-user-login-id="${member.groupMemberLoginId}"
									data-group-id="${member.groupId}">
										삭제
									</button>
								</div>
							</c:if>
						</div>
					</c:forEach>
					
				</div>
			</div>
			<button type="button" class="btn delete-group-btn" 
								data-group-name="${group.groupName}"
								data-group-id="${group.id}"
								data-group-profile-image-path="${group.groupProfileImagePath}">
				그룹삭제
			</button>
		</div> 
	</section>
</section>