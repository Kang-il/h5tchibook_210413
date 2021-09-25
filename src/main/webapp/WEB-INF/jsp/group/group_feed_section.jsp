<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="group-feed-section">
		<div class="group-profile-box">
			<div class="group-profile-background">
			
				<c:choose>
					<c:when test="${group.groupCoverImagePath eq null}">
						<img src="/static/images/no_background_image.jpg" class="group-background"/>					
					</c:when>
					<c:otherwise>
						<img src="${group.groupCoverImagePath}" class="group-background"/>
					</c:otherwise>
				</c:choose>
				<button class="background-edit-btn"><span class="material-icons">photo_camera</span> 커버사진 편집</button>
			</div>
			<div class="group-introduce-box">
				<h3 class="group-id">${group.groupName}</h3>
				<span class="group-member-count">그룹 멤버 ${groupMemberCount} 명</span>
			<hr>
	
			</div>
			<nav class="profile-nav-box">
				<div class="profile-nav-item">
					<a href="#" class="item-link">게시물 </a>
					<a href="#" class="item-link">사진</a>
				</div>
				<c:choose>
					<c:when test="${user.id eq group.groupManagerId}">
						<button type="button" class="join-group-btn"><span class="material-icons">edit</span> &nbsp; 그룹 관리</button>
					</c:when>
					
					<c:when test="${groupJoinRequest eq null }">
						<button type="button" class="join-group-btn"><span class="material-icons">group_add</span> &nbsp; 그룹 가입</button>
					</c:when>
					
					<c:when test="${groupJoinRequest ne null }">
						<button type="button" class="join-group-btn"><span class="material-icons">group_add</span> &nbsp; 가입 승인 대기중</button>
					</c:when>
				</c:choose>
				
				
				
			</nav>
		</div> 
		
		<section class="group-contents-section">
			<section class="contents-info-section">
				<div class="content-photo-box">
				
					<div class="photo-box-title">
						<h5 class="title">사진</h5>
						<a href="#">사진 더보기</a>
					</div>
					
					<div class="content-photo-item-section">
					
						<c:set var="photoListSize" value="${fn:length(groupPhotoList)}"/>
						
						<c:forEach var="photo" items="${groupPhotoList}" varStatus="status">	
												
						<c:if test="${status.index % 3 eq 0}">
							<div class="photo-item-row">
						</c:if>
						
							<div class="photo-item-box">
								<a href="#">
									<img class="photo-item" src="${photo.contentPath}"/>
								</a>
							</div>
						<c:if test="${status.index %3 eq 2 || status.index eq photoListSize-1}">
							</div>							
						</c:if>
						
						</c:forEach>	
						
					</div>
				</div>
				
				<div class="content-friend-box">
				
					<div class="friend-box-title">
						<h5 class="title">그룹멤버</h5>
						<a href="#">모든 멤버 보기</a>
					</div>
					
					<div>멤버 ${groupMemberCount}명</div>
					<c:set var="groupMemberSize" value="${fn:length(groupMemberList) }"/>
					<div class="content-friend-item-section">
						<c:forEach var="member" items="${groupMemberList}" varStatus="status">
						<c:if test="${status.index % 3 eq 0}">
							<div class="friend-item-row">
						</c:if>						
							<div class="friend-item-box">
								<a href="/feed/${member.groupMemberLoginId }">
									<c:choose>
										<c:when test="${member.groupMemberProfileImagePath eq null }">
											<img class="friend-item" src="/static/images/no_profile_image.png"/>
										</c:when>
										<c:otherwise>
											<img class="friend-item" src="${member.groupMemberProfileImagePath}"/>
										</c:otherwise>
									</c:choose>
									
								</a>
								<div><a href="#" class="friend-item-link">h5tchi</a></div>
							</div>
						<c:if test="${status.index %3 eq 2 || status.index eq groupMemberSize-1 }">
							</div>
						</c:if>
						</c:forEach>
					</div>
					
				</div>
				
				<footer>
					<jsp:include page="../include/footer.jsp"/>
				</footer>
				
			</section>
			
			<jsp:include page="../post/group_timeline_post_section.jsp"/>
			
		</section>
	</section>