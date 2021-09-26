<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<section class="user-feed-section">
		<jsp:include page="../include/feed_user_profile.jsp"/>
		
		<section class="user-contents-section">
			<section class="contents-info-section">
				<div class="content-photo-box">
				
					<div class="photo-box-title">
						<h5 class="title">사진</h5>
						<a href="/feed/${feedOwner.loginId}?category=photo">사진 더보기</a>
					</div>
					
					<div class="content-photo-item-section">
						<div class="photo-item-row">
							<c:forEach var="photo" items="${photoList}">
								<div class="photo-item-box">
									<a href="/post/detail?postId=${photo.id}"><img class="photo-item" src="${photo.contentPath}"/></a>
								</div>
							</c:forEach>
						</div>
					</div>
					
				</div>
				
				<div class="content-friend-box">
				
					<div class="friend-box-title">
						<h5 class="title">친구</h5>
						<a href="/feed/${feedOwner.loginId}?category=friend">모든 친구 보기</a>
					</div>
					
					<div>친구 ${fn:length(friendList)}명</div>
					
					<div class="content-friend-item-section">
						<div class="friend-item-row">
							<c:forEach var="friend" items="${friendList}">
								<div class="friend-item-box">
									
									<a href="/feed/${friend.friendLoginId}">
										<c:if test="${friend.friendProfileImagePath eq null}">
											<img class="friend-item" src="/static/images/no_profile_image.png"/>
										</c:if>
										
										<c:if test="${friend.friendProfileImagePath ne null}">
											<img class="friend-item" src="${friend.friendProfileImagePath}"/>
										</c:if>
									</a>
									
									<div><a href="#" class="friend-item-link">${friend.friendLoginId}</a></div>
								</div>
							</c:forEach>
						</div>
					</div>
					
				</div>
				
				<footer>
					<jsp:include page="../include/footer.jsp"/>
				</footer>
				
			</section>
			<%-- 포스트 리스트 뿌리기 --%>
			<jsp:include page="../post/timeline_post_section.jsp"/>
		</section>
	</section>
