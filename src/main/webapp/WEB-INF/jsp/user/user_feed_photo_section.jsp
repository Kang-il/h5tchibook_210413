<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="user-feed-section">
	<jsp:include page="../include/feed_user_profile.jsp"/>

	<section class="user-photo-section">
		<h4 class="feed-title">사진</h4>
		
		<hr>
		
		<div class="feed-photo-box">
		
			<c:forEach var="photo" items="${photoList}" varStatus="status">
				<c:if test="${status.index % 5 eq 0}">
					<div class="feed-photo-row">
				</c:if>
					<div class="feed-photo-item" data-post-id="${photo.id}">
						<img src="${photo.contentPath}"/>
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