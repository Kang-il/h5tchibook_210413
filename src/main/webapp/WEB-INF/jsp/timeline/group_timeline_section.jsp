<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<section class="timeline-section">

	<section class="group-timeline-info-section">
		<div class="group-timeline-user-profile">
			<img src="/static/images/dummy_profile.jpg" alt="프로필" class="group-timeline-info-profile-img"/>
			<span>h5tchi</span>
		</div>
		
		<div class="group-timeline-info-menu">
			<span class="material-icons-outlined">burst_mode</span>
			<span>내 피드</span>
		</div>
		
		<div class="create-new-group">
			<span> +&nbsp;</span><span> 새 그룹 만들기</span> 
		</div>
		
		<hr>
		
		<h5 class="my-shortcuts-title">가입한 그룹</h5>
		<div class="group-timeline-group-box"> 
			<div class="group-timeline-group-item">
				<img src="/static/images/dummy_profile.jpg" alt="그룹 이미지" class="group-timeline-group-profile-img"/>
				<span>h5tchi's group</span>
			</div>
		</div>
		<footer>
			<jsp:include page="../include/footer.jsp"/>
		</footer>
	</section>
	
	<section class="group-timeline-post-section">
		<jsp:include page="../post/group_timeline_post_section.jsp"/>
	</section>
</section>