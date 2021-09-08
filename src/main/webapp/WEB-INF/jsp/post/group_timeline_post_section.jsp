<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<section class="contents-group-timeline-section">
		<div class="create-group-post-section ${section_display}">
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
		<div class="group-post-box">
			<div class="group-post-profile-box">
				<div class="group-profile-item">
					<img src="/static/images/dummy_profile.jpg" alt="프로필"/>
					<div>
						<a href="#">h5tchi'group</a>
						<a href="#" class="group-user-id">h5tchi</a>
					</div>
				</div>
				<div>
					<button type="button" class="group-post-menu-btn material-icons">menu</button>
				</div>
			</div>
			<div class="group-post-content-section">집 가고싶어여</div>
			<div class="group-post-img-section">
				<img src="/static/images/dummy_profile.jpg" alt="포스트 이미지"/>
			</div>
			<div>
				<button class="group-post-like-btn">
					<span class="material-icons">thumb_up</span>
					aaa외 1명
				</button>
				<hr>
					<div class="group-post-btn-box">
						<div>
							<button class="group-like-before-btn"><span class="material-icons-outlined ">thumb_up</span> 좋아요</button>							
						</div> 
						<div>							
							<button class="group-post-comment-btn"><span class="material-icons-outlined ">chat_bubble_outline</span> 댓글 달기</button>
						</div>
					</div>
				<hr class="mt-2">
			</div>
			<div class="group-post-comment-box">
				<div>
					<button type="button" class="group-more-comment">댓글 1개 더보기</button>
				</div>
				<div class="group-post-comment-item">
					<a href="#"><img src="/static/images/dummy_profile.jpg"/></a>
					<div class="group-post-comment">
						<div><a href="#">h5tchi</a></div>
						<div>집이시면서.</div>
					</div>
					<button type="button" class="material-icons-outlined group-comment-menu-btn">more_horiz</button>
				</div>
				<div class="group-create-comment-box">
					<img src="/static/images/dummy_profile.jpg"/>
					<input type="text" class="comment-input form-control"/>
					<button type="button" class="group-comment-create-btn">게시</button>
				</div>
			</div>
		</div>
	</section>