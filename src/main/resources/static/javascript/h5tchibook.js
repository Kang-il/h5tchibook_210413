
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
	
	//---------------create-post
		
	$('.post-modal-close-btn').on('click',function(){
		$('.create-post-modal-section').addClass('d-none');
	});
	$('.create-post-modal-btn').on('click',function(){
		$('.create-post-modal-section').removeClass('d-none');
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
		}
			reader.readAsDataURL(f);
		});
	});
	
	$('.create-user-post-btn').on('click',function(){
		let content=$('.create-text-form').val();
		if(content==''){
			alert('내용을 입력해 주세요');
		}
		
		let disclosureStatus = $('#disclosureStatus option:selected').val();

		if(disclosureStatus == ''){
			alert('공개범위 설정 오류');
		}
		
		let filePath=$('#loadImageInput').val();
		
		let file=null;
		
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
	
	//-------------------group-view
	$('.create-new-group').on('click',function(){
		location.href="/group/create_group_view";
	});
	
});