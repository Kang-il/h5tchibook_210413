package com.h5tchibook.friend.bo;

import java.util.ArrayList;	
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.bo.FriendRequestAlertBO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.friend.dao.FriendDAO;
import com.h5tchibook.friend.model.Friend;
import com.h5tchibook.friend.model.FriendView;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class FriendBO {
	@Autowired
	private FriendDAO friendDAO;
	@Autowired
	private UserBO userBO;
	@Autowired
	private FriendRequestAlertBO friendRequestAlertBO;
	
	public void createFriend(int userId, int friendId) {
		
		friendDAO.insertFriend(userId,friendId);
		
		Friend requestFriend = friendDAO.selectFriendByUserIdAndFriendId(userId, friendId);
		Friend checkFriend =friendDAO.selectFriendByUserIdAndFriendId(friendId, userId);
		
		//두 개의 값이 모두 있는 경우는 서로친구
		//requestFriend 만있다면 내가 보낸 친구 요청만 존재
		//후자의 경우에만 알람 생성해주기
		
		//1.내가 친구 추가 했고 상대방의 요청을 기다리는 상황
		if(requestFriend!=null && checkFriend==null) {
			Alert alert=Alert.builder()
						   	 .sendUserId(userId)
							 .receiveUserId(friendId)
							 .alertType(AlertType.FRIEND_REQUEST)
							 .build();
			friendRequestAlertBO.createFriendRequestAlert(alert);
		}
	}
	
	public void deleteFriend(int userId, int friendId) {
		//삭제시 둘의 친구 정보를 모두 삭제한다.
		//양방향 관계이므로 둘다
		friendDAO.deleteFriendByUserIdAndFriendId(userId, friendId);
		friendDAO.deleteFriendByUserIdAndFriendId(friendId, userId);
	}
	
	public void deleteFriendRequest(int userId, int friendId) {
		
		Friend requestFriend = friendDAO.selectFriendByUserIdAndFriendId(userId, friendId);
		
		Friend checkFriend = friendDAO.selectFriendByUserIdAndFriendId(friendId, userId);
		
		if(requestFriend!=null && checkFriend == null ) {
			//friendRequest 제거 후 
			friendDAO.deleteFriendByUserIdAndFriendId(userId, friendId);
			//friendRequestAlert 지워주기
			friendRequestAlertBO.deleteFriendRequestAlert(userId, friendId);
		}
		
	}
	
	public List<FriendView> selectFriendListByUserId(int userId , Integer limit){
		List<Friend> friendList=friendDAO.selectFriendListByUserId(userId);
		List<FriendView> friendViewList=new ArrayList<FriendView>();
			//서로 쌍의 데이터가 존재해야 둘은 친구사이다.
			//단방향인경우 userId -- 친구요청하는 유저 friendId -- 친구요청 받는 유저
		
		if(limit == null) {//limit == null일경우 모든 친구의 값을 가져온다
			
			for(int i=0 ; i < friendList.size();i++) {
				//쌍의 데이터가 userId friendId 에 서로의 값이 들어간 쌍의 데이터가 존재해야 하므로
				//매개변수를 정 반대로 넣어 해당 데이터가 있는지 확인하여 없으면 가져온 list의 해당 값을 지워준다
				
				Friend friend = friendList.get(i);
				Friend friendCheck = friendDAO.selectFriendByUserIdAndFriendId(friend.getFriendId(), userId);
				
				if(friendCheck == null) {//값이 없는경우 -- 친구요청은 했으나 상대방이 받아주지 않았다.
					continue;
				}
				 
				//값이 있는경우 부가적인 유저의 값을 set 해준다.
				User user= userBO.getUserById(friend.getFriendId());
				FriendView friendView=FriendView.builder()
												.id(friend.getId())
												.userId(friend.getUserId())
												.friendId(friend.getFriendId())
												.createdAt(friend.getCreatedAt())
												.updatedAt(friend.getUpdatedAt())
												.friendLoginId(user.getLoginId())
												.friendProfileImagePath(user.getProfileImagePath())
												.build();
				friendViewList.add(friendView);
			}
			
		}else {// limit이 null이 아닐경우
			int count=0;
			//제한적으로 친구의 List를 담는 친구 리스트 선언

			
			for(int i=0 ; i < friendList.size();i++) {
				//count가 limit와 일치하면 break하여 반복문을 벗어난다.
				if(count == limit) {
					break;
				}
				
				Friend friend = friendList.get(i);
				Friend friendCheck = friendDAO.selectFriendByUserIdAndFriendId(friend.getFriendId(), userId);
				
				if(friendCheck == null) {//check 값이 없다면 continue
					continue;
				}
				//값이 있는경우 부가적인 유저의 값 set
				User user= userBO.getUserById(friend.getFriendId());
				FriendView friendView=FriendView.builder()
						.id(friend.getId())
						.userId(friend.getUserId())
						.friendId(friend.getFriendId())
						.createdAt(friend.getCreatedAt())
						.updatedAt(friend.getUpdatedAt())
						.friendLoginId(user.getLoginId())
						.friendProfileImagePath(user.getProfileImagePath())
						.build();
				friendViewList.add(friendView);
				count++;
			}
			
			
		}
		
		return friendViewList;
	}
	
	public Friend getFriendByUserIdAndFriendId(int userId,int friendId) {
		return friendDAO.selectFriendByUserIdAndFriendId(userId, friendId);
	}
	
	public List<Integer> getFriendIdListByUserid(int userId){
		List<Integer> friendIdList= friendDAO.selectFriendIdListByUserId(userId);
		for (int i=0 ; i<friendIdList.size() ; i++) {
			Friend friend=getFriendByUserIdAndFriendId(friendIdList.get(i), userId);
			if(friend == null) {
				friendIdList.remove(i);
			}
		}
		return friendIdList;
	}

}
