<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<section class="timeline-section">

	<section class="timeline-info-section">
		<div class="timeline-user-profile">
			<img src="/static/images/dummy_profile.jpg" alt="프로필" class="timeline-info-profile-img"/>
			<span>h5tchi</span>
		</div>
		
		<div class="timeline-info-menu">
			<span class="material-icons-outlined">group</span>
			<span>친구</span>
		</div>
		
		<div class="timeline-info-menu">
			<span class="material-icons-outlined">groups</span>
			<span>그룹</span>
		</div>
		
		<hr>
		
		<h5 class="my-shortcuts-title">내 바로가기</h5>
		<div class="timeline-group-box"> 
			<div class="timeline-group-item">
				<img src="/static/images/dummy_profile.jpg" alt="그룹 이미지" class="timeline-group-profile-img"/>
				<span>h5tchi's group</span>
			</div>
		</div>
		<footer>
			<jsp:include page="../include/footer.jsp"/>
		</footer>
	</section>
	
	<section class="timeline-post-section">
		<jsp:include page="../post/timeline_post_section.jsp"/>
	</section>
</section>