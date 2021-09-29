package com.h5tchibook.alert.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.LikeAlertDAO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.LikeAlert;

@Service
public class LikeAlertBO {
	@Autowired
	private LikeAlertDAO likeAlertDAO;
	@Autowired
	private AlertBO alertBO;
	
	
	public void createLikeAlert(Alert alert,int postId,int likeId) {
		alertBO.createAlert(alert);
		
		LikeAlert likeAlert=LikeAlert.builder()
									 .alertId(alert.getId())
									 .postId(postId)
									 .likeId(likeId)
									 .build();
		
		likeAlertDAO.insertLikeAlert(likeAlert);
	}
	
	public void deleteLikeAlertByLikeId(int likeId) {
		//라이크 아이디로 해당 알람정보 가져오기
		LikeAlert alert = likeAlertDAO.selectLikeAlertByLikeId(likeId);
		//라이크 정보가 있는 경우 LikeAlert와 그 짝을 이루는 Alert도 지워준다
		if(alert!=null) {
			likeAlertDAO.deleteLikeAlertByLikeId(likeId);
			alertBO.deleteAlertById(alert.getAlertId());			
		}
	}
	
	public void deleteLikeAlertByPostId(int postId) {
		List<LikeAlert> alertList=likeAlertDAO.selectLikeAlertListByPostId(postId);
		if(alertList != null) {
			List<Integer> alertIdList=new ArrayList<Integer>();
			for(LikeAlert alert:alertList) {
				alertIdList.add(alert.getAlertId());
			}
			
			if(alertIdList.size()!=0) {
				alertBO.deleteAlertByIdList(alertIdList);
				likeAlertDAO.deleteLikeAlertByPostId(postId);	
			}
		}
	}
	
	public LikeAlert getLikeAlertByAlertId(int alertId) {
		return likeAlertDAO.selectLikeAlertByAlertId(alertId);
	}
}
