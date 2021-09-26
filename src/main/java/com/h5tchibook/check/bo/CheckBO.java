package com.h5tchibook.check.bo;

import java.util.HashMap;	
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.comment.model.GroupComment;
import com.h5tchibook.common.EncryptUtils;
import com.h5tchibook.friend.bo.FriendBO;
import com.h5tchibook.friend.model.Friend;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.group.model.GroupJoinRequest;
import com.h5tchibook.group.model.GroupMember;
import com.h5tchibook.post.model.GroupPost;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class CheckBO {

	@Autowired
	private FriendBO friendBO;
	@Autowired
	private UserBO userBO;

	private final String PASSWORD_REGEX="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";

	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public Map<String, Boolean> feedCheckElements(User user, User feedOwner) {
		Map<String, Boolean> result = new HashMap<>();

		// 피드 오너 체크 ::
		boolean feedOwnerCheck = false;
		// 서로 친구인지 체크
		boolean friendCheck = false;
		// 친구 요청중인지 체크
		boolean requestFriendCheck = false;
		// 친구요청을 받았는지 체크
		boolean receiveFriendCheck = false;

		boolean existUser = existUser(feedOwner);
		boolean loginCheck = loginCheck(user);
		
		result.put("loginCheck", loginCheck);
		
		if (!existUser) {
			result.put("existUser", existUser);
			// 피드 오너가 존재하지 않다면 바로 리턴해주기
			// 아래 코드를 실행할 필요가 없음
			return result;
		}

		if (loginCheck) {

			feedOwnerCheck = feedOwnerCheck(user, feedOwner);

			// 1.이 유저랑 나랑 친구인지 확인
			// 2.친구가 맞다면 friendCheck true 담아주기
			// 3.친구가 아니라면 내가 친구요청중인지 확인 -- requestFriendCheck
			// 4.내가 친구요청중이 아니라면 상대방이 나에게 요청중인지 확인 -- receiveFriendCheck
			// 5.둘 중 친구요청을 한 적이 없다면 request receive 둘다 false -->
			// 뷰상에서 둘 다 false면(request,receive) 친구요청버튼 띄워주기
			Friend myFriend = friendBO.getFriendByUserIdAndFriendId(user.getId(), feedOwner.getId());
			Friend oppositeFriend = friendBO.getFriendByUserIdAndFriendId(feedOwner.getId(), user.getId());

			friendCheck = friendCheck(myFriend, oppositeFriend);
			requestFriendCheck = requestFriendCheck(myFriend, oppositeFriend);
			receiveFriendCheck = receiveFriendCheck(myFriend, oppositeFriend);

		}

		result.put("existUser", existUser);
		result.put("friendCheck", friendCheck);
		result.put("requestFriendCheck", requestFriendCheck);
		result.put("receiveFriendCheck", receiveFriendCheck);
		result.put("feedOwnerCheck", feedOwnerCheck);

		return result;
	}

	// 친구 삭제 시 필요한 체크 시 필요한 체크항목
	public Map<String, Boolean> friendDeleteCheckElemnts(User user, int friendId) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		boolean loginCheck = loginCheck(user);
		boolean friendCheck = false;
		if (loginCheck) {// 로그인 되어있을 경우에만 실행
			Friend friend = friendBO.getFriendByUserIdAndFriendId(user.getId(), friendId);
			Friend oppositeFriend = friendBO.getFriendByUserIdAndFriendId(friendId, user.getId());
			friendCheck = friendCheck(friend, oppositeFriend);
		}
		result.put("friendCheck", friendCheck);
		result.put("loginCheck", loginCheck);
		return result;
	}

	// 친구 요청/취소/친구수락 시 필요한 체크 항목
	public Map<String, Boolean> friendRequestAndResponseCheckElements(User user, int friendId) {

		Map<String, Boolean> result = new HashMap<String, Boolean>();

		boolean loginCheck = loginCheck(user);
		boolean existUser = existUser(userBO.getUserById(friendId));
		boolean friendCheck = false;
		boolean requestFriendCheck = false;
		boolean receiveFriendCheck = false;

		if (loginCheck && existUser) {

			Friend myFriend = friendBO.getFriendByUserIdAndFriendId(user.getId(), friendId);
			Friend oppositeFriend = friendBO.getFriendByUserIdAndFriendId(friendId, user.getId());

			requestFriendCheck = requestFriendCheck(myFriend, oppositeFriend);
			receiveFriendCheck = receiveFriendCheck(myFriend, oppositeFriend);

		}

		result.put("loginCheck", loginCheck);
		result.put("existUser", existUser);
		result.put("friendCheck", friendCheck);
		result.put("requestFriendCheck", requestFriendCheck);
		result.put("receiveFriendCheck", receiveFriendCheck);

		return result;
	}
	
	public Map<String,Boolean> updateUserProfileInfoCheckElements(User user, String feedOwnerLoginId){
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		User feedOwner=userBO.getUserByLoginId(feedOwnerLoginId);
		
		boolean loginCheck=loginCheck(user);
		boolean feedOwnerCheck =feedOwnerCheck(user , feedOwner);
		boolean existUser=existUser(user);
		
		result.put("loginCheck", loginCheck);
		result.put("feedOwnerCheck",feedOwnerCheck);
		result.put("existUser", existUser);
		
		return result;
	}
	
	public Map<String,Boolean> validateEditUserInfoCheckElements(User user,User editedUser){
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		//로그인 되어있는지 체크
		boolean loginCheck=loginCheck(user);
		//현재 로그인 아이디와 이전 로그인아이디와 동일한지 여부 체크
		boolean previousLoginIdCheck=previousLoginIdCheck(user.getLoginId(),editedUser.getLoginId());
		//로그인 아이디가 바뀌었다면 아이디 길이 조건 체크
		boolean loginIdLengthCheck=false;
		//아이디의 중복확인 체크
		boolean duplicateLoginIdCheck=false;
		//이전 비밀번호와 동일여부 체크
		boolean previousPasswordCheck=false;
		//패스워드가 조건에 부함한지 체크
		boolean passwordRegexCheck=false;
		//이전의 아이디와 입력받은 아이디가 동일하거나
		//동일하지않다면 아이디 길이가 조건에 맞고 중복체크값이 true인 경우
		boolean loginIdCheck=false;
		
		boolean passwordCheck=false;
		
		boolean resultCheck=false;
		
		//기존 아이디와 동일하지 않다면
		if(!previousLoginIdCheck) {
			//바꾸고자 하는 아이디의 길이 체크
			loginIdLengthCheck=loginIdLengthCheck(editedUser.getLoginId());
			//바꾸고자하는 아이디길이가 조건에 부합한다면
			if(loginIdLengthCheck) {
				//바꾸려는 아이디 중복체크
				duplicateLoginIdCheck=duplicateUserLoginIdCheck(editedUser.getLoginId());
			}
		}
		
		//입력받은 비밀번호가 있다면
		if(editedUser.getPassword()!=null) {
			//입력받은 비밀번호를 해싱하여 본래 비밀번호와 비교한다.
			String encryptedPassword=EncryptUtils.mb5(editedUser.getPassword());
			
			previousPasswordCheck=previousPasswordCheck(user.getPassword(),encryptedPassword);
			
			if(!previousPasswordCheck) {
				passwordRegexCheck=Pattern.matches(PASSWORD_REGEX, editedUser.getPassword());
				logger.debug("::::::::::::::::passwordRegexCheck"+passwordRegexCheck);
			}
		}
		
		//기존 로그인 아이디와 같거나 다르다면 로그인아이디의 길이와 중복확인 조건을 모두 만족해야 한다.
		if(previousLoginIdCheck) {
			loginIdCheck=true;
		}else {
			if(loginIdLengthCheck&&duplicateLoginIdCheck) {
				loginIdCheck=true;
			}
		}
		
		//애초에 비밀번호는 동일값을 받지 않아야 하므로
		//기존 비밀번호와 동일한 값을 받은경우엔 validate조건에 맞지 않는다
		//기존 비밀번호와 동일하지 않고 받은 패스워드가 있다면 정규식을 만족해야한다 또는 넘어온 비밀번호가 없어야함
		if((!previousPasswordCheck && passwordRegexCheck) || editedUser.getPassword()==null) {
			passwordCheck=true;
		}
		//비밀번호와 패스워드가 모든 조건을 만족한다면 결과 true
		if(loginIdCheck&&passwordCheck) {
			resultCheck=true;
		}
		
		result.put("loginCheck", loginCheck);
		result.put("previousLoginIdCheck", previousLoginIdCheck);
		result.put("loginIdLengthCheck", loginIdLengthCheck);
		result.put("duplicateLoginIdCheck", duplicateLoginIdCheck);
		result.put("previousPasswordCheck", previousPasswordCheck);
		result.put("passwordRegexCheck", passwordRegexCheck);
		result.put("result",resultCheck);
		
		return result;
	}
	
	public Map<String,Object> groupFeedCheckElements(User user, Group group,String category){
		Map<String,Object> result=new HashMap<>();
		boolean loginCheck=loginCheck(user);
		boolean existGroupCheck=false;
		boolean groupOwnerCheck=false;
		
		if(loginCheck) {
			existGroupCheck=group==null?  false : true;
			
			if(existGroupCheck) {
				groupOwnerCheck=groupOwner(user.getId(),group.getGroupManagerId());
			}
			
		}
		
		String userView=null;
		if(category==null){
			userView="group/group_feed_section";
		}else if(category.equals("member")) {
			userView="group/group_feed_member_section";
		}else if(category.equals("photo")) {
			userView="group/group_feed_photo_section";
		}
		
		result.put("userView", userView);
		result.put("loginCheck",loginCheck);
		result.put("existGroupCheck", existGroupCheck);
		result.put("groupOwnerCheck",groupOwnerCheck);
		
		return result;
	}
	
	public Map<String,Boolean> createGroupCheckElements(User user , Group group) {
		Map<String,Boolean> result=new HashMap<String,Boolean>();	
		boolean existGroupCheck= group==null ? true : false ;
		boolean loginCheck = loginCheck(user);
		result.put("existGroupCheck", existGroupCheck);
		result.put("loginCheck", loginCheck);
		return result;
	}
	
	public Map<String,Boolean> createCommentCheckElements(User user,GroupComment comment){
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		
		boolean loginCheck=loginCheck(user);
		boolean commentBlankCheck=commentBlankCheck(comment);
		boolean postIdCheck=comment.getPostId()==0? false : true;
		boolean groupIdCheck=comment.getGroupId()==0? false : true;
		
		result.put("loginCheck",loginCheck);
		result.put("commentBlankCheck", commentBlankCheck);
		result.put("postIdCheck", postIdCheck);
		result.put("groupIdCheck", groupIdCheck);
		
		return result;
	}
	
	public Map<String,Boolean> deleteGroupCommentCheckElements(User user, GroupComment groupComment, GroupPost groupPost, Group group){
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		boolean loginCheck=loginCheck(user);
		boolean groupOwnerCheck=groupOwnerCheck(user,group);
		boolean postOwnerCheck=groupPostOwnerCheck(user,groupPost);
		boolean commentOwnerCheck=groupCommentOwnerCheck(user,groupComment);
		
		result.put("loginCheck", loginCheck);
		result.put("groupOwnerCheck", groupOwnerCheck);
		result.put("postOwnerCheck", postOwnerCheck);
		result.put("commentOwnerCheck", commentOwnerCheck);
		
		return result;
	}
	
	public Map<String,Boolean> createGroupJoinRequestCheckElements(User user,Group group,GroupJoinRequest groupJoinRequest){
		boolean loginCheck=loginCheck(user);
		boolean existGroupCheck=existGroupCheck(group);
		boolean existGroupJoinRequestCheck=existGroupJoinRequest(groupJoinRequest);
		
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		
		result.put("loginCheck", loginCheck);
		result.put("existGroupCheck",existGroupCheck);
		result.put("existGroupJoinRequestCheck", existGroupJoinRequestCheck);
		return result;
	}
	
	public Map<String,Boolean> deleteGroupMemberCheckElements(User user, Group group, GroupMember groupMember){
		boolean loginCheck=loginCheck(user);
		boolean existGroupCheck=existGroupCheck(group);
		boolean groupOwnerCheck=groupOwner(user.getId(),group.getGroupManagerId());
		boolean existGroupMemberCheck=existGroupMember(groupMember);
		
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		result.put("loginCheck", loginCheck);
		result.put("existGroupCheck",existGroupCheck);
		result.put("groupOwnerCheck",groupOwnerCheck);
		result.put("existGroupMemberCheck", existGroupMemberCheck);
		return result;
	}
	
	public Map<String,Boolean> deleteGroupJoinRequestCheckElements(User user, Group group,GroupJoinRequest groupJoinRequest){
		boolean loginCheck=loginCheck(user);
		boolean existGroupCheck=existGroupCheck(group);
		boolean existGroupJoinRequestCheck=existGroupJoinRequest(groupJoinRequest);
		
		//GroupJoinRequest 가 false 를 반환받았을 경우 있다는 뜻
		if(existGroupJoinRequestCheck==false) {
			//validationCheck하기 편하도록 true를 담아줌
			existGroupJoinRequestCheck=true;
		}
		
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		result.put("loginCheck", loginCheck);
		result.put("existGroupCheck",existGroupCheck);
		result.put("existGroupJoinRequest", existGroupJoinRequestCheck);
		
		return result;
	}
	
	public Map<String,Boolean> responseJoinGroupCheckElements(User user,Group group,GroupJoinRequest groupJoinRequest,int userId){
		boolean loginCheck=loginCheck(user);
		boolean existGroupCheck=existGroupCheck(group);
		boolean existGroupJoinRequestCheck=existGroupJoinRequest(groupJoinRequest);
		boolean groupOwnerCheck=groupOwner(user.getId(), group.getGroupManagerId());
		boolean existUserCheck=existUser(userBO.getUserById(userId));
		if(existGroupJoinRequestCheck==false) {
			existGroupJoinRequestCheck=true;
		}
		
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		
		result.put("loginCheck", loginCheck);
		result.put("existGroupCheck",existGroupCheck);
		result.put("existGroupJoinRequest", existGroupJoinRequestCheck);
		result.put("groupOwnerCheck", groupOwnerCheck);
		result.put("existUser", existUserCheck);
		return result;
	}
	
	private boolean existGroupMember(GroupMember groupMember) {
		boolean existGroupMemberCheck=false;
		if(groupMember!=null) {
			existGroupMemberCheck=true;
		}
		return existGroupMemberCheck;
	}
	
	private boolean existGroupJoinRequest(GroupJoinRequest groupJoinRequest) {
		boolean existGroupJoinRequest=true;
		if(groupJoinRequest!=null) {
			existGroupJoinRequest=false;
		}
		return existGroupJoinRequest;
	}

	private boolean existGroupCheck(Group group) {
		boolean existGroupCheck=false;
		
		if(group!=null) {
			existGroupCheck=true;
		}
		
		return existGroupCheck;
	}
	
	private boolean groupCommentOwnerCheck(User user,GroupComment comment) {
		boolean commentOwnerCheck=false;
		if(user.getId()==comment.getMemberId()) {
			commentOwnerCheck=true;
		}
		return commentOwnerCheck;
	}
	
	private boolean groupPostOwnerCheck(User user, GroupPost post) {
		boolean postOwnerCheck=false;
		if(user.getId()== post.getGroupMemberId()) {
			postOwnerCheck=true;
		}
		return postOwnerCheck;
	}
	
	private boolean groupOwnerCheck(User user , Group group) {
		boolean groupOwnerCheck=false;
		if(user.getId()==group.getGroupManagerId()) {
			groupOwnerCheck=true;
		}
		return groupOwnerCheck;
	}
	
	private boolean commentBlankCheck(GroupComment groupComment) {
		boolean commentBlankCheck=false;
		
		String comment=groupComment.getComment();
		if(!comment.equals("") && comment!=null) {
			commentBlankCheck=true;
		}
		
		return commentBlankCheck;
	}
	
	private boolean groupOwner(int id, int groupManagerId) {
		boolean groupOwnerCheck=false;
		
		if(id==groupManagerId) {
			groupOwnerCheck=true;
		}
		
		return groupOwnerCheck;
	}
	
	
	private boolean previousPasswordCheck(String password , String changedPassword) {
		boolean previousPasswordCheck=false;
		if(password.equals(changedPassword)) {
			previousPasswordCheck=true;
		}
		return previousPasswordCheck;
	}
	
	private boolean duplicateUserLoginIdCheck(String loginId) {
		boolean duplicateUserLoginIdCheck=false;
		User user=userBO.getUserByLoginId(loginId);
		if(user==null) {
			duplicateUserLoginIdCheck=true;
		}
		return duplicateUserLoginIdCheck;
	}
	
	private boolean previousLoginIdCheck(String loginId, String changedLoginId) {
		boolean previousLoginIdCheck=false;
		if(loginId.equals(changedLoginId)) {
			previousLoginIdCheck=true;
		}
		return previousLoginIdCheck;
	}
	
	private boolean loginIdLengthCheck(String loginId) {
		boolean userLoginIdLengthCheck=false;
		logger.debug("::::loginIdLength"+loginId.length());
		if(loginId.length()>=4 && loginId.length()<=15) {
			userLoginIdLengthCheck=true;
		}
		return userLoginIdLengthCheck;
	}
	
	private boolean loginCheck(User user) {
		boolean loginCheck = false;
		if (user != null) {
			loginCheck = true;
		}
		return loginCheck;
	}

	private boolean existUser(User user) {
		boolean existUser = false;
		if (user != null) {
			existUser = true;
		}
		return existUser;
	}

	private boolean feedOwnerCheck(User user, User feedOwner) {
		boolean feedOwnerCheck = false;
		if (feedOwner.getId() == user.getId()) {
			feedOwnerCheck = true;
		}
		return feedOwnerCheck;
	}

	private boolean friendCheck(Friend myFriend, Friend oppositeFriend) {
		boolean friendCheck = false;
		if (myFriend != null && oppositeFriend != null) {
			// 두 값이 모두 존재한다면 둘은 서로 친구이다.
			friendCheck = true;
		}
		return friendCheck;
	}

	private boolean requestFriendCheck(Friend myFriend, Friend oppositeFriend) {
		boolean requestFriendCheck = false;
		if (myFriend != null && oppositeFriend == null) {
			requestFriendCheck = true;
		}
		return requestFriendCheck;
	}

	private boolean receiveFriendCheck(Friend myFriend, Friend oppositeFriend) {
		boolean receiveFriendCheck = false;
		if (myFriend == null && oppositeFriend != null) {
			receiveFriendCheck = true;
		}
		return receiveFriendCheck;
	}

}
