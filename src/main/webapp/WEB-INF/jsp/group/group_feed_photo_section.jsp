<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="group-feed-section">
	<jsp:include page="../include/feed_group_profile.jsp"/>
	<section class="group-photo-section">
		<h4 class="feed-title">사진</h4>
		
		<hr>
		
		<div class="group-photo-box">
			<c:set var="photoListSize" value="${fn:length(groupPhotoList)}"/>
			<c:forEach var="photo" items="${groupPhotoList}" varStatus="status">
				<c:if test="${status.index % 5 eq 0}"> 
					<div class="group-photo-row">
				</c:if>
					<div class="group-photo-item" data-post-id="${photo.id}" data-group-id="${group.id}">
						<img src="${photo.contentPath}"/>
					</div>
				<c:if test="${status.index % 5 eq 4 || status.index eq photoListSize-1}">
					</div>
				</c:if>
			</c:forEach>
		</div>
		
	</section>
	
</section>
<footer>
	<jsp:include page="../include/footer.jsp"></jsp:include>
</footer>
</section>