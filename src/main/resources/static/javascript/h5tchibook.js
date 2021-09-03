

$(document).ready(function(){
//-------------------sign-up-modal
	$('.sign-up-form-btn').on('click',function(){
		$('.sign-up-modal-section').removeClass('d-none');
	});	
	$('.close-sign-up-section').on('click',function(){
		$('.sign-up-modal-section').addClass('d-none');
	});
	
});