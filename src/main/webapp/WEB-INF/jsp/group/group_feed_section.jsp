<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<section class="group-feed-section">
		<div class="group-profile-box">
			<div class="group-profile-background">
				<img src="/static/images/dummy_profile.jpg" class="group-background"/>
				<button class="background-edit-btn"><span class="material-icons">photo_camera</span> 커버사진 편집</button>
			</div>
			<div class="group-introduce-box">
				<h3 class="group-id">h5tchi's group</h3>
				<span class="group-member-count">멤버 1명</span>
			<hr>
	
			</div>
			<nav class="profile-nav-box">
				<div class="profile-nav-item">
					<a href="#" class="item-link">게시물</a>
					<a href="#" class="item-link">사진</a>
				</div>
				<button type="button" class="join-group-btn"><span class="material-icons">group_add</span> &nbsp; 그룹 가입</button>
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
						<div class="photo-item-row">
							<div class="photo-item-box">
								<a href="#"><img class="photo-item" src="/static/images/dummy_profile.jpg"/></a>
							</div>
							<div class="photo-item-box">
								<a href="#"><img class="photo-item" src="/static/images/dummy_profile.jpg"/></a>
							</div>
							<div class="photo-item-box">
								<a href="#"><img class="photo-item" src="/static/images/dummy_profile.jpg"/></a>
							</div>
						</div>
					</div>
					
				</div>
				
				<div class="content-friend-box">
				
					<div class="friend-box-title">
						<h5 class="title">그룹멤버</h5>
						<a href="#">모든 멤버 보기</a>
					</div>
					
					<div>멤버 135명</div>
					
					<div class="content-friend-item-section">
						<div class="friend-item-row">
						
							<div class="friend-item-box">
								<a href="#"><img class="friend-item" src="/static/images/dummy_profile.jpg"/></a>
								<div><a href="#" class="friend-item-link">h5tchi</a></div>
							</div>
							
							<div class="friend-item-box">
								<a href="#"><img class="friend-item" src="/static/images/dummy_profile.jpg"/></a>
								<div><a href="#" class="friend-item-link">h5tchi</a></div>
							</div>
							
							<div class="friend-item-box">
								<a href="#"><img class="friend-item" src="/static/images/dummy_profile.jpg"/></a>
								<div><a href="#" class="friend-item-link">h5tchi</a></div>
							</div>
							
						</div>
						
					</div>
					
				</div>
				
				<footer>
					<jsp:include page="../include/footer.jsp"/>
				</footer>
				
			</section>
			
			<jsp:include page="../include/group_timeline_post.jsp"/>
			
		</section>
	</section>