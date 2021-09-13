<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

		<section class="contents-timeline-section">
			<c:if test="${feedOwnerCheck eq true || feedOwnerCheck eq null}">
			
				<div class="create-post-section ">
					<div class="create-post-box">
					
						<c:if test="${user.profileImagePath eq null}">
							<img src="/static/images/no_profile_image.png" alt="프로필" class="profile-img"/>						
						</c:if>
						<c:if test="${user.profileImagePath ne null}">
							<img src="${user.profileImagePath}" alt="프로필" class="profile-img"/>						
						</c:if>
						
						<button type="button" class="create-post-modal-btn">무슨 생각을 하고 계신가요?</button>
					</div>
					
					<hr>
					
					<div class="create-post-btn-box">
						<label class="create-post-photo-btn" for="loadImageInput">
							<span class="material-icons">photo_library</span>사진
						</label>
					</div>
				</div>
				
			</c:if>
			
				<c:forEach var="post" items="${postList}">
					<div class="post-box">
						<div class="post-profile-box">
							<div class="profile-item">
							
								<c:if test="${post.userProfilePath eq null}">
									<img src="/static/images/no_profile_image.png" alt="프로필"/>
								</c:if>
								<c:if test="${post.userProfilePath ne null }">
									<img src="${post.userProfilePath}" alt="프로필"/>
								</c:if>
								
								<a href="/feed/${post.userLoginId}">${post.userLoginId}</a>
								
								<c:choose>
									<c:when test="${post.disclosureStatus eq 'PUBLIC' }">
										<button class="disclosure-status material-icons">public</button>
									</c:when>
									<c:when test="${post.disclosureStatus eq 'FRIEND' }">
										<button class="disclosure-status material-icons">people</button>
									</c:when>
									<c:when test="${post.disclosureStatus eq 'PRIVATE' }">
										<button class="disclosure-status material-icons">lock</button>
									</c:when>
								</c:choose>
								
							</div>
							
							<div>
								<c:if test="${user.id eq post.userId}">
									<button type="button" class="profile-menu-btn material-icons">menu</button>
								</c:if>
							</div>
							
						</div>
						
						<div class="post-content-section">${post.content}</div>
						<c:if test="${post.contentType eq 'PHOTO'}">
							<div class="post-img-section">
								<img src="${post.contentPath}" alt="포스트 이미지"/>
							</div>						
						</c:if>
						
						<div>
						
							<c:if test="${fn:length(post.likeList) ne 0}">
								<button class="post-like-btn">
									<span class="material-icons">thumb_up</span>
									${post.likeList[0].userLoginId} 
									<c:if test="${fn:length(post.likeList)>1}">
										외 ${fn:length(post.likeList)-1}명
									</c:if>
								</button>
							</c:if>
							
							<hr>
								<div class="post-btn-box">
								
									<c:set var="likeCheck" value="false"/>
									<c:forEach var="like" items="${post.likeList}">
										<c:if test="${like.userId eq user.id}">
											<c:set var="likeCheck" value="true"/>
										</c:if>
									</c:forEach>
									
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
												<span class="material-icons" >thumb_up</span> 좋아요
											</button>							
										</div> 
									</c:if>
									
									<div>							
										<button class="post-comment-btn" data-post-id="${post.id}">
											<span class="material-icons-outlined">chat_bubble_outline</span> 댓글 달기
										</button>
									</div>
									
								</div>
								
							<hr class="mt-2">
							
						</div>
						
						<div class="post-comment-box">
							<c:if test ="${fn:length(post.commentList) ne 0}"> 
								
								<c:if test="${fn:length(post.commentList) >= 2}">
									<div>
										<button type="button" class="more-comment" id="moreComment${post.id}" data-post-id="${post.id}" data-post-owner-id="${post.userId}">댓글 ${fn:length(post.commentList)-1}개 더보기</button>
									</div>
								</c:if>
								
								<div class="post-item-box" id="postCommentItemBox${post.id}">
									<div class="post-comment-item">
										<c:set var="comment" value="${post.commentList[0]}"/>
										
										<a href="/feed/${comment.userLoginId}">
											<c:if test="${comment.userProfileImagePath eq null}">
												<img src="/static/images/no_profile_image.png"/>
											</c:if>
											<c:if test="${comment.userProfileImagePath ne null}">
												<img src="${comment.userProfileImagePath}"/>
											</c:if>
										</a>
										
										<div class="post-comment">
											<div><a href="/feed/${comment.userLoginId}">${comment.userLoginId}</a></div>
											<div>${comment.comment}</div>
										</div>
										
										<c:if test="${user.id eq comment.userId || user.id eq post.userId}">
											<button type="button" class="material-icons-outlined comment-menu-btn" data-comment-id="${comment.id}">more_horiz</button>
										</c:if>
									</div>
								</div>
							</c:if>
							
							<div class="create-comment-box">
							
								<c:if test="${user.profileImagePath eq null}">
									<img src="/static/images/no_profile_image.png" alt="프로필"/>
								</c:if>
								
								<c:if test="${user.profileImagePath ne null }">
									<img src="${user.profileImagePath}" alt="프로필"/>
								</c:if>
								
								<input type="text" id="commentInput${post.id}" class="comment-input form-control"/>
								
								<button type="button" class="comment-create-btn" data-post-id="${post.id}">게시</button>
							</div>
						</div>
					</div>
				</c:forEach>
				
			</section>