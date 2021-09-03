<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="sign-up-modal-section d-none">
	<div class="sign-up-modal-box">
		<div class="sign-up-title">
			<h3>가입하기</h3>
			<span class="material-icons-outlined close-sign-up-section">close</span>
		</div>
		<span class="sign-up-description">빠르고 쉽습니다.</span>
		<hr class="mt-3 mb-2">
		
		<div class="name-form">
			<input type="text" class="form-control" placeholder="성(姓)" name="lastName"/>
			<input type="text" class="form-control" placeholder="이름(성은 제외)" name="firstName"/>
		</div>
		<input type="text" class="form-control" placeholder="아이디" name="userId"/>
		<input type="text" class="form-control" placeholder="비밀번호" name="userPassword"/>
		<p class="sex-title">성별</p> 
		<div class="sex-radio-box">
			<div class="sex-item"><label for="female">여성</label><input type="radio" name="sex" id="female" value="female" checked></div>
			<div class="sex-item"><label for="male">남성</label> <input type="radio" name="sex" id="male" value="male"></div>
		</div>
		<div class="sign-up-btn-box">
			<button type="button" class="btn sign-up-btn"> 가입하기 </button>
		</div>
	</div>
</div>
    