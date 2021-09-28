<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="group-post-detail-section">
	<div class="group-post-photo-box">
		<img src="${post.contentPath}"/>
	</div>
	
	<div class="group-post-info-box">
	 
		<div class="group-profile-box">
		
			<div class="group-info-item">
				<div class="group-profile-img-box">
					<c:choose>
						<c:when test="${post.userProfilePath eq null}">
							<img src="/static/images/no_profile_image.png"/>							
						</c:when>
						<c:otherwise>
							<img src="${post.userProfilePath}"/>
						</c:otherwise>
					</c:choose>
				</div>
				<div>   
					<div>
						<a href="/feed/group/${group.groupName}" class="group-feed-link">${group.groupName}</a>
					</div>
					<div class="user-feed-link-box">
						<a href="/feed/${post.userLoginId}" class="user-feed-link">${post.userLoginId}</a>				
					</div>
				</div>
			</div>
			
			<div>
				<c:if test="${user.id eq post.groupMemberId || user.id eq group.groupManagerId}">
					<button type="button" class="profile-menu-btn material-icons" id="groupPostDetailMenuBtn" data-post-id="${post.id}">menu</button>
				</c:if>
			</div>
			 
		</div>
		
		<div class="content-item">${post.content}</div>
		
		<div class="group-post-like-count-box">
			<c:if test="${fn:length(post.likeList) ne 0}">
				<button class="group-post-like-btn">
					<span class="material-icons">thumb_up</span>
					${post.likeList[0].userLoginId}
					<c:if test="${fn:length(post.likeList)>1}">
						외 ${fn:length(post.likeList)-1}명
					</c:if>
				</button>
			</c:if>
		</div>
		<hr class="mb-1">
		
		<c:set var="likeCheck" value="false"/>
		<c:forEach var="like" items="${post.likeList}">
			<c:if test="${like.memberId eq user.id}">
				<c:set var="likeCheck" value="true" />
			</c:if>
		</c:forEach>
		
		<div class="group-post-detail-btn-box">
		
			<c:if test="${likeCheck eq false }">
				<div>
					<button class="detail-group-like-before-btn" data-post-id="${post.id}" data-group-id="${group.id}">
						<span class="material-icons-outlined">thumb_up</span> 좋아요
					</button>
				</div>
			</c:if>
	
			<c:if test="${likeCheck eq true }">
				<div>
					<button class="detail-group-like-after-btn" data-post-id="${post.id}" data-group-id="${group.id}">
						<span class="material-icons">thumb_up</span> 좋아요
					</button>
				</div>
			</c:if>
		
			<div>
				<button class="group-post-detail-comment-btn">
					<span class="material-icons-outlined">chat_bubble_outline</span> 댓글달기
				</button>
			</div>
			
		</div>
		
		<hr class="mt-1">
		
		<div class="group-comment-input-box">
		
			<div class="group-comment-input-item">
				<div class="group-comment-user-profile-item">
					<c:choose>
						<c:when test="${user.profileImagePath eq null}">
							<img src="/static/images/no_profile_image.png"/>							
						</c:when>
						<c:otherwise>
							<img src="${user.profileImagePath}"/>
						</c:otherwise>
					</c:choose>
				</div>
				<input type="text" class="form-control group-post-detail-input"/>
				<button type="button" class="group-comment-input-btn" data-post-id="${post.id}" data-group-id="${post.groupId}">게시</button>				
 			</div>
 			
		</div>
		
		<div class="group-comment-box">
		
			<c:forEach var="comment" items="${post.commentList}">
				<div class="group-comment-item">
					<div class="group-comment-content-box">
						<div class="group-comment-user-profile">
							<c:choose>
								<c:when test="${comment.userProfileImagePath eq null}">
									<img src="/static/images/no_profile_image.png"/>							
								</c:when>
								<c:otherwise>
									<img src="${comment.userProfileImagePath}"/>
								</c:otherwise>
							</c:choose>
						</div>
						<a href="/feed/${comment.userLoginId}" class="group-comment-user-id">${comment.userLoginId}</a>
						<div class="group-comment-content">${comment.comment}</div>
					</div>
					<c:if test="${user.id eq post.groupMemberId || comment.memberId eq user.id || user.id eq group.groupManagerId }">
						<button type="button" class="material-icons-outlined group-post-detail-comment-menu-btn" data-comment-id="${comment.id}">more_horiz</button>
					</c:if>					
				</div>
			</c:forEach>
			
		</div> 
		<footer>
			<jsp:include page="../include/footer.jsp"/>
		</footer>
	</div>
</section>