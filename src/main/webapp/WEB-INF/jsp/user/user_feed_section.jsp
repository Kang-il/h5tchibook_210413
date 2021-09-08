<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<section class="user-feed-section">
		<div class="user-profile-box">
			<div class="user-profile-background">
				<img src="/static/images/dummy_profile.jpg" class="profile-background"/>
				<div class="img-box">
					<img src="/static/images/dummy_profile.jpg" alt="프로필"/>
				</div>
				<button class="material-icons profile-img-edit-btn">photo_camera</button>
				<button class=" profile-background-edit-btn"><span class="material-icons">photo_camera</span> 커버사진 편집</button>
			</div>
			<div class="profile-introduce-box">
				<h3 class="profile-user-id">h5tchi</h3>
				<button class="add-introduce-btn">소개 추가</button>
			<hr>
	
			</div>
			<nav class="profile-nav-box">
				<div class="profile-nav-item">
					<a href="#" class="item-link">게시물</a>
					<a href="#" class="item-link">친구</a>
					<a href="#" class="item-link">사진</a>
				</div>
				<button type="button" class="edit-profile-btn"><span class="material-icons">edit</span>프로필 편집</button>
			</nav>
		</div> 
		
		<section class="user-contents-section">
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
						<h5 class="title">친구</h5>
						<a href="#">모든 친구 보기</a>
					</div>
					
					<div>친구 135명</div>
					
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
			
			<jsp:include page="../post/timeline_post_section.jsp"/>
			
		</section>
	</section>
