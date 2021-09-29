<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    <div class="modal-window d-none"></div>
    
	<header class="header-section">
	
		<div class="logo-box">
			<a href="/timeline/user_timeline_view">
				<img src="/static/images/h5tchibook_logo.png" alt="로고" class="logo"/>
			</a>			
		</div>
		
		<div class="section-nav-box">
			<c:if test="${userView eq 'timeline/user_timeline_section'}">
				<a href="/timeline/user_timeline_view" class="material-icons nav-bar-link nav-bar-in-use">home</a>
				<a href="/timeline/group_timeline_view" class="material-icons nav-bar-link">people_alt</a>
			</c:if>
			<c:if test="${userView eq 'timeline/group_timeline_section'}">
				<a href="/timeline/user_timeline_view"  class="material-icons nav-bar-link">home</a>
				<a href="/timeline/group_timeline_view" class="material-icons nav-bar-link nav-bar-in-use">people_alt</a>
			</c:if>
			<c:if test="${userView ne 'timeline/user_timeline_section' && userView ne 'timeline/group_timeline_section'}">
				<a href="/timeline/user_timeline_view"  class="material-icons nav-bar-link">home</a>
				<a href="/timeline/group_timeline_view" class="material-icons nav-bar-link">people_alt</a>
			</c:if>
			
			
		</div>
		
		<%-- 연결  --%>
		<div class="user-nav-box">
			<c:if test="${feedOwnerCheck eq true}">
				<div class="nav-profile-box-in-use" data-user-login-id="${user.loginId}">
					<c:if test="${user.profileImagePath eq null}">
						<img src="/static/images/no_profile_image.png" alt="profile image" class="nav-profile"/>
					</c:if>
					<c:if test="${user.profileImagePath ne null}">
						<img src="${user.profileImagePath}" alt="profile image" class="nav-profile"/>
					</c:if>
					<span class="nav-profile-name-in-use">${user.loginId}</span>				
				</div>
			</c:if>
			
			<c:if test="${feedOwnerCheck eq false || feedOwnerCheck eq null}">
				<div class="nav-profile-box" data-user-login-id="${user.loginId}">
					<c:if test="${user.profileImagePath eq null}">
						<img src="/static/images/no_profile_image.png" alt="profile image" class="nav-profile"/>
					</c:if>
					<c:if test="${user.profileImagePath ne null}">
						<img src="${user.profileImagePath}" alt="profile image" class="nav-profile"/>
					</c:if>
					<span class="nav-profile-name">${user.loginId}</span>				
				</div>				
			</c:if>
			
			<div class="nav-alert-btn-box">
				<button class="material-icons nav-alert-btn">notifications</button>
			</div>
			<div class="nav-menu-btn-box">
				<button class="material-icons nav-menu-btn">menu</button>
			</div>
		</div>
		
		<div class="nav-alert-modal d-none">
			<c:forEach var="alert" items="${alertList}">
				<div class="nav-alert-item">
				
					<c:if test="${alert.alertType eq 'COMMENT'}">
					
						<div class="alert-info-box">
							<a href="/feed/${alert.sendUserLoginId}">
								<c:choose>
									<c:when test="${alert.sendUserProfileImagePath eq null }">
										<img src="/static/images/no_profile_image.png" class="alert-profile"/>
									</c:when>
									<c:otherwise>
										<img src="${alert.sendUserProfileImagePath}" class="alert-profile"/>
									</c:otherwise>
								</c:choose>
							</a>
							<a href="/feed/${alert.sendUserLoginId}" class="alert-user-link">${alert.sendUserLoginId}</a> 
							<span class="alert-description">님이 댓글을 남겼습니다.</span>
						</div>
						<div class="alert-post">
							<a href="/post/post_detail_view?postId=${alert.postId}">
								<c:if test="${alert.postImagePath ne null}">
									<img src="${alert.postImagePath}" class="alert-post-image"/>
								</c:if>
								<c:if test="${alert.postImagePath eq null}">
									<img src="/static/images/no_post_image.jpg" class="alert-post-image"/>
								</c:if>
							</a>
						</div>
						
					</c:if>
					
					<c:if test="${alert.alertType eq 'LIKE'}">
					
						<div class="alert-info-box">
							<a href="/feed/${alert.sendUserLoginId}">
								<c:choose>
									<c:when test="${alert.sendUserProfileImagePath eq null }">
										<img src="/static/images/no_profile_image.png" class="alert-profile"/>
									</c:when>
									<c:otherwise>
										<img src="${alert.sendUserProfileImagePath}" class="alert-profile"/>
									</c:otherwise>
								</c:choose>
							</a>
							<a href="/feed/${alert.sendUserLoginId}" class="alert-user-link">${alert.sendUserLoginId}</a> 
							<span class="alert-description">님이 좋아요를 눌렀습니다.</span>
						</div>
						<div class="alert-post">
							<a href="/post/post_detail_view?postId=${alert.postId}">
								<c:if test="${alert.postImagePath ne null}">
									<img src="${alert.postImagePath}" class="alert-post-image"/>
								</c:if>
								<c:if test="${alert.postImagePath eq null}">
									<img src="/static/images/no_post_image.jpg" class="alert-post-image"/>
								</c:if>
							</a>
						</div>
						
					</c:if>
					
					<c:if test="${alert.alertType eq 'FRIEND_REQUEST'}">
					
						<div class="alert-info-box">
							<a href="/feed/${alert.sendUserLoginId}">
								<c:choose>
									<c:when test="${alert.sendUserProfileImagePath eq null }">
										<img src="/static/images/no_profile_image.png" class="alert-profile"/>
									</c:when>
									<c:otherwise>
										<img src="${alert.sendUserProfileImagePath}" class="alert-profile"/>
									</c:otherwise>
								</c:choose>
							</a>
							<a href="/feed/${alert.sendUserLoginId}" class="alert-user-link">${alert.sendUserLoginId}</a>  
							<span class="alert-description">님이 친구요청을 보냈습니다.</span>
							<c:set var="friendCheck" value="${false}"/>
							
							<c:forEach var="friend" items="${friendList}">
								<c:if test="${alert.sendUserId eq friend.friendId}">
									<c:set var="friendCheck" value="${true}"/>
								</c:if>
							</c:forEach>
						</div>
								
					</c:if>
					
					<c:if test="${alert.alertType eq 'GROUP_JOIN_REQUEST'}">
						<div class="alert-info-box">
							<a href="/feed/${alert.sendUserLoginId}">
								<c:choose>
									<c:when test="${alert.sendUserProfileImagePath eq null }">
										<img src="/static/images/no_profile_image.png" class="alert-profile"/>
									</c:when>
									<c:otherwise>
										<img src="${alert.sendUserProfileImagePath}" class="alert-profile"/>
									</c:otherwise>
								</c:choose>
							</a>
							<a href="/feed/${alert.sendUserLoginId}" class="alert-user-link">${alert.sendUserLoginId}</a>  
							<span class="alert-description">님이 그룹가입 요청을 보냈습니다.</span>
						</div>
							
						<div class="alert-post">
							<a href="/group/edit/edit_group_view/${alert.groupName}">
								<c:if test="${alert.groupProfileImagePath ne null}">
									<img src="${alert.groupProfileImagePath}" class="alert-post-image"/>
								</c:if>
								<c:if test="${alert.groupProfileImagePath eq null}">
									<img src="/static/images/no_profile_image.png" class="alert-post-image"/>
								</c:if>
							</a>
						</div>

					</c:if>
					
					<c:if test="${alert.alertType eq 'GROUP_COMMENT'}"> 
						
						<div class="alert-info-box">
							<a href="/feed/group/${alert.groupName}">
								<c:choose>
									<c:when test="${alert.groupProfileImagePath eq null }">
										<img src="/static/images/no_profile_image.png" class="alert-group-profile"/>
									</c:when>
									<c:otherwise>
										<img src="${alert.groupProfileImagePath}" class="alert-group-profile"/>
									</c:otherwise>
								</c:choose>
							</a>
							<a href="/feed/${alert.sendUserLoginId}" class="alert-user-link">${alert.sendUserLoginId}</a> 
							<span class="alert-description">님이 댓글을 남겼습니다.</span>
						</div>
						<div class="alert-post">
							<a href="/group/post/group_post_detail_view?postId=${alert.postId}&groupId=${alert.groupId}">
								<c:if test="${alert.postImagePath ne null}">
									<img src="${alert.postImagePath}" class="alert-post-image"/>
								</c:if>
								<c:if test="${alert.postImagePath eq null}">
									<img src="/static/images/no_post_image.jpg" class="alert-post-image"/>
								</c:if>
							</a>
						</div>
						
					</c:if>
					
					<c:if test="${alert.alertType eq 'GROUP_LIKE'}">
						<div class="alert-info-box">
							<a href="/feed/group/${alert.groupName}">
								<c:choose>
									<c:when test="${alert.groupProfileImagePath eq null }">
										<img src="/static/images/no_profile_image.png" class="alert-group-profile"/>
									</c:when>
									<c:otherwise>
										<img src="${alert.groupProfileImagePath}" class="alert-group-profile"/>
									</c:otherwise>
								</c:choose>
							</a>
							<a href="/feed/${alert.sendUserLoginId}" class="alert-user-link">${alert.sendUserLoginId}</a> 
							<span class="alert-description">님이 좋아요를 눌렀습니다.</span>
						</div>
						<div class="alert-post">
							<a href="/group/post/group_post_detail_view?postId=${alert.postId}&groupId=${alert.groupId}">
								<c:if test="${alert.postImagePath ne null}">
									<img src="${alert.postImagePath}" class="alert-post-image"/>
								</c:if>
								<c:if test="${alert.postImagePath eq null}">
									<img src="/static/images/no_post_image.jpg" class="alert-post-image"/>
								</c:if>
							</a>
						</div>
					</c:if>
					
				</div>
			</c:forEach>
		</div>
		
		<div class="nav-menu-modal d-none">
		
			<div class="menu-profile-box" data-user-login-id="${user.loginId}">
				<c:if test="${user.profileImagePath eq null}">
					<img src="/static/images/no_profile_image.png" alt="프로필" class="menu-profile"/>
				</c:if>
				<c:if test="${user.profileImagePath ne null }">
					<img src="${user.profileImagePath}" alt="프로필" class="menu-profile"/>
				</c:if>
				<div class="menu-profile-item">
					<p class="menu-profile-name">${user.loginId}</p>
					<p class="menu-profile-description">내 프로필 보기</p>
				</div>
			</div>
			
			<hr class="nav-menu-hr">
			
			<div class="menu-item-box">
				<div class="menu-item" id="editUserInfo">
					<div class="material-icons menu-item-setting">settings</div>
					<div class="menu-item-description" data-user-login-id="${user.loginId}">개인정보 수정</div>
				</div>
				
				<div class="menu-item" id="logOut">
					<div class="material-icons menu-item-setting">logout</div>
					<div class="menu-item-description">로그아웃</div>
				</div>
			</div>

		</div>
		
	</header>