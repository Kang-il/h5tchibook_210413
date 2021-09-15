package com.h5tchibook.friend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.friend.model.Friend;

@Repository
public interface FriendDAO {
	public void insertFriend(@Param("userId") int userId 
						    ,@Param("friendId") int friendId
						    );
	
	public void deleteFriendByUserIdAndFriendId(@Param("userId") int userId
											   ,@Param("friendId") int friendId
											   );
	
	public List<Friend> selectFriendListByUserId(@Param("userId") int userId);
	
	public Friend selectFriendByUserIdAndFriendId(@Param("userId")int userId
												 ,@Param("friendId")int friendId
												 );
	public List<Integer> selectFriendIdListByUserId(@Param("userId") int userId);
}
