<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<div class="title">
		<div class="title-item">
			<img src="/static/images/h5tchibook_typo_logo.png" alt="로고이미지" class="logo"/>
			<div class="logo-description">H5tchibook에서 전 세계에 있는 친구, 가족 지인들과 함께 이야기를 나눠보세요.</div>
		</div>
	</div>
	<div class="sign-in-form">
		<div class="form-box">
			<div>
				<input type="text" placeholder="아이디" class="sign-in-input form-control " id="userLoginId"/>
				<input type="password" placeholder="비밀번호" class="sign-in-input form-control " id="userLoginPassword"/>
				
				<div class="sign-in-check-alert-box d-none">
					<span class="text-danger check-alert">아이디나 비밀번호를 확인 해 주세요.</span>
				</div>
				
				<div class="sign-in-blank-alert-box d-none">
					<span class="text-danger check-alert">빈칸을 모두 채워주세요.</span>
				</div>
				
				<button type="button" class="btn sign-in-btn" disabled="disabled">로그인</button>
				<a href="#" class="find-password-link">비밀번호를 잊으셨나요?</a>
				<hr>
				<div class="form-btn-box">
					<button type="button" class="btn sign-up-form-btn">새 계정 만들기</button>
				</div>
			</div>
		</div>
	</div>