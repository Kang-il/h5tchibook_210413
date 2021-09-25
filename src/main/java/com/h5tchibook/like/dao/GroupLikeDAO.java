package com.h5tchibook.like.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.like.model.GroupLike;

@Repository
public interface GroupLikeDAO {
	public List<GroupLike> selectGroupLikeListByPostIdList(@Param("postIdList") List<Integer> postIdList);
}
