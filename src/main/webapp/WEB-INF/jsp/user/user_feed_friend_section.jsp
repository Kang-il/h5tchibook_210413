<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


 <jsp:include page="../include/delete_friend_modal.jsp"/>

<section class="user-feed-section">
	<jsp:include page="../include/feed_user_profile.jsp"/>
	<section class="user-friend-section">
		<h4 class="feed-title">친구</h4>
		
		<hr>
		
		<div class="feed-friend-box">
		
			<c:forEach var="friend" items="${friendList}" varStatus="status">
				<c:if test="${status.index % 5 eq 0}">
					<div class="feed-friend-row">
				</c:if>
					<div class="feed-friend-item" data-login-id="${friend.friendLoginId}">
						<a href="/feed/${friend.friendLoginId}">
							<c:if test="${friend.friendProfileImagePath eq null}">
								<img src="/static/images/no_profile_image.png"/>
							</c:if>
							<c:if test="${friend.friendProfileImagePath ne null}">
								<img src="${friend.friendProfileImagePath}"/>						
							</c:if>
						</a>
						<div class="friend-info-box">
							<a href="/feed/${friend.friendLoginId}" class="friend-login-id">${friend.friendLoginId}</a>
							<c:if test="${feedOwner.id eq user.id}">
								<div class="material-icons-outlined friend-menu-btn" data-friend-id="${friend.friendId}">more_horiz</div>
							</c:if>
						</div>
					</div>
				<c:if test="${status.index % 5 eq 4}">
					</div>
				</c:if>
			</c:forEach>
		</div>
		
	</section>
	
</section>
<footer>
	<jsp:include page="../include/footer.jsp"></jsp:include>
</footer>
</section>