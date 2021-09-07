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
			<input type="text" class="form-control" placeholder="성(姓)" id="lastName"/>
			<input type="text" class="form-control" placeholder="이름(성은 제외)" id="firstName"/>
		</div>
		<input type="text" class="form-control" placeholder="아이디" id="userId"/>
		
		<div class="d-none" id="duplicationAlert">
			<span class="validation-text">중복된 아이디 입니다.</span>
		</div>
		
		<div class="d-none" id="idLengthAlert">
			<span class="validation-text">아이디가 너무 짧습니다.(4자 이상)</span>
		</div>
		
		<input type="password" class="form-control" placeholder="비밀번호" id="userPassword"/>
		
		<div class="justify-content-center d-none" id="passwordValidationCheck">
			<span class="validation-text text-center  ml-0">비밀번호는 영문, 숫자, 특수기호가 적어도 1개 이상씩 포함된 <br> 8~20자의 비밀번호여야 합니다.</span>
		</div>
		
		<p class="sex-title">성별</p> 
		<div class="sex-radio-box">
			<div class="sex-item"><label for="female">여성</label><input type="radio" name="sex" id="female" value="female"></div>
			<div class="sex-item"><label for="male">남성</label> <input type="radio" name="sex" id="male" value="male"></div>
		</div>
		
		<div class="d-none" id="notEnterAllAlert">
			<span class="validation-text">빈칸을 모두 채워 주세요.</span>
		</div>
		
		<div class="sign-up-btn-box">
			<button type="button" class="btn sign-up-btn" disabled="disabled"> 가입하기 </button>
		</div>
	</div>
</div>