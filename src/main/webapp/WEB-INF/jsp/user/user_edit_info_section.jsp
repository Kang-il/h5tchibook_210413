<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  

<section class="edit-user-info-section">

	<div class="edit-user-info-box">
		<h4 class="section-title">개인정보 수정</h4>
		<hr class="m-0">
		<div class="user-img-box">
			<div class="user-img-item">
				<c:if test="${user.profileImagePath eq null}">
					<img class="edit-user-info-profile-img" src="/static/images/no_profile_image.png"/>
				</c:if>
				<c:if test="${user.profileImagePath ne null}">
					<img class="edit-user-info-profile-img" src="${user.profileImagePath}"/>
				</c:if>
			</div>
		</div>
		<h3 class="user-login-id">${user.loginId}</h3> 
		<div>
		<hr class="mb-3 mt-4">
			<label for="editLoginId" class="input-title">아이디</label>
			<input type="text" id="editLoginId" class="form-control" value="${user.loginId}">
		</div>
		
		<div class="blank-login-id text-danger d-none">아이디를 입력해 주세요.</div> 
		<div class="too-short-login-id text-danger d-none">아이디가 너무 짧습니다.(4 ~ 15자)</div>
		<div class="duplicate-login-id text-danger d-none">아이디가 중복됩니다.</div> 

		<div>
			<label for=editPassword class="input-title">비밀번호</label>
			<input type="password" id="editPassword" class="form-control">
		</div>
		
		<div class="previous-password  text-center text-danger d-none ml-0">변경하려는 비밀번호와 이전비밀번호가 동일합니다.</div>
		<div class="no-validate-password-condition text-center text-danger d-none ml-0">비밀번호는 영문, 숫자, 특수기호가 적어도 1개 이상씩 포함된 <br> 8~20자의 비밀번호여야 합니다.</div>
			
		<div class="input-title">성별</div>
		<div class="sex-radio-box">
			<div class="radio-items">
				<c:if test="${user.sex eq'FEMALE'}">
					<div class="sex-item"><label for="editFemale">여성</label><input type="radio" name="editSex" id="editFemale" value="female" checked></div>
					<div class="sex-item"><label for="editMale">남성</label> <input type="radio" name="editSex" id="editMale" value="male"></div>
				</c:if>
				<c:if test="${user.sex eq'MALE'}">
					<div class="sex-item"><label for="editFemale">여성</label><input type="radio" name="editSex" id="editFemale" value="female"></div>
					<div class="sex-item"><label for="editMale">남성</label> <input type="radio" name="editSex" id="editMale" value="male" checked></div>
				</c:if>
				
			</div>
		</div>
		
		<button type="button" class="edit-user-info-btn btn">수정하기</button>
	</div>
	
</section>
<footer>
	<jsp:include page="../include/footer.jsp"></jsp:include> 
</footer>  