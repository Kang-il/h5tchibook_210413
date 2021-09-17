<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<jsp:include page="../include/edit_profile_modal.jsp"/>
<div class="user-profile-box">
	<div class="user-profile-background">
		<c:if test="${feedOwner.coverImagePath eq null}">
			<img src="/static/images/no_background_image.jpg" class="profile-background"/>
			<input type="hidden" value="/static/images/no_background_image.jpg" class="original-background-img"/>				
		</c:if>
		<c:if test="${feedOwner.coverImagePath ne null}">
			<img src="${feedOwner.coverImagePath}" class="profile-background" class="original-background-img"/>
			<input type="hidden" value="${feedOwner.coverImagePath}"/>					
		</c:if>
		
		<div class="img-box">
			<c:if test="${feedOwner.profileImagePath eq null}">
				<img src="/static/images/no_profile_image.png" alt="프로필"/>
			</c:if>
			<c:if test="${feedOwner.profileImagePath ne null}">
				<img src="${feedOwner.profileImagePath}" alt="프로필"/>
			</c:if>	
		</div>
				
		<c:if test="${feedOwnerCheck eq true}">
			<div>
				<button class="material-icons profile-img-edit-btn">photo_camera</button>
				<div class="profile-pic-modal d-none">
					<div class="profile-pic-item">
						<button type="button" class="show-edit-profile-modal-btn" data-feed-owner-login-id="${feedOwner.loginId}"><span class="material-icons-outlined">account_circle</span>사진 변경</button>
					</div>
				</div>
			</div>
			<div>
				<button class="profile-background-edit-btn" data-feed-owner-login-id="${feedOwner.loginId}"><span class="material-icons">photo_camera</span> 커버사진 편집</button>
				<input type="file" class="change-background-img-input d-none"/>
			</div>
		</c:if>
	</div>
	
	<div class="profile-introduce-box">
		<h3 class="profile-user-id">${feedOwner.loginId}</h3>
				
		<c:if test="${feedOwnerCheck eq true}">
			<c:if test="${feedOwner.introduce eq null}">
				<button class="add-introduce-btn" data-user-introduce="" data-feed-owner-login-id="${feedOwner.loginId}">소개 추가</button>
			</c:if>
			<c:if test="${feedOwner.introduce ne null}">
				<button class="add-introduce-btn" data-user-introduce="${feedOwner.introduce}" data-feed-owner-login-id="${feedOwner.loginId}">${feedOwner.introduce}</button>
			</c:if>
		</c:if>
		<c:if test="${feedOwnerCheck eq false}">
			<c:if test="${feedOwner.introduce eq null}">
				<span class="user-introduce">소개가 없습니다.</span>
			</c:if>
			<c:if test="${feedOwner.introduce ne null}">
				<button class="user-introduce" >${feedOwner.introduce}</button>
			</c:if>
		</c:if>
	<hr>
	</div>
	
	<nav class="profile-nav-box">
	
		<div class="profile-nav-item">
			<c:if test="${userView eq 'user/user_feed_post_section'}">
				<a href="/feed/${feedOwner.loginId}" class="item-link-in-use">게시물</a>
				<a href="/feed/${feedOwner.loginId}?category=friend" class="item-link">친구</a>
				<a href="/feed/${feedOwner.loginId}?category=photo" class="item-link">사진</a>
			</c:if>
			
			<c:if test="${userView eq 'user/user_feed_friend_section'}">
				<a href="/feed/${feedOwner.loginId}" class="item-link">게시물</a>
				<a href="/feed/${feedOwner.loginId}?category=friend" class="item-link-in-use">친구</a>
				<a href="/feed/${feedOwner.loginId}?category=photo" class="item-link">사진</a>
			</c:if>
			
			<c:if test="${userView eq 'user/user_feed_photo_section'}">
				<a href="/feed/${feedOwner.loginId}" class="item-link">게시물</a>
				<a href="/feed/${feedOwner.loginId}?category=friend" class="item-link">친구</a>
				<a href="/feed/${feedOwner.loginId}?category=photo" class="item-link-in-use">사진</a>
			</c:if>
		</div>
				
		<c:if test="${feedOwnerCheck eq true}">
			<button type="button" class="edit-profile-btn"><span class="material-icons">edit</span>프로필 편집</button>				
		</c:if>
				
		<c:if test="${feedOwnerCheck eq false}">
				
			<c:if test="${friendCheck eq true}">	
				<button type="button" class="friend-btn" data-feed-owner-id="${feedOwner.id}"><span class="material-icons">people</span>친구</button>
			</c:if>
					
			<c:if test="${friendCheck eq false}">
				<c:if test="${requestFriendCheck eq true && receiveFriendCheck eq false}">
					<button type="button" class="progress-friend-btn"  data-feed-owner-id="${feedOwner.id}"><span class="material-icons">group_add</span>친구요청 취소</button>
				</c:if>
						
				<c:if test="${requestFriendCheck eq false && receiveFriendCheck eq true}">
					<button type="button" class="response-friend-btn"  data-feed-owner-id="${feedOwner.id}"><span class="material-icons">group_add</span>요청 확인</button>
				</c:if>
						
				<c:if test="${requestFriendCheck eq false && receiveFriendCheck eq false}">
					<button type="button" class="request-friend-btn" data-feed-owner-id="${feedOwner.id}"><span class="material-icons">group_add</span>친구요청</button>
				</c:if>
			</c:if>
						
		</c:if>	
		
	</nav>
	
</div> 