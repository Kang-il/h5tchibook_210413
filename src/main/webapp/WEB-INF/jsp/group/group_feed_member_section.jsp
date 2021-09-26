<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section class="group-feed-section">
	<jsp:include page="../include/feed_group_profile.jsp"/>
		<section class="group-feed-member-section">
		<h4 class="feed-title">그룹 멤버</h4>
		
		<hr>
		
		<div class="group-feed-member-box">
			<c:set var="groupMemberSize" value="${fn:length(groupMemberList) }"/>
			<c:forEach var="member" items="${groupMemberList}" varStatus="status">
				<c:if test="${status.index % 5 eq 0}">
					<div class="feed-member-row">
				</c:if>
					<div class="group-feed-member-item" data-login-id="${member.groupMemberLoginId}">
						<a href="/feed/${member.groupMemberLoginId}">
							<c:if test="${member.groupMemberProfileImagePath eq null}">
								<img src="/static/images/no_profile_image.png"/>
							</c:if>
							<c:if test="${member.groupMemberProfileImagePath ne null}">
								<img src="${member.groupMemberProfileImagePath}"/>						
							</c:if>
						</a>
						<div class="member-info-box">
							<a href="/feed/${member.groupMemberLoginId}" class="member-login-id">${member.groupMemberLoginId}</a>
							<c:if test="${groupOwnerCheck eq true && user.id ne member.groupMemberId}">
								<div class="material-icons-outlined group-member-menu-btn" data-member-id="${member.groupMemberId}">more_horiz</div>
							</c:if>
						</div>
					</div>
				<c:if test="${status.index % 5 eq 4 || status.index eq groupMemberSize-1}">
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