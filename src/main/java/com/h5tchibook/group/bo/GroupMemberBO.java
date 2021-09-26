package com.h5tchibook.group.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.group.dao.GroupMemberDAO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.group.model.GroupMember;
import com.h5tchibook.group.model.GroupMemberView;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class GroupMemberBO {
	
	@Autowired
	private UserBO userBO;
	@Autowired
	private GroupMemberDAO groupMemberDAO;
	@Autowired
	private CheckBO checkBO;
	
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public int createGroupMember(GroupMember groupMember) {
		return groupMemberDAO.insertGroupMember(groupMember);
	}
	public Map<String,Boolean> deleteGroupMember(User user, Group group , int memberId) {
		
		GroupMember groupMember=groupMemberDAO.selectGroupMemberByGroupIdAndGroupMeberId(group.getId(), memberId);
		
		Map<String,Boolean> result=checkBO.deleteGroupMemberCheckElements(user,group,groupMember);
		boolean validationCheck=true;
		boolean resultCheck=false;
		
		for(String key : result.keySet()) {
			if(!result.get(key)) {
				//유효성 체크 하나라도 통과 못할 시 validationCheck에 false 넣어주고 for 문 빠져나옴
				validationCheck=false;
				break;
			}
		}
		
		//for문 을 돌렸는데도 vaidationCheck가 true를 유지하고있다면 그룹 멤버를 지워준다.
		if(validationCheck) {
			int row=groupMemberDAO.deleteGroupMemberByGroupIdAndGroupMemberId(group.getId(), memberId);
			if(row!=0) {
				resultCheck=true;
			}
		}
		
		result.put("result", resultCheck);
		return result;
	}
	
	
	public List<GroupMember> getGroupMemberListByGroupMemberId(int memberId){
		return groupMemberDAO.selectGroupMemberListByGroupMemberId(memberId);
	}
	
	public int getGroupMemberCountByGroupId(int groupId) {
		return groupMemberDAO.selectGroupMemberCountByGroupId(groupId);
	}
	public List<GroupMemberView> getGroupMemberViewListByGroupId(int groupId , String category){
		Integer limit=null;
		boolean validationCheck=false;
		if(category==null) {
			limit=9;
			validationCheck=true;
		}else if(category.equals("member")){
			limit=null;
			validationCheck=true;
		}
		
		//limit 설정 
		//feed 에서는 9명만 노출 해주면 되고
		//멤버 카테고리를 선택할 시 모든 친구 리스트를 노출해 줘야 함.
		if(validationCheck) { //카테고리가 photo일 경우엔 멤버 가져올 필요 없음
			List<GroupMember> groupMemberList= groupMemberDAO.selectGroupMemberListByGroupId(groupId,limit);
			List<Integer> userIdList=new ArrayList<Integer>();
			List<User> userList=null;
			
			List<GroupMemberView> groupMemberViewList=new ArrayList<GroupMemberView>();
			
			//groupMember에서 유저의 아이디만 받는다.
			for(GroupMember groupMember : groupMemberList) {
				userIdList.add(groupMember.getGroupMemberId());
			}
			//리스트 사이즈가 0이 아니다== 그룹의 멤버가 있다.
			if(userIdList.size()!=0) {
				// 리스트를 이용해 유저의 리스트를 받아온다.
				userList=userBO.getUserListByIdList(userIdList);
			}
			
			//그룹 멤버 리스트와 유저리스트를 이중for 문으로 돌려 각 정보의 일치여부를
			//유저의 아이디 - 그룹멤버의 아이디를 기준으로 한다.
			for(GroupMember groupMember : groupMemberList) {
				
				for(User user : userList) {
					//그룹의 멤버아이디와 유저의 아이디가 일치하지 않는다면 아래 문을 실행하지 않는다.
					if(groupMember.getGroupMemberId() != user.getId()) {
						continue;
					}
					
					// 유저의 아이디와 그룹멤버아이디가 일치한 경우
					// groupMember 와 User 객체를 이용하여 GroupMemberView 객체를 가공하여 
					// groupMemberView 리스트에 담아준다.
					GroupMemberView groupMemberView=GroupMemberView
													.builder()
													.id(groupMember.getId())
													.groupMemberId(groupMember.getGroupMemberId())
													.groupId(groupMember.getGroupId())
													.createdAt(groupMember.getCreatedAt())
													.groupMemberLoginId(user.getLoginId())
													.groupMemberProfileImagePath(user.getProfileImagePath())
													.build();
					
					groupMemberViewList.add(groupMemberView);
					//groupMemberViewList에 한 개의 값을 추가했다면 
					//적어도 해당 멤버에서 User를 찾을 필요가 없으므로 for문에서 벗어남
					break;
				}
	
			}
			
			return groupMemberViewList;
		}
		
		return null;
	}
}
