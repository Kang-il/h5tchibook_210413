<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="create-post-modal-section">
	<div class="create-post-modal-box">
	
		<div class="modal-title-box">
			<h4>게시물 만들기</h4>
			<button type="button" class="material-icons-outlined post-modal-close-btn">close</button>
		</div>
		
		<hr>
		
		<div class="create-post-profile-box">
			<img src="/static/images/dummy_profile.jpg" alt="프로필" />
			<div class="create-post-select-box">
				<span>h5tchi</span>
				<select id="disclosureStatus" class="form-control"> 
					<option value="public" class="disclosure-option">전체공개</option>
					<option value="friend"  class="disclosure-option">친구공개</option>
					<option value="private"  class="disclosure-option">비공개</option>
				</select>
			</div>
		</div>
			
		<div class="create-post-content-box">
			<img src="/static/images/dummy_profile.jpg" class="create-post-img d-none" alt="포스트"/>
			<textarea class="form-control create-text-form" placeholder="h5tchi님, 무슨 생각을 하고 계신가요?" rows="6"></textarea>
		</div>
			
		<div class="add-photo-box">
			<div>게시물에 추가</div>

			<label for="loadImageInput" class="load-post-photo-btn">
				<span class="material-icons">photo_library</span>사진
			</label>
				
			<input type="file" id="loadImageInput" class="create-file-input d-none"/>
		</div>
			
		<div>
			<button type="button" class="btn create-user-post-btn">게시</button>
		</div>
		
	</div>
</div>