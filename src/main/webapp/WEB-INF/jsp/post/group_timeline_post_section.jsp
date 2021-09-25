<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    

	
	<section class="contents-group-timeline-section">
		<c:if test="${userView ne 'timeline/group_timeline_section'}">
			<div class="create-group-post-section">
				<div class="create-group-post-box">
					<img src="/static/images/dummy_profile.jpg" alt="프로필" class="profile-img"/>
					<button type="button" class="create-group-post-modal-btn">무슨 생각을 하고 계신가요?</button>
				</div>
				<hr>
				<div class="create-group-post-btn-box">
					<button type="button" class="create-group-post-photo-btn">
						<span class="material-icons">photo_library</span>사진
					</button>
				</div>
			</div>		
		</c:if>
		
		<c:forEach var="post" items="${groupPostList}">
			<div class="group-post-box">
				<div class="group-post-profile-box">
					<div class="group-profile-item">
						<c:if test="${post.userProfilePath eq null}">
							<img src="/static/images/no_profile_image.png" alt="프로필"/>
						</c:if>
						<c:if test="${post.userProfilePath ne null}">
							<img src="${post.userProfilePath}" alt="프로필"/>
						</c:if>
						<div>
							<a href="/feed/${post.userLoginId}">${post.groupName}</a>
							<a href="/feed/${post.userLoginId}" class="group-user-id">${post.userLoginId}</a>
						</div>
					</div>
					<div>
						<button type="button" class="group-post-menu-btn material-icons">menu</button>
					</div>
				</div>
				<div class="group-post-content-section">${post.content}</div>
				
				<c:if test="${post.contentType eq 'PHOTO'}">
					<div class="group-post-img-section">
						<img src="${post.contentPath }" alt="포스트 이미지"/>
					</div>
				</c:if>
				
				<div>
					<c:if test="${fn:length(post.likeList) ne 0 }">
						<button class="group-post-like-btn">
							<span class="material-icons">thumb_up</span>
							<c:set var="likeSize" value="${fn:length(post.likeList)}"/>
							
							<c:if test="${likeSize eq 1 }">
								${post.likeList[0].userLoginId}
							</c:if>
							
							<c:if test="${likeSize > 1 }">
								${post.likeList[0].userLoginId} 님 외 ${likeSize-1}명
							</c:if>
						</button>
					</c:if>
					<hr>
						<div class="group-post-btn-box">
							
							<c:set var="likeCheck" value="false"/>
							<c:forEach var="like" items="${post.likeList}">
								<c:if test="${like.memberId eq user.id}">
									<c:set var="likeCheck" value="true"/>
								</c:if>
							</c:forEach>
							
							<c:if test="${likeCheck eq false}">
								<div>
									<button class="group-like-before-btn" data-post-id="${post.id}" data-group-id="${post.groupId}">
										<span class="material-icons-outlined ">thumb_up</span> 좋아요
									</button>							
								</div> 
							</c:if>
							<c:if test="${likeCheck eq true}">
								<div>
									<button class="group-like-after-btn" data-post-id="${post.id}" data-group-id="${post.groupId}">
										<span class="material-icons-outlined">thumb_up</span> 좋아요
									</button>							
								</div>
							</c:if>
							<div>							
								<button class="group-post-comment-btn" data-post-id="${post.id}"><span class="material-icons-outlined">chat_bubble_outline</span> 댓글 달기</button>
							</div>
						</div>
					<hr class="mt-2">
				</div>
				<div class="group-post-comment-box">
				
					<div class="group-post-item-box" id="groupPostCommentBox${post.id}">
						<c:set var="commentSize" value="${fn:length(post.commentList)}"/>
						<c:if test="${commentSize > 1}">
							<div>
								<button type="button" class="group-more-comment" data-post-id="${post.id}" data-group-id="${post.groupId}" data-user-id="${user.id}">댓글 ${commentSize-1}개 더보기</button>
							</div>					
						</c:if>
						<c:if test="${commentSize >= 1}">
							<c:set var="comment" value="${post.commentList[0]}"/>
							
							<div class="group-post-comment-item">
								<a href="/feed/${comment.userLoginId}">
									<c:choose>
										<c:when test="${comment.userProfileImagePath ne null}">
											<img src="${comment.userProfileImagePath}"/>
										</c:when>
										<c:otherwise>
											<img src="/static/images/no_profile_image.png"/>
										</c:otherwise>
									</c:choose>
								</a>
								<div class="group-post-comment">
									<div><a href="/feed/${comment.userLoginId}">${comment.userLoginId}</a></div>
									<div>${comment.comment}</div>
								</div>
								<c:if test="${comment.memberId eq user.id || post.groupMemberId eq user.id }">
									<button type="button" class="material-icons-outlined group-comment-menu-btn" data-comment-id="${comment.id}">more_horiz</button>						
								</c:if>
							</div>					
						</c:if>
					</div>
					
					<div class="group-create-comment-box">
						<c:choose>
							<c:when test="${user.profileImagePath eq null}">
								<img src="/static/images/no_profile_image.png"/>							
							</c:when>
							
							<c:otherwise>
								<img src="${user.profileImagePath}"/>
							</c:otherwise>
						</c:choose>
						<input type="text" class="comment-input form-control" id="groupCommentInput${post.id}"/>
						<button type="button" class="group-comment-create-btn" data-post-id="${post.id}" data-group-id="${post.groupId}">게시</button>
					</div>
				</div>
			</div>
		</c:forEach>
	</section>