package com.h5tchibook.group.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.alert.bo.GroupCommentAlertBO;
import com.h5tchibook.alert.bo.GroupJoinRequestAlertBO;
import com.h5tchibook.alert.bo.GroupLikeAlertBO;
import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.common.FileManagerService;
import com.h5tchibook.group.dao.GroupDAO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.group.model.GroupMember;
import com.h5tchibook.user.model.User;

@Service
public class GroupBO {
	@Autowired
	private GroupDAO groupDAO;
	@Autowired
	private CheckBO checkBO;
	@Autowired
	private GroupMemberBO groupMemberBO;
	@Autowired
	private FileManagerService fileManagerService;
	@Autowired
	private GroupJoinRequestAlertBO groupJoinRequestAlertBO;
	@Autowired
	private GroupCommentAlertBO groupCommentAlertBO;
	@Autowired
	private GroupLikeAlertBO groupLikeAlertBO;
	
	
	public Map<String,Boolean> createGroup(User user,Group group) {
		Group groupCheck=groupDAO.selectGroupByGroupName(group.getGroupName());
		
		Map<String,Boolean> result=checkBO.createGroupCheckElements(user,groupCheck);
		boolean resultCheck=false;
		//그룹 중복체크 중복 없을 시 true있을 시 false;
		if(result.get("loginCheck")) {
			if(result.get("existGroupCheck")) {
				groupDAO.insertGroup(group);
				//groupMember에 정보를 담아줌(내가 그룹장)
				GroupMember groupMember=GroupMember.builder()
						.groupId(group.getId())
						.groupMemberId(group.getGroupManagerId())
						.build();
				//groupMember생성
				groupMemberBO.createGroupMember(groupMember);
				resultCheck=true;
			}			
		}
		result.put("result",resultCheck);
		return result;
	}
	
	public void editGroupProfileImage(int groupId,MultipartFile file) {
		Group group=groupDAO.selectGroupById(groupId);
		
		if(group.getGroupProfileImagePath()!=null) {
			try {				
				fileManagerService.deleteFile(group.getGroupProfileImagePath());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		String imageUrl=generateImageUrlByFile(group.getGroupName(), file);
		groupDAO.updateGroupProfileImage(groupId, imageUrl);
	}
	
	public void editGroupCoverImage(int groupId,MultipartFile file) {
		Group group=groupDAO.selectGroupById(groupId);
		
		if(group.getGroupProfileImagePath()!=null) {
			try {				
				fileManagerService.deleteFile(group.getGroupProfileImagePath());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		String imageUrl=generateImageUrlByFile(group.getGroupName(), file);
		groupDAO.updateGroupCoverImage(groupId, imageUrl);
	}
	
	public void deleteGroupById(int id) {
		Group group=groupDAO.selectGroupById(id);
		
		deleteGroupImage(group);
		
		groupDAO.deleteGroupById(id);
		groupJoinRequestAlertBO.deleteroupJoinRequestAlertByGroupId(id);
		
	}
	
	private void deleteGroupImage(Group group) {
		if(group!=null) {
			List<String> groupImagePathList=new ArrayList<String>();
			
			if(group.getGroupCoverImagePath()!=null) {
				groupImagePathList.add(group.getGroupCoverImagePath());
			}
			
			if(group.getGroupProfileImagePath()!=null) {
				groupImagePathList.add(group.getGroupProfileImagePath());
			}
			
			if(groupImagePathList.size()!=0) {
				
				for(String imagePath : groupImagePathList) {
					try {
						fileManagerService.deleteFile(imagePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void deleteGroupByGroupManagerId(int groupManagerId) {
		List<Group> groupList=groupDAO.selectGroupListByGroupmanagerId(groupManagerId);
		if(groupList!=null) {
			for(Group group : groupList) {
				deleteGroupImage(group);
				groupJoinRequestAlertBO.deleteroupJoinRequestAlertByGroupId(group.getId());
			}
			groupDAO.deleteGroupByGroupManagerId(groupManagerId);			
		}
	}
	
	public Group getGroupByGroupName(String groupName) {
		return groupDAO.selectGroupByGroupName(groupName);
	}
	
	public Group getGroupById(int id) {
		return groupDAO.selectGroupById(id);
	}
	
	public List<Group> getGroupListByMemberId(int memberId){
		List<GroupMember> groupMemberList=groupMemberBO.getGroupMemberListByGroupMemberId(memberId);
		List<Integer> groupIdList=new ArrayList<Integer>();
		if(groupMemberList!=null) {
			for(GroupMember groupMember : groupMemberList) {
				groupIdList.add(groupMember.getGroupId());
			}
			List<Group> groupList=null;
			if(groupIdList.size()!=0) {
				groupList=groupDAO.selectGroupListByIdList(groupIdList);
			}
			return groupList;
		}
		
		
		return null;
	}
	
	public List<Group> getGroupListByGroupManagerId(int groupManagerId){
		return groupDAO.selectGroupListByGroupmanagerId(groupManagerId);
	}
	
	public List<Integer>getGroupIdListkByGroupManagerId(int groupManagerId){
		List<Group> groupList=groupDAO.selectGroupListByGroupmanagerId(groupManagerId);
		List<Integer> groupIdStack=new Stack<Integer>();
		
		for(Group group:groupList) {
			groupIdStack.add(group.getId());
		}
		
		return groupIdStack;
	}
	
	public List<Group> getGroupListByIdList(List<Integer> idList){
		return groupDAO.selectGroupListByIdList(idList);
	}
	
	private String generateImageUrlByFile(String userLoginId, MultipartFile file) {
		String imageUrl=null;
		if(file!=null) {
			try {
				imageUrl=fileManagerService.saveFile(userLoginId, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imageUrl;
	}
}
