
function signIn(loginId,password){
	let formData = new FormData();
	formData.append("loginId",loginId);
	formData.append("password",password);
	$.ajax({
		type:'POST'
		,data:formData
		,url:'/user/sign_in'
		,contentType:false
		,processData:false
		,success:function(data){
			if(data.result===true){
				location.href='/timeline/user_timeline_view';
			}else{
					
				if(loginId=='' || password==''){
					$('.sign-in-blank-alert-box').removeClass('d-none');
					return;
				}
					
				$('.sign-in-check-alert-box').removeClass('d-none');
			}	
		}
		,error:function(e){
			alert(e);
		}
	});
}

function setLike(postId){
	if(postId ==''|| postId === undefined || postId === null){
		alert('잘못된 접근입니다.');
		return;
	}
		
	$.ajax({
		type:'POST'
		,url:"/like/set_like/"+postId
		,success:function(data){
			if(data.loginCheck===true){
				if(data.result===true){
					location.reload();
				}
			}else if(data.loginCheck===false){
				location.href="/user/sign_in_view";
			}
		}
		
	});
}

function createComment(postId,comment){
	$.ajax({
		type:'POST'
		,url:'/comment/create_comment'
		,data:{'postId':postId
				,'comment':comment}
		,success:function(data){
			if(data.loginCheck==false){
				location.href="/user/sign_in_view"
			}else{
				location.reload();
			}
		}
		,error:function(e){
			
		}
	});
}

function getCommentList(postId,userId){//포스트 유저 아이디
	$.ajax({
		type:'GET'
		,url:'/comment/get_comment_list'
		,data:{'postId':postId}
		,success:function(data){
			if(data.loginCheck===true){
				if(data.resultCheck===true){
					$('#postCommentItemBox'+postId).empty();
					data.commentList.map((comment) => {
						
						let imageUrl= comment.userProfileImagePath == null ? '/static/images/no_profile_image.png':comment.userProfileImagePath;
						let myId =data.userId;
						let button = '';
						if(myId == userId || myId==comment.userId){// 내가 포스트주인이거나 코멘트 주인일경우
							button='<button type="button" class="material-icons-outlined comment-menu-btn" data-comment-id="'+comment.id+'">more_horiz</button>';
						}
							let html='<div class="post-comment-item">'
										+'<a href="/feed/'+comment.userLoginId+'">'
											+'<img src="'+imageUrl+'"/>'
										+'</a>'
											
										+'<div class="post-comment">'
											+'<div>'+'<a href="/feed/'+comment.userLoginId+'">'+comment.userLoginId+'</a>'+'</div>'
											+'<div>'+comment.comment+'</div>'
										+'</div>'
										+button
									+'</div>';
						
						
						$('#postCommentItemBox'+postId).append(html).trigger("create");		
					});
				}
			}else if(data.loginCheck===false){
				location.href="/user/sign_in_view";
			}
		}
		,error:function(e){
			alert(e);
		}
	});
}
	
function deleteComment(commentId){
	$.ajax({
		type:'POST'
		,data:{'commentId':commentId}
		,url:'/comment/delete_comment'
		,success:function(data){
			if(data.loginCheck === true){
				if(data.deleteResult===true){
					location.reload();
				}else if(data.deleteResult===false){
					alert('삭제 실패 관리자에게 문의하세요');
				}
			}else if(data.loginCheck === false){
				location.href="/user/sign_in_view";
			}				
		}
		,error:function(e){
			alert(e);	
		}
	});
}

function deleteFriend(friendId){
	$.ajax({
		type:'POST'
		,data:{'friendId':friendId}
		,url:'/friend/delete_friend'
		,success:function(data){
			if(data.loginCheck===true){
				if(data.result===true){
					location.reload();
				}
			}else if(data.loginCheck === false){
				location.href="/user/sign_in_view";
			}
		}
		,error:function(e){
			alert(e);
		}
	});
}

function requestFriend(friendId){
	$.ajax({
		type:'POST'
		,data:{'friendId':friendId}
		,url:'/friend/request_friend'
		,success:function(data){
			if(data.loginCheck===true){
				if(data.existUser==true){
					if(data.result===true){
						location.reload();
					}else{
						alert("친구요청 실패 관리자에게 문의하세요");
						return;
					}
				}else{
					alert('유저가 존재하지 않습니다.');
					return;
				}
			}else{
				location.href="/user/sign_in_view";
			}
		}
		,error:function(e){
			alert(e);
		}
	});
}

function cancelFriendRequest(friendId){
	$.ajax({
		type:'POST'
		,data:{'friendId':friendId}
		,url:'/friend/cancel_friend_request'
		,success:function(data){
			if(data.loginCheck===true){
				if(data.existUser===true){
					if(data.result===true){
						location.reload();
					}else{
						alert('친구요청 취소 실패 관리자에게 문의하세요');
					}
				}else{
					alert('유저가 존재하지 않습니다.');
				}
			}else{
				location.href='/user/sign_in_view';
			}
		}
		,error:function(e){
			alert(e);
		}
	});
}

function receiveFriend(friendId){
	$.ajax({
		type:'POST'
		,data:{'friendId':friendId}
		,url:'/friend/receive_friend'
		,success:function(data){
			if(data.loginCheck===true){
				if(data.existUser===true){
					if(data.result===true){
						location.reload();
					}else{
						alert('친구수락 실패 관리자에게 문의하세요');
					}
				}else{
					alert('유저가 존재하지 않습니다.');
				}
			}else{
				return false;	
			}
					
		}
		,error:function(e){
			alert(e);
		}
	});
}
	
function refuseFriend(friendId) {
	$.ajax({
		type: 'POST'
		, data: { 'friendId': friendId }
		, url: '/friend/refuse_friend'
		, success: function(data) {
			if (data.loginCheck === true) {
				if(data.existUser){
					if (data.result === true) {
						location.reload();
					}else{
						alert('친구요청 거절 실패 관리자에게 문의하세요');
						return;
					}
				}
			}else{
				location.href="/user/sign_in_view";
			}

			return false;
		}
		, error: function(e) {
			alert(e);
		}
	});
}

function checkPassword(loginId,password){
	$.ajax({
		type:'POST'
		,data:{'password':password}
		,url:'/profile/check_password'
		,success:function(data){
			if(data.loginCheck===true){
				if(data.result===true){
					location.href='/profile/edit/user_info_view/'+loginId
				}else{
					
					$('.wrong-password-alert').removeClass('d-none');
				}
			}else{
				location.href='/user/sign_in_view';
			}
		}
		,error:function(e){
			alert(e);
		}
	});
}

function setGroupLike(groupId,postId){
	$.ajax({
		type:'POST'
		,data:{'groupId':groupId}
		,url:'/group/like/set_like/'+postId
		,success:function(data){
			if(data.loginCheck==true){
				if(data.result){
					location.reload();
				}else{
					alert('좋아요 실패 관리자에게 문의하세요.');
				}
			}else{
				location.href='/user/sign_in_view';
			}
		}
		,error:function(e){
			alert(e);
		}
	});
}
	
function createGroupComment(postId, groupId, comment) {
	$.ajax({
		type: 'POST'
		, url: '/group/comment/create_comment'
		, data: {
			'postId': postId
			, 'groupId': groupId
			, 'comment': comment
		}
		, success: function(data) {
			//1.loginCheck
			//2.commentBlankCheck
			//3. postIdCheck
			//4. groupIdCheck
			//5. result

			if (data.loginCheck === true) {
				if (data.result === true) {
					location.reload();
				} else {
					if (data.commentBlankCheck === false) {
						alert('내용을 입력해 주세요');
					}
					if (data.postIdCheck === false) {
						alert('잘못된 접근입니다.\n(포스트 아이디 누락)');
					}
					if (data.groupIdCheck === false) {
						alert('잘못된 접근입니다.\n(그룹 아이디 누락)');
					}
				}
			} else {
				location.href = '/user/user_sign_in';
			}
		}
		, error: function(e) {
			alert(e);
			return;
		}
	});
}

function deleteGroupComment(commentId) {
	$.ajax({
		type: 'POST'
		, url: '/group/comment/delete_group_comment'
		, data: { 'commentId': commentId }
		, success: function(data) {
			if (data.loginCheck === true) {
				if (data.groupOwnerCheck === false && data.postOwnerCheck === false && data.commentOwnerCheck === false) {
					alert('삭제 권한이 없습니다.')
					return;
				} else {
					if (data.result === true) {
						location.reload();
					} else {
						alert('삭제 실패 관리자에게 문의하세요');
						return;
					}
				}
			} else {
				location.href = '/user/sign_in_view';
			}
		}
		, error: function(e) {
			alert(e);
		}
	});
}

$(document).ready(function(){
//-------------------sign-up-modal
	$('.sign-up-form-btn').on('click',function(){
		$('.sign-up-modal-section').removeClass('d-none');
	});	
	
	$('.close-sign-up-section').on('click',function(){
		$('.sign-up-modal-section').addClass('d-none');
	});

//--------------------sign-up-view
	//회원가입 란 빈칸이 모두 채워져 있을 경우에만 회원가입 버튼 활성화
	$('#lastName,#firstName,#userId,#userPassword,input[name=sex]').on('input',function(){
		//값 변경시 초기화
		$('#notEnterAllAlert').addClass("d-none");
		$('#idLengthAlert').addClass("d-none");
		$('#passwordValidationCheck').addClass("d-none");
		$('#duplicationAlert').addClass("d-none");
		
		let lastName=$('#lastName').val().trim().length;
		let firstName=$('#firstName').val().trim().length;
		let userId=$('#userId').val().trim().length;
		let userPassword=$('#userPassword').val().trim().length;
		let userSex=$('input[name=sex]:checked').val();
		
		//input에 공백이 없을 경우 버튼 활성화
		if(lastName!=0 && firstName!=0 && userId!=0 
			&& userPassword!=0 && userSex!==undefined ){
			$('.sign-up-btn').attr('disabled',false);
		}else{
			$('.sign-up-btn').attr('disabled',true);
		}
	});
	
	//아이디 중복 검사, 비밀번호 
	$('.sign-up-btn').on('click',function(){
		$('#notEnterAllAlert').addClass("d-none");
		$('#idLengthAlert').addClass("d-none");
		$('#passwordValidationCheck').addClass("d-none");
		$('#duplicationAlert').addClass("d-none");
		
		let lastName=$('#lastName').val().trim();
		let firstName=$('#firstName').val().trim();
		let userId=$('#userId').val().trim();
		let userPassword=$('#userPassword').val().trim();
		let userSex=$('input[name=sex]:checked').val();
		
		//input에 공백이 있는경우 경고문 표시
		if(lastName.length==0 || firstName.length==0 || userId.length==0 
			|| userPassword.length==0 || userSex===undefined ){
			$('#notEnterAllAlert').removeClass('d-none');
			return;
		}
		
		let userName=lastName+firstName;
		
		
		let formData=new FormData();
		formData.append("loginId",userId);
		formData.append("name",userName);
		formData.append("password",userPassword);
		formData.append("sex",userSex);
		
		$.ajax({
			type:'POST'
			,data:formData
			,url:'/user/sign_up_validation'
			,contentType:false
			,processData:false
			,success:function(data){
				if(data.result===false){//유효성 검사 실패 시
					if(data.valid_loginId=='blank' || data.valid_name=='blank'
						||data.valid_password=='blank' || data.valid_sex=='blank'){
						//공백이 들어간 경우
						$('#notEnterAllAlert').removeClass("d-none");
						return;
					}
					
					if(data.valid_loginId == 'userIdLength'){
						//아이디 길이의 불만족
						$('#idLengthAlert').removeClass("d-none");
						return;
					}
					
					if(data.valid_password=='passwordValidation'){
						//비밀번호 정규식에 위배되는 값일 경우
						$('#passwordValidationCheck').removeClass("d-none");
						return;
					}
					
					if(data.valid_duplicateId=='duplicateId'){
						//아이디가 이미 존재하는 경우
						$('#duplicationAlert').removeClass("d-none");
						return;
					}
					
				}else if(data.result===true){
					//아이디 중복도 모두 통과한 경우
					$.ajax({
						type:'POST'
						,data:formData
						,url:'/user/sign_up'
						,contentType:false
						,processData:false
						,success:function(data){
							if(data.result===true){
								alert("회원가입 성공! 로그인 해주세요");
								location.reload();
							}else{
								alert("회원가입 실패 관리자에게 문의하세요");
								return;
							}
						}
						,error:function(e){
							alert(e);
						}
					});
				}
			}
			,error:function(e){
				alert(e);
			}
		});
		
	});
//-----------------------------sign-in-view
	$('#userLoginId,#userLoginPassword').on('input',function(){
		$('.sign-in-check-alert-box').addClass('d-none');
		$('.sign-in-blank-alert-box').addClass('d-none');
		
		let loginId=$('#userLoginId').val().trim().length;
		let password=$('#userLoginPassword').val().trim().length;
		
		if(loginId != 0 && password != 0){
			$('.sign-in-btn').attr('disabled',false);
		}else{
			$('.sign-in-btn').attr('disabled',true);
		}
	});
	
	$('#userLoginId,#userLoginPassword').on('keypress',function(key){
		if(key.keyCode==13){
			if($('.sign-in-btn').attr('disabled') === undefined){
				let loginId=$('#userLoginId').val().trim();
				let password=$('#userLoginPassword').val().trim();
				signIn(loginId,password);
			}
		}
	});
	
	$('.sign-in-btn').on('click',function(){
		let loginId=$('#userLoginId').val().trim();
		let password=$('#userLoginPassword').val().trim();
		
		if(loginId!='' && password!=''){
			signIn(loginId,password);
		}else{
			$('.sign-in-blank-alert-box').removeClass('d-none');
			return;
		}
		
		
	});
	
//---------------------------create-post
		
	$('.post-modal-close-btn').on('click',function(){
		$('.create-post-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	$('.create-post-modal-btn').on('click',function(){
		$('.create-post-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
	});
	
	$('#loadImageInput').on('change',function(e){
	
		let files = e.target.files;
		let filesArr = Array.prototype.slice.call(files);

		filesArr.forEach(function(f) {
			if (!f.type.match("image.*")) {
				alert("확장자는 이미지 확장자만 가능합니다.");
				$("#loadImageInput").val('');
				return;
			}
			sel_file = f;
			let reader = new FileReader();
			reader.onload =function(e){
				$('.create-post-img').attr('src',e.target.result);
				$('.create-post-modal-section').removeClass('d-none');
				$('.create-post-img').removeClass('d-none');
				$('.delete-img-btn').removeClass('d-none');
				$('body').addClass('disabled-scroll');
			}
				reader.readAsDataURL(f);
		});
	});

	$('.create-user-post-btn').on('click',function(){
		let content=$('.create-text-form').val();
		let filePath=$('#loadImageInput').val();
		let disclosureStatus = $('#disclosureStatus option:selected').val();
		let file=null;
		
		if(content==''){
			alert('내용을 입력해 주세요');
			return;
		}
		
		if(disclosureStatus == ''){
			alert('공개범위 설정 오류');
			return;
		}
		
		if(filePath!=''){
			file=$('#loadImageInput')[0].files[0];
		}
		
		let formData =new FormData();
		formData.append("content",content);
		formData.append("disclosureStatus",disclosureStatus);
		formData.append("file",file);
		
		$.ajax({
			type:'POST'
			,url:'/post/create_post'
			,data:formData
			,processData:false
			,contentType:false
			,enctype:'multipart/form-data'
			,success:function(data){
				if(data.loginCheck===true){
					if(data.result===false){
						if(data.valid_content == 'blank'){
							//content값이 정상적으로 넘어가지 않은경우
							alert('글 내용을 채워주세요');
						}
						if(data.valid_disclosureStatus=='blank'){
							alert('포스트 공개여부를 설정하세요');
						}
						if(data.valid_wrong_extension =='file'){
							alert('이미지 파일만 게시 가능합니다.');
						}
						if(data.valid_empty_extension == 'file'){
							alert('잘못된 파일입니다.')
						}
					}else if(data.result===true){
						alert('게시완료!');
						location.reload();
					}
				}else{
					location.href="/user/sign_in_view";
				}
			}
			,error : function(e){
				alert(e);
			}
		});
		
	});
	
	$('.delete-img-btn').on('click',function(){
		$('#loadImageInput').val('');
		$('.create-post-img').attr('src','');
		$('.create-post-img').addClass('d-none');
		$(this).addClass('d-none');
	});
	
	//-------------------group-view
	$('.create-new-group').on('click',function(){
		location.href="/group/create_group_view";
	});
	
	//-----------gnb
	$('.menu-profile-box').on('click',function(){
		let loginId = $(this).data('user-login-id');
		location.href="/feed/"+loginId;
	});
	
	$('#logOut').on('click',function(){
		location.href="/user/sign_out";
	});
	
	$('.nav-menu-btn').on('click',function(){
		$('.modal-window').trigger('click');
		$('.nav-menu-modal').removeClass('d-none');
		$('.modal-window').removeClass('d-none');
	});
	
	$('.modal-window').on('click',function(e){
		if(!$('.nav-menu-modal').has(e.target).length){
			$('.nav-menu-modal').addClass('d-none');
			$('.modal-window').addClass('d-none');
			$('.profile-pic-modal').addClass('d-none');
		}
	});
	
	$('.nav-profile-box').on('click',function(){
		let loginId=$(this).data('user-login-id');
		location.href='/feed/'+loginId;
	});
	
	$('.nav-alert-btn').on('click',function(){
		$('.modal-window').trigger('click');
		$('.nav-alert-modal').removeClass('d-none');
		$('.modal-window').removeClass('d-none');
	});
	
	$('.modal-window').on('click',function(e){
		if(!$('.nav-alert-modal').has(e.target).length){
			$('.nav-alert-modal').addClass('d-none');
			$('.modal-window').addClass('d-none');
		}
	});

	$('.menu-item-description').on('click',function(){
		let loginId=$(this).data('user-login-id');
		location.href='/profile/check_password_view/'+loginId;
	});
	
	//sign out
	$('#logOut').on('click',function(){
		location.href="/user/sign_out";
	});
	
//------------------user_timeline_section

	$('.timeline-user-profile').on('click',function(){
		let userLoginId=$(this).data('user-login-id');	
		location.href='/feed/'+userLoginId;
	});
	
	$('#groupTimeLineBtn').on('click',function(){
		location.href='/timeline/group_timeline_view';	
	});
	
	$('.like-before-btn').on('click',function(){
		let postId = $(this).data('post-id');
		setLike(postId);
		
	});
	
	$('.like-after-btn').on('click',function(){
		let postId = $(this).data('post-id');
		setLike(postId);
	});
	
	$('.post-comment-btn').on('click',function(){
		let postId=$(this).data('post-id');
		$('#commentInput'+postId).focus();
	});
	
	$('.comment-create-btn').on('click',function(){
		let postId=$(this).data('post-id');
		let comment=$('#commentInput'+postId).val();
		if(comment==''){
			alert('내용을 입력해 주세요');
			return;
		}
		createComment(postId,comment);
	});
	
	$('.more-comment').on('click',function(){
		let postId=$(this).data('post-id');
		let postOwnerId=$(this).data('post-owner-id');
		$('#moreComment'+postId).addClass('d-none');
		getCommentList(postId,postOwnerId);
	});
	
	$('.post-item-box').on('click','.comment-menu-btn',function(){
		let commentId = $(this).data('comment-id');

		$('body').addClass('disabled-scroll');
		$('.delete-comment-modal-section').removeClass('d-none');
		$('.delete-comment-btn').data('comment-id',commentId);

	});
	
	$('.delete-comment-btn').on('click',function(){
		let commentId = $(this).data('comment-id');
		$('.dele-coment-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
		deleteComment(commentId);
	});
	
	$('.cancel-delete-comment-modal').on('click',function(){
		$('.delete-comment-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
	//--------------user-feed-photo-section
	$('.feed-photo-item').on('click',function(){
		let postId=$(this).data('post-id');
		location.href="/post/post_detail_view?postId="+postId;
		
	});

	$('.friend-menu-btn').on('click',function(){
		let friendId = $(this).data('friend-id');
		$('.delete-friend-btn').data('friend-id',friendId);
		$('.delete-friend-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
	});
	
	$('.friend-btn').on('click',function(){
		let friendId=$(this).data('feed-owner-id');
		$('.delete-friend-btn').data('friend-id',friendId);
		$('.delete-friend-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
	});
	
	$('.cancel-delete-friend-modal').on('click',function(){
		$('.delete-friend-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
	$('.delete-friend-btn').on('click',function(){
		let friendId = $(this).data('friend-id');
		if(friendId=='' || friendId == undefined){
			alert('잘못된 접근');
			return;
		}
		deleteFriend(friendId);
	});
	
	$('.delete-friend-modal-section').on('click',function(e){
		if(!$('.delete-friend-btn').has(e.target).length){
			$('.delete-friend-modal-section').addClass('d-none');
			$('body').removeClass('disabled-scroll');
		}
	});
	
	$('.progress-friend-btn').on('click',function(){
		// 요청 취소할 것인지 물어봐야됨.
		let feedOwnerId=$(this).data('feed-owner-id');
		if(feedOwnerId == '' || feedOwnerId == undefined){
			alert('잘못된 접근');
			return;
		}
		
		$('.request-friend-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
		$('.request-cancel-friend-btn').data('feed-owner-id',feedOwnerId);
	});
	
	$('.request-cancel-friend-btn').on('click',function(){
		let feedOwnerId=$(this).data('feed-owner-id');
		cancelFriendRequest(feedOwnerId);
	});
	
	$('.request-friend-modal-section').on('click',function(e){
		if(!$('.request-friend-modal-box').has(e.target).length){
			$('.request-friend-modal-section').addClass('d-none');
			$('body').removeClass('disabled-scroll');
		}
	});
	
	$('.cancel-request-friend-modal').on('click',function(){
		$('.request-friend-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
	$('.response-friend-btn').on('click',function(){
		//수락 거절 묻기
		let feedOwnerId=$(this).data('feed-owner-id');
		if(feedOwnerId == '' || feedOwnerId == undefined){
			alert('잘못된 접근');
			return;
		}

		$('.response-friend-modal-section').removeClass('d-none');
		$('.response-friend-action-btn').data('feed-owner-id',feedOwnerId);
		$('.refuse-friend-action-btn').data('feed-owner-id',feedOwnerId);
		$('body').addClass('disabled-scroll');
	});
	
	$('.response-friend-action-btn').on('click',function(){
		let friendId=$(this).data('feed-owner-id');
		receiveFriend(friendId);

	});
	
	$('.refuse-friend-action-btn').on('click',function(){
		let friendId=$(this).data('feed-owner-id');	
		refuseFriend(friendId);
	});
	
	$('.request-friend-btn').on('click',function(){
		//친구요청 취소
		let feedOwnerId=$(this).data('feed-owner-id');
		
		if(feedOwnerId == '' || feedOwnerId == undefined){
			alert('잘못된 접근');
			return;
		}
		
		requestFriend(feedOwnerId);
	});
	
	$('.response-friend-modal-section').on('click',function(e){
		if(!$('.response-friend-modal-box').has(e.target).length){
			$('.response-friend-modal-section').addClass('d-none');
			$('body').removeClass('disabled-scroll');
		}
	});
	
	$('.cancel-response-friend-modal').on('click',function(){
		$('.response-friend-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
//user-profile-section-----------------------

	$('.profile-img-edit-btn').on('click',function(){
		$('.profile-pic-modal').removeClass('d-none');
		$('.modal-window').removeClass('d-none');
	});
	
	$('.show-edit-profile-modal-btn').on('click',function(){
		$('.edit-profile-pic-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
		$('.modal-window').trigger('click');
	});
	
	$('.edit-profile-item').on('click',function(){
		$('.edit-profile-img-input').trigger('click');
	});
	
	$('.edit-profile-img-input').on('input',function(e){
		let files = e.target.files;
		let filesArr = Array.prototype.slice.call(files);

		filesArr.forEach(function(f) {
			if (!f.type.match("image.*")) {
				alert("확장자는 이미지 확장자만 가능합니다.");
				$(".edit-profile-img-input").val($('.edit-profile-img').attr('src'));
				$('.addapt-profile-img').attr('disabled',true);
				return;
			}
			sel_file = f;
			let reader = new FileReader();
			reader.onload =function(e){
				$('.edit-profile-img').attr('src',e.target.result);
				
				let profileImage=$('.profile-img-value-box').val();
				let changeImg=$('.edit-profile-img').attr('src');

				if(profileImage==changeImg){
					$('.addapt-profile-img').attr('disabled',true);
				}else{
					$('.addapt-profile-img').attr('disabled',false);
				}
				
			}
				reader.readAsDataURL(f);
		});
	});
	
	$('.change-basic-img').on('click',function(){
		$('.edit-profile-img').attr('src','/static/images/no_profile_image.png');
		$('.edit-profile-img-input').val('');
		let profileImage=$('.profile-img-value-box').val();
		let changeImg=$('.edit-profile-img').attr('src');
		if(profileImage==changeImg){
			$('.addapt-profile-img').attr('disabled',true);
		}else{
			$('.addapt-profile-img').attr('disabled',false);
		}
	});
	
	$('.profile-modal-close-btn').on('click',function(){
		$('.edit-profile-pic-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
	$('.addapt-profile-img').on('click',function(){
		let filePath=$('.edit-profile-img-input').val();
		let feedOwnerLoginId=$('.show-edit-profile-modal-btn').data('feed-owner-login-id');
		let file=null;
		
		if(filePath!=''){
			file=$('.edit-profile-img-input')[0].files[0];
		}
		
		let formData=new FormData();
		formData.append("file",file);
		
		$.ajax({
			type:'POST'
			,url:'/profile/update_profile_img/'+feedOwnerLoginId
			,data:formData
			,processData:false
			,contentType:false
			,enctype:'multipart/form-data'
			,success:function(data){
				if(data.existUser===true){
					if(data.loginCheck===true){
						if(data.result===true){
							location.reload();
						}else{
							
							if(data.valid_wrong_extension===true){
								alert('올바른 파일 확장자가 아닙니다.');
							}
							
							if(data.valid_empty_extension===true){
								alert('올바른 파일 형식이 아닙니다.');
							}
							
							alert("프로필 변경 실패 관리자에게 문의하세요");
						}
					}else{
						location.href="/user/sign_in_view";
					}
				}else{
					location.href="/feed/"+feedOwnerLoginId;
				}
			}
			,error:function(e){
				alert(e);
			}
		});
		
	});
	
	$('.profile-background-edit-btn').on('click',function(){
		$('.edit-profile-background-section').removeClass('d-none');
	});
	
	$('.change-background-btn').on('click',function(){
		$('.change-background-img-input').trigger('click');
	});
	
	$('.change-background-img-input').on('change',function(){
		$('.edit-profile-background-section').removeClass('d-none');
	});
	
	$('.basic-background-img-btn').on('click',function(){
		const BASIC_IMAGE_PATH="/static/images/no_background_image.jpg";
		$('.profile-background').attr('src',BASIC_IMAGE_PATH);
		let originalBackgroundImg=$('.original-background-img').val();
		
		if(originalBackgroundImg==BASIC_IMAGE_PATH){
			$('.change-background-action-btn').attr('disabled',true);
		}else{
			$('.change-background-action-btn').attr('disabled',false);
		}
		
		$('.change-background-img-input').val('');
	});
	
	$('.change-background-close-btn').on('click',function(){
		$('.edit-profile-background-section').addClass('d-none');
		$('.change-background-img-input').val('');
		let originalBackgroundImg=$('.original-background-img').val();
		$('.profile-background').attr('src',originalBackgroundImg);
	});
	
	
	
	$('.change-background-img-input').on('input',function(e){
		let files = e.target.files;
		let filesArr = Array.prototype.slice.call(files);

		filesArr.forEach( f => {
			if (!f.type.match("image.*")) {
				alert("확장자는 이미지 확장자만 가능합니다.");
				$(".profile-background").attr('src',$('.original-background-img').val());
				$('.change-background-action-btn').attr('disabled',true);
				return;
			}
			
			sel_file = f;
			let reader = new FileReader();
			reader.onload =function(e){
				$('.profile-background').attr('src',e.target.result);
				
				let originalBackgroundImg=$('.original-background-img').val();
				let changeImg=$('.profile-background').attr('src');

				if(originalBackgroundImg==changeImg){
					$('.change-background-action-btn').attr('disabled',true);
				}else{
					$('.change-background-action-btn').attr('disabled',false);
				}
				
			}
				reader.readAsDataURL(f);
		});
	});
	
	$('.change-background-action-btn').on('click',function(){
		let filePath=$('.change-background-img-input').val();
		let feedOwnerLoginId=$('.profile-background-edit-btn').data('feed-owner-login-id');
		let file=null;
		
		if(filePath!=''){
			file=$('.change-background-img-input')[0].files[0];

		}
		
		let formData=new FormData();
		formData.append("file",file);
		
		$.ajax({
			type:'POST'
			,url:'/profile/set_background_img/'+feedOwnerLoginId
			,data:formData
			,processData:false
			,contentType:false
			,enctype:"multipart/form-data"
			,success:function(data){
				if(data.existUser===true){
					if(data.loginCheck===true){
						if(data.result===true){
							location.reload();
						}else{
							
							if(data.valid_wrong_extension===true){
								alert('올바른 파일 확장자가 아닙니다.');
							}
							
							if(data.valid_empty_extension===true){
								alert('올바른 파일 형식이 아닙니다.');
							}
							
							alert("프로필 변경 실패 관리자에게 문의하세요");
						}
					}else{
						location.href="/user/sign_in_view";
					}
				}else{
					alert('잘못된 접근');
					location.href="/feed/"+feedOwnerLoginId;
				}
			}
			,error:function(e){
				alert(e);
			}
		});
		
		
	});

	$('.introduce-modal-close-btn').on('click',function(){
		$('.edit-profile-introduce-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
	$('.add-introduce-btn').on('click',function(){
		$('.edit-profile-introduce-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
		let userIntroduce=$(this).data('user-introduce');
		$('.introduce-text-area').val(userIntroduce);
	});
	
	$('.introduce-text-area').on('input',function(){
		let userIntroduce=$('.add-introduce-btn').data('user-introduce');
		let revisedIntroduce=$(this).val().trim();
		
		if(userIntroduce == revisedIntroduce){
			$('.introduce-submit-btn').attr('disabled',true);
		}else{
			$('.introduce-submit-btn').attr('disabled',false);
		}
	});
	
	$('.introduce-submit-btn').on('click',function(){
		let revisedIntroduce=$('.introduce-text-area').val().trim();
		let feedOwnerLoginId=$('.add-introduce-btn').data('feed-owner-login-id');
		let formData =new FormData();
		//revisedIntroduce 가 공백이 아닐경우에만 formData 에 append해주기
		if(revisedIntroduce !=''){
			formData.append('introduce',revisedIntroduce);
		}

		$.ajax({
			type:'POST'
			,data:formData
			,url:'/profile/set_introduce/'+feedOwnerLoginId
			,contentType:false
			,processData:false
			,success:function(data){
				if(data.existUser===true){
					if(data.loginCheck===true && data.feedOwnerCheck===true){
						if(data.result===true){						
							location.reload();
						}else{
							alert("소개 업데이트 실패 관리자에게 문의하세요.");
						}
					}else{
						location.href="/user/sign_in_view";
					}
				}else{
					alert("잘못된 접근");
					location.href="/feed/"+feedOwnerLoginId;
				}
			}
			,error:function(e){
				alert(e);
			}
		});
	});
	
	$('.edit-profile-btn').on('click',function(){
		let feedOwnerLoginId=$(this).data('feed-owner-login-id');
		location.href='/profile/check_password_view/'+feedOwnerLoginId;
	});
	
	//profile-check_profile_view =-==========================================
	
	$('.profile-password-submit-btn').on('click',function(){
		let password=$('#editProfilePassword').val();
		let loginId=$(this).data('user-login-id');
		
		if(password==''){
			$('.blank-password-alert').removeClass('d-none');
		}
		
		if(loginId!=undefined || loginId != ''){
			checkPassword(loginId,password)
		}
		
	});
	
	$('#editProfilePassword').on('input',function(){
		$('.blank-password-alert').addClass('d-none');
		$('.wrong-password-alert').addClass('d-none');
		let password=$(this).val();
		if(password==''){
			$('.profile-password-submit-btn').attr('disabled',true);
		}else{
			$('.profile-password-submit-btn').attr('disabled',false);
		}
	});
	
	$('#editProfilePassword').on('keypress',function(key){
		if(key.keyCode==13){
			if($('.profile-password-submit-btn').attr('disabled') === undefined || $(this).val()!=''){
				let password=$(this).val().trim();
				let loginId=$('.profile-password-submit-btn').data('user-login-id');
				
				if(loginId!=undefined || loginId != ''){
					checkPassword(loginId,password)
				}
				
			}
		}
	});
	
	$('.edit-user-info-btn').on('click',function(){
		let userLoginId= $('#editLoginId').val();;
		let password= $('#editPassword').val();;
		let sex = $('input[name=editSex]:checked').val();
		let formData=new FormData();
		
		if(userLoginId==''){
			$('.blank_login_id').removeClass('d-none');
		}
		
		if(sex==undefined || sex ==''){
			alert('성별을 입력해 주세요');
			return;
		}
		
		if(password!=''){
			formData.append('password',password);
		}
		
		if(userLoginId!=''){
			formData.append('loginId',userLoginId);
		}
		
		formData.append('sex',sex);
		
		$.ajax({
			type:'POST'
			,data:formData
			,contentType:false
			,processData:false
			,url:'/profile/edit/validate_edit_user_info'
			,success:function(data){
				if(data.loginCheck===true){
					if(data.result===true){
						// 결과 부합한다면 수정 할 것
						$.ajax({
							type:'POST'
							,data:formData
							,url:'/profile/edit/set_user_profile'
							,contentType:false
							,processData:false
							,success:function(data){
								if(data.loginCheck===true){
									if(data.result===true){
										alert("개인정보 수정 성공! 다시 로그인 해주세요");
										location.href="/user/sign_out";
										
									}
								}else{
									location.href="/user/sign_in_view";
								}
							}
							,error:function(e){
								alert(e);
							}
						});
					}else{
						//1.이전 아이디가 동일하지 않다면
							
							
						if(data.previousLoginIdCheck===false){
							if(data.loginIdLengthCheck===false){
								//아이디 길이조건이 부합하지 않다면
								$('.too-short-login-id').removeClass('d-none');
								return;								
							}
							if(data.duplicateLoginIdCheck===false){
								//아이디 중복확인 결과를 통과하지 못했다면
								$('.duplicate-login-id').removeClass('d-none');
								return;
							}
						}
						
						if(data.previousPasswordCheck===true){
							//이전 비밀번호와 동일하다면.
							$('.previous-password').removeClass('d-none');
							return;
						}
						
						if(data.passwordRegexCheck===false){
							//비밀번호가 정규식 조건에 부합하지 않는다면.
							$('.no-validate-password-condition').removeClass('d-none');
							return;							
						}
					}
				}else{
					location.href="/user/sign_in_view";
				}
			}
			,error:function(e){
				alert(e);
			}
		});
	});
//-------------------------create group section
	$('.create-group-btn').on('click',function(){
		let groupName=$('.group-name-input').val().trim();
		
		$.ajax({
			type:'POST'
			,url:'/group/create_group'
			,data:{'groupName':groupName}
			,success:function(data){
				if(data.loginCheck===true){
					if(data.existGroupCheck===false){
						$('.duplicate-group-name-alert').removeClass('d-none');
						return;						
					}
					
					if(data.result===true){
						alert('그룹 생성완료!');
						location.href="/timeline/group_timeline_view";
					}else{
						alert('그룹생성 실패! 관리자에게 문의하세요');
						return;
					}
				}else{
					location.href="/user/sign_in_view";
				}
			}
			,error:function(e){
				alert(e);
			}
		});
	});
	
	$('.group-name-input').on('input',function(){
		$('.duplicate-group-name-alert').addClass('d-none');
		let groupName=$(this).val().trim();
		if( groupName.length!=0 ){
			$('.create-group-btn').attr('disabled',false);
		}else{
			$('.create-group-btn').attr('disabled',true);
		}
	});
	
	$('#editLoginId, #editPassword').on('input',function(){
		$('.blank-login-id').addClass('d-none');
		$('.too-short-login-id').addClass('d-none');
		$('.duplicate-login-id').addClass('d-none');
		$('.previous-password').addClass('d-none');
		$('.no-validate-password-condition').addClass('d-none');
	});
	//group -timeline-section
	$('.group-timeline-group-item').on('click',function(){
		let groupName=$(this).data('group-name');
		location.href="/feed/group/"+groupName;
	});
	
	//group create post section
	
	$('.create-group-post-modal-btn').on('click',function(){
		$('.create-group-post-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
	});
	
	$('.group-post-modal-close-btn').on('click',function(){
		$('.create-group-post-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
	$('.create-group-text-form').on('input',function(){
		let text=$(this).val();
		if(text=='' || text == undefined){
			$('.create-group-post-btn').attr('disabled',true);
		}else{
			$('.create-group-post-btn').attr('disabled',false);
		}
	});
	
	$('.create-group-post-photo-btn').on('click',function(){
		$('#loadGroupImageInput').trigger('click');
	});
	
	$('#loadGroupImageInput').on('input',function(e){
		let files = e.target.files;
		let filesArr = Array.prototype.slice.call(files);

		filesArr.forEach(function(f) {
			if (!f.type.match("image.*")) {
				alert("확장자는 이미지 확장자만 가능합니다.");
				$("#loadGroupImageInput").val('');
				return;
			}
			sel_file = f;
			let reader = new FileReader();
			reader.onload =function(e){
				$('.create-group-post-img').attr('src',e.target.result);
				$('.create-group-post-image-box').removeClass('d-none');
				$('.create-group-post-img').removeClass('d-none');
				$('.delete-group-img-btn').removeClass('d-none');
				$('.create-group-post-modal-section').removeClass('d-none');
				$('body').addClass('disabled-scroll');
			}
				reader.readAsDataURL(f);
		});
	});
	
	$('.delete-group-img-btn').on('click',function(){
		$('.create-group-post-img').attr('src','');
		$('.create-group-post-image-box').addClass('d-none');
		$('.create-group-post-img').addClass('d-none');
		$('.delete-group-img-btn').addClass('d-none');
	});
	
	$('.create-group-post-btn').on('click',function(){
		let filePath=$('#loadGroupImageInput').val();
		let content = $('.create-group-text-form').val();
		let groupId=$(this).data('group-id');

		let file=null;
		
		if(filePath!=''){
			file=$('#loadGroupImageInput')[0].files[0];

		}
		
		let formData=new FormData();
		formData.append("file",file);
		
		if(content==''){
			alert('내용을 입력해 주세요');
			return;
		}
		
		formData.append('content',content);
		
		if(groupId == '' || groupId == undefined){
			alert('잘못된 접근');
			return;
		}
		formData.append('groupId',groupId)
		
		
		$.ajax({
			type:'POST'
			,url:'/group/post/create_post'
			,data:formData
			,processData:false
			,contentType:false
			,enctype:"multipart/form-data"
			,success:function(data){
				if(data.loginCheck===true){
					if(data.result===true){
						location.reload();
					}else{
						if(data.valid_content == 'blank'){
							//content값이 정상적으로 넘어가지 않은경우
							alert('글 내용을 채워주세요');
							return;
						}
						if(data.valid_wrong_extension =='file'){
							alert('이미지 파일만 게시 가능합니다.');
							return;
						}
						if(data.valid_empty_extension == 'file'){
							alert('잘못된 파일입니다.');
							return;
						}
						if(data.groupId == 'blank'){
							alert('잘못된 접근입니다.');
							return;
						}
					}
				}else{
					location.href='/user/sign_in_view';
				}
			}
			,error:function(e){
				alert(e);
			}
		});
		
		
	});
	
	
	$('.group-post-comment-btn').on('click',function(){
		let postId=$(this).data('post-id');
		$('#groupCommentInput'+postId).focus();
	});
	
	$('.group-comment-create-btn').on('click',function(){
		let postId=$(this).data('post-id');
		let groupId=$(this).data('group-id');
		let comment=$('#groupCommentInput'+postId).val();
		
		if(postId == undefined ){
			alert('포스트 아이디 누락');
		}
		
		if(groupId == undefined){
			alert('그룹 아이디 누락');
		}
		
		if(comment==''){
			alert('내용을 입력해 주세요.');
			return;
		}
		
		createGroupComment(postId,groupId,comment);
		
		
		
	});
	
	

	
	$('.group-more-comment').on('click',function(){
		let postId = $(this).data('post-id');
		let groupId=$(this).data('group-id');
		let userId=$(this).data('user-id');
		$.ajax({
			type:'POST'
			,data:{'postId': postId}
			,url:'/group/comment/get_group_comment_list'
			,success:function(data){
				if(data.loginCheck===true){
					if(data.result===true){
						let commentList=data.groupCommentViewList;
						let groupIdList=data.groupIdList;
						$('#groupPostCommentBox'+postId).empty();
						commentList.forEach(comment=>{
								//private int id;
								//private int userId;
								//private int postId;
								//private String comment;
								//private Date createdAt;
								//private Date updatedAt;
								//private String userLoginId;
								//private String userProfileImagePath;
							let profileImagePath = comment.userProfileImagePath == null ? 
							'/static/images/no_profile_image.png' : comment.userProfileImagePath;
							
							let button='';
							
							// 내가 그룹 매니저 인경우 댓글 삭제가 가능하도록 메뉴 창 노출
							for(let i=0; i< groupIdList.length;i++){
								if(groupIdList[i]==groupId){
									button='<button type="button" class="material-icons-outlined group-comment-menu-btn">more_horiz</button>';
									break;
								}
								if(groupIdList[i]!=groupId){
									continue;
								}
							}
							
							// 내가 작성자인 경우 댓글 삭제가 가능하도록 메뉴 창 노출
							if(userId==comment.memberId){
								button='<button type="button" class="material-icons-outlined group-comment-menu-btn" data-comment-id="'+comment.id+'">more_horiz</button>';
							}
							
							let html='<div class="group-post-comment-item">'
										+'<a href="/feed/"'+comment.userLoginId+'>'
											+'<img src="'+profileImagePath+'"/>'
										+'</a>'
										+'<div class="group-post-comment">'
											+'<div><a href="/feed/"'+comment.userLoginId+'>'+comment.userLoginId+'</a></div>'
											+'<div>'+comment.comment+'</div>'
										+'</div>'
										+button
									+'</div>';
									
							$('#groupPostCommentBox'+postId).append(html);
								
						});
						
					}else{
						alert('댓글 불러오기 실패 관리자에게 문의하세요');
						return;
					}
				}else{
					location.href='/user/sign_in_view';
				}
			}
			,error:function(e){
				alert(e);
			}
		});
	});
	
	$('.group-post-item-box').on('click','.group-comment-menu-btn',function(){
		let commentId=$(this).data('comment-id');
		$('.delete-group-comment-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
		$('.delete-group-comment-btn').data('comment-id',commentId);	
	});
	
	$('.cancel-group-delete-comment-modal').on('click',function(){
		$('.delete-group-comment-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
	$('.delete-group-comment-btn').on('click',function(){
		let commentId=$(this).data('comment-id');
		deleteGroupComment(commentId)
	});
	
	// groupLike Action
	$('.group-like-before-btn').on('click',function(){
		let groupId=$(this).data('group-id');
		let postId=$(this).data('post-id');
		setGroupLike(groupId,postId);
	});
	
	$('.group-like-after-btn').on('click',function(){
		let groupId=$(this).data('group-id');
		let postId=$(this).data('post-id');
		setGroupLike(groupId,postId);
	});
	
	$('.join-group-btn').on('click',function(){
		let groupId=$(this).data('group-id');
		alert(groupId);
		$.ajax({
			type:'POST'
			,url:'/group/request_join_group'
			,data:{'groupId':groupId}
			,success:function(data){
				if(data.loginCheck===true){
					if(data.result===true){
						location.reload();
					}else{
						alert('그룹가입 요청 실패 관리자에게 문의하세요');
					}
				}else{
					location.href='/user/sign_in_view';
				}
			}
			,error: function(e){
				alert(e);
			}
		});
	});
	
	$('.progress-join-group-btn').on('click',function(){
		let groupId=$(this).data('group-id');
		$('.request-join-group-cancel-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
		$('.request-join-group-cancel-btn').data('group-id',groupId);
	});
	
	$('.request-join-group-cancel-modal').on('click',function(){
		$('.request-join-group-cancel-modal-section').addClass('d-none');
		$('body').removeClass('disabled-scroll');
	});
	
	$('.request-join-group-cancel-btn').on('click',function(){
		let groupId=$(this).data('group-id');
		$.ajax({
			type:'POST'
			,data:{'groupId':groupId}
			,url:'/group/cancel_join_request'
			,success:function(data){
				if(data.loginCheck===true){
					if(data.result===true){
						location.reload();
					}else{
						alert('그룹가입요청 취소 실패! 관리자에게 문의하세요');
						return;
					}
				}else{
					location.href='/user/sign_in_view';
				}
			}
			,error:function(e){
				alert(e);
			}
			
		});
	});
	
	// ----- post-detail-comment
	$('.comment-input-btn').on('click',function(){
		let postId=$(this).data('post-id');
		let comment=$('.post-detail-input').val();
		
		if(postId==undefined){
			alert('잘못된 접근입니다.');
			return;
		}
		
		if(comment==''){
			alert('내용을 입력하세요');
			return;
		}
		
		createComment(postId,comment);
	});
	
	$('.post-detail-comment-menu-btn').on('click',function(){
		let commentId=$(this).data('comment-id');
		$('.delete-comment-modal-section').removeClass('d-none');
		$('body').addClass('disabled-scroll');
		$('.delete-comment-btn').data('comment-id',commentId);
	});
	
	$('.post-detail-comment-btn').on('click',function(){
		$('.post-detail-input').focus();
	});
	
	$('.group-photo-item').on('click',function(){
		let postId=$(this).data('post-id');
		location.href="/group/post/group_post_detail_view?postId="+postId;
	});
	
	$('.group-post-detail-comment-btn').on('click',function(){
		$('.group-post-detail-input').focus();
	});
	
	$('.group-comment-input-btn').on('click',function(){
		let postId=$(this).data('post-id');
		let groupId=$(this).data('group-id');
		let comment=$('.group-post-detail-input').val();

		if(comment==''){
			alert('내용을 입력하세요');
			return;
		}
		
		if(postId == undefined || groupId == undefined){
			alert('잘못된 접근입니다.');
			return;
		}
		createGroupComment(postId, groupId, comment);
	});
	
	$('.detail-group-like-before-btn, .detail-group-like-after-btn').on('click',function(){
		let postId=$(this).data('post-id');
		let groupId=$(this).data('group-id');
		
		if(postId == undefined || groupId==undefined){
			alert('잘못된 접근입니다.');
			return;
		}
		
		setGroupLike(groupId,postId);
	});
	
	
	
	
	
});