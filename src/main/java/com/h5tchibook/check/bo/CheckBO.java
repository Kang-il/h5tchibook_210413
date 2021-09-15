package com.h5tchibook.check.bo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.friend.bo.FriendBO;
import com.h5tchibook.friend.model.Friend;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class CheckBO {

	@Autowired
	private FriendBO friendBO;
	@Autowired
	private UserBO userBO;

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

		result.put("loginCheck", loginCheck);
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
