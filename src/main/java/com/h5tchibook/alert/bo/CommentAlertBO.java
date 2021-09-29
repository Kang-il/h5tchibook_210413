package com.h5tchibook.alert.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.CommentAlertDAO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.CommentAlert;

@Service
public class CommentAlertBO {
	@Autowired
	private CommentAlertDAO commentAlertDAO;
	@Autowired
	private AlertBO alertBO;
	
	public void createCommentAlert(Alert alert, int commentId , int postId ) {
		
		alertBO.createAlert(alert);
		
		CommentAlert commentAlert=CommentAlert.builder()
											  .alertId(alert.getId())
											  .commentId(commentId)
											  .postId(postId)
											  .build();
		
		commentAlertDAO.insertCommentAlert(commentAlert);
	}
	
	public void deleteCommentAlertByCommentId(int commentId) {
		CommentAlert alert=commentAlertDAO.selectCommentAlertByCommentId(commentId);
		
		if(alert!=null) {
			commentAlertDAO.deleteCommentAlertByCommentId(commentId);
			alertBO.deleteAlertById(alert.getAlertId());
		}
	}
	
	public void deleteCommentAlertByPostId(int postId) {
		List<CommentAlert> alertList=commentAlertDAO.selectCommentAlertByPostId(postId);
		if(alertList!=null) {
			List<Integer> alertIdList=new ArrayList<Integer>();
			
			for(CommentAlert alert : alertList) {
				alertIdList.add(alert.getAlertId());
			}
			
			if(alertIdList.size()!=0) {
				alertBO.deleteAlertByIdList(alertIdList);
				commentAlertDAO.deleteCommentAlertByPostId(postId);
			}
		}
	}
	
	public CommentAlert getCommentAlertByAlertId(int alertId) {
		return commentAlertDAO.selectCommentAlertByAlertId(alertId);
	}
	
}
