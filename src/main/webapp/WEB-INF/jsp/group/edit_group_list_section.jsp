<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<section>
	<jsp:include page="../include/user_group_info_section.jsp"/>
	
	<section class="edit-group-list-section">
		<div class="edit-group-list-box">
			<h5 class="text-center group-list-title">내 그룹 목록</h5>
			
			<hr>
		
			<div class="group-list-box">
				<c:forEach var="group" items="${groupList}">
					<div class="group-item" data-group-name="${group.groupName}">
						<div class="group-list-info-box">
							<div class="group-profile-box">
								<c:choose>
									<c:when test="${group.groupProfileImagePath eq null}">
										<img src="/static/images/no_profile_image.png"/>
									</c:when>
									<c:otherwise>
										<img src="${group.groupProfileImagePath}"/>
									</c:otherwise>
								</c:choose>
								
							</div>
							<div class="group-name">${group.groupName}</div>
						</div>
						<div class="created-group-date">Created : <fmt:formatDate value="${group.createdAt}" pattern="yyyy-MM-dd"/></div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
</section>  