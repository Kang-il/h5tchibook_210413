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
			
			<section class="contents-timeline-section">
				<div class="create-post-section ">
					<div class="create-post-box">
						<img src="/static/images/dummy_profile.jpg" alt="프로필" class="profile-img"/>
						<button type="button" class="create-post-modal-btn">무슨 생각을 하고 계신가요?</button>
					</div>
					<hr>
					<div class="create-post-btn-box">
						<button type="button" class="create-post-photo-btn">
							<span class="material-icons">photo_library</span>사진
						</button>
					</div>
				</div>
				<div class="post-box">
					<div class="post-profile-box">
						<div class="profile-item">
							<img src="/static/images/dummy_profile.jpg" alt="프로필"/>
							<a href="#">h5tchi</a>
							<button class="disclosure-status material-icons">people</button>
						</div>
						<div>
							<button type="button" class="profile-menu-btn material-icons">menu</button>
						</div>
					</div>
					<div class="post-content-section">집 가고싶어여</div>
					<div class="post-img-section">
						<img src="/static/images/dummy_profile.jpg" alt="포스트 이미지"/>
					</div>
					<div>
						<button class="post-like-btn">
							<span class="material-icons">thumb_up</span>
							aaa외 1명
						</button>
						<hr>
							<div class="post-btn-box">
								<div>
									<button class="like-before-btn"><span class="material-icons-outlined ">thumb_up</span> 좋아요</button>							
								</div> 
								<div>							
									<button class="post-comment-btn"><span class="material-icons-outlined ">chat_bubble_outline</span> 댓글 달기</button>
								</div>
							</div>
						<hr class="mt-2">
					</div>
					<div class="post-comment-box">
						<div>
							<button type="button" class="more-comment">댓글 1개 더보기</button>
						</div>
						<div class="post-comment-item">
							<a href="#"><img src="/static/images/dummy_profile.jpg"/></a>
							<div class="post-comment">
								<div><a href="#">h5tchi</a></div>
								<div>집이시면서.</div>
							</div>
							<button type="button" class="material-icons-outlined comment-menu-btn">more_horiz</button>
						</div>
						<div class="create-comment-box">
							<img src="/static/images/dummy_profile.jpg"/>
							<input type="text" class="comment-input form-control"/>
							<button type="button" class="comment-create-btn">게시</button>
						</div>
					</div>
				</div>
			</section>
			
		</section>
	</section>
