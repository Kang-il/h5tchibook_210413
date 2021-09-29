package com.h5tchibook.like;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.like.bo.LikeBO;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.Post;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/like")
public class LikeRestController {

	@Autowired
	private LikeBO likeBO;
	@Autowired
	private UserPostBO userPostBO;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/set_like/{postId}")
	public Map<String, Boolean> setLike(@PathVariable("postId") int postId, HttpServletRequest request) {

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		Post post = userPostBO.getPostById(postId);

		Map<String, Boolean> result = likeBO.setLike(user, post);

		return result;
	}
}
