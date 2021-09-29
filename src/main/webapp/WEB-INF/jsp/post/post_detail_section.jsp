<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="post-detail-section">
	<div class="post-photo-box">
		<c:if test="${post.contentPath ne null }">
			<img src="${post.contentPath}"/>		
		</c:if>
	</div>
	
	<div class="post-info-box">
	 
		<div class="user-profile-box">
		
			<div class="user-info-item">
				<div class="user-profile-img-box">
					<c:choose>
						<c:when test="${post.userProfilePath eq null}">
							<img src="/static/images/no_profile_image.png"/>							
						</c:when>
						<c:otherwise>
							<img src="${post.userProfilePath}"/>
						</c:otherwise>
					</c:choose>
				</div>
				<a href="/feed/${post.userLoginId}">${post.userLoginId}</a>
				<c:choose>
					<c:when test="${post.disclosureStatus eq 'PUBLIC'}">
						<button class="disclosure-status material-icons">public</button>
					</c:when>
					<c:when test="${post.disclosureStatus eq 'FRIEND'}">
						<button class="disclosure-status material-icons">people</button>
					</c:when>
					<c:when test="${post.disclosureStatus eq 'PRIVATE'}">
						<button class="disclosure-status material-icons">lock</button>
					</c:when>
				</c:choose>
			</div> 
			
			<div>
				<c:if test="${user.id eq post.userId }">
					<button type="button" class="profile-menu-btn material-icons" id="postDetailMenuBtn" data-post-id="${post.id}">menu</button>
				</c:if>
			</div>
			 
		</div>
		
		<div class="content-item">${post.content}</div>
		
		<div class="post-like-count-box">
			<c:if test="${fn:length(post.likeList) ne 0}">
				<button class="post-like-btn" data-post-id="${post.id}">
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
			<c:if test="${like.userId eq user.id}">
				<c:set var="likeCheck" value="true" />
			</c:if>
		</c:forEach>
		
		<div class="post-detail-btn-box">
		
			<c:if test="${likeCheck eq false }">
				<div>
					<button class="like-before-btn" data-post-id="${post.id}">
						<span class="material-icons-outlined">thumb_up</span> 좋아요
					</button>
				</div>
			</c:if>
	
			<c:if test="${likeCheck eq true }">
				<div>
					<button class="like-after-btn" data-post-id="${post.id}">
						<span class="material-icons">thumb_up</span> 좋아요
					</button>
				</div>
			</c:if>
		
			<div>
				<button class="post-detail-comment-btn" data-post-id="${post.id}">
					<span class="material-icons-outlined">chat_bubble_outline</span> 댓글달기
				</button>
			</div>
			
		</div>
		
		<hr class="mt-1">
		
		<div class="comment-input-box">
		
			<div class="comment-input-item">
				<div class="comment-user-profile-item">
					<c:choose>
						<c:when test="${user.profileImagePath eq null}">
							<img src="/static/images/no_profile_image.png"/>							
						</c:when>
						<c:otherwise>
							<img src="${user.profileImagePath}"/>
						</c:otherwise>
					</c:choose>
				</div>
				<input type="text" class="form-control post-detail-input"/>
				<button type="button" class="comment-input-btn" data-post-id="${post.id}">게시</button>				
 			</div>
 			
		</div>
		
		<div class="comment-box">
		
			<c:forEach var="comment" items="${post.commentList}">
				<div class="comment-item">
					<div class="comment-content-box">
						<div class="comment-user-profile">
							<c:choose>
								<c:when test="${comment.userProfileImagePath eq null}">
									<img src="/static/images/no_profile_image.png"/>							
								</c:when>
								<c:otherwise>
									<img src="${comment.userProfileImagePath}"/>
								</c:otherwise>
							</c:choose>
						</div>
						<a href="/feed/${comment.userLoginId}" class="comment-user-id">${comment.userLoginId}</a>
						<div class="comment-content">${comment.comment}</div>
					</div>
					<c:if test="${user.id eq post.userId || comment.userId eq user.id }">
						<button type="button" class="material-icons-outlined post-detail-comment-menu-btn" data-comment-id="${comment.id}">more_horiz</button>
					</c:if>					
				</div>
			</c:forEach>
			
		</div> 
		<footer>
			<jsp:include page="../include/footer.jsp"/>
		</footer>
	</div>
</section>