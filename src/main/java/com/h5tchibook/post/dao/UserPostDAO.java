package com.h5tchibook.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.post.model.Post;

@Repository
public interface UserPostDAO {
	public int insertUserPost(Post userpost);
	public Post selectPostById(@Param("id") int id);
	public List<Post> selectPostListByUserId(@Param("userId") int userId,@Param("disclosureStatus")String disclosureStatus);
	public List<Post> selectPostListOnlyPhotoByUserId(@Param("userId") int userId
														,@Param("limit") Integer limit
														,@Param("disclosureStatus")String disclosureStatus);
	public List<Post> selectPostByPostIdList(@Param("postIdList")List<Integer> postIdList);
	
}
