<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<header class="header-section">
		<div class="logo-box">
			<a href="#">
				<img src="/static/images/h5tchibook_logo.png" alt="로고" class="logo"/>
			</a>			
		</div>
		<div class="section-nav-box">
			<a href="#" class="material-icons nav-bar-link">home</a>
			<a href="#" class="material-icons nav-bar-link">people_alt</a>
		</div>
		<%-- 연결  --%>
		<div class="user-nav-box">
			<div class="nav-profile-box">
				<img src="/static/images/dummy_profile.jpg" alt="profile image" class="nav-profile"/>
				<span class="nav-profile-name">h5tchi</span>
			</div>
			<div class="nav-alert-btn-box">
				<button class="material-icons nav-alert-btn">notifications</button>
			</div>
			<div class="nav-menu-btn-box">
				<button class="material-icons nav-menu-btn">menu</button>
			</div>
		</div>
		
		<div class="nav-alert-modal d-none">
			<div class="nav-alert-item">
				<a href="#"><img src="/static/images/dummy_profile.jpg" class="alert-profile"/></a>
				<a href="#" class="alert-user-link">h5tchi</a> 
				<span class="alert-description">님이 친구요청을 보냈습니다.</span>
				<button class="btn request-accept-btn">수락</button>
				<button class="btn request-delete-btn">삭제</button>
			</div>
		</div>
		
		<div class="nav-menu-modal d-none">
			<div class="menu-profile-box">
				<img src="/static/images/dummy_profile.jpg" alt="프로필" class="menu-profile"/>
				<div class="menu-profile-item">
					<p class="menu-profile-name">h5tchi</p>
					<p class="menu-profile-description">내 프로필 보기</p>
				</div>
			</div>
			<hr class="nav-menu-hr">
			<div class="menu-item-box">
			
				<div class="menu-item" id="editUserInfo">
					<div class="material-icons menu-item-setting">settings</div>
					<div class="menu-item-description">개인정보 수정</div>
				</div>
				
				<div class="menu-item" id="logOut">
					<div class="material-icons menu-item-setting">logout</div>
					<div class="menu-item-description">로그아웃</div>
				</div>
				
			</div>
		</div>
	</header>