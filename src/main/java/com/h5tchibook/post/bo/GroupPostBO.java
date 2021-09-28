package com.h5tchibook.post.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.comment.bo.GroupCommentBO;
import com.h5tchibook.comment.model.GroupCommentView;
import com.h5tchibook.common.FileManagerService;
import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.bo.GroupMemberBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.group.model.GroupMember;
import com.h5tchibook.like.bo.GroupLikeBO;
import com.h5tchibook.like.model.GroupLikeView;
import com.h5tchibook.post.dao.GroupPostDAO;
import com.h5tchibook.post.model.ContentType;
import com.h5tchibook.post.model.GroupPost;
import com.h5tchibook.post.model.GroupPostView;
import com.h5tchibook.timeline.bo.GroupTimeLineBO;
import com.h5tchibook.timeline.model.GroupTimeLine;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class GroupPostBO {
	@Autowired
	private GroupPostDAO groupPostDAO;
	@Autowired
	private GroupLikeBO groupLikeBO;
	@Autowired
	private GroupCommentBO groupCommentBO;
	@Autowired
	private UserBO userBO;
	@Autowired
	private FileManagerService fileManagerService;
	@Autowired
	private GroupBO groupBO;
	@Autowired
	private GroupTimeLineBO groupTimeLineBO;
	@Autowired
	private GroupMemberBO groupMemberBO;
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public boolean createGroupPost(String userLoginId,GroupPost groupPost ,MultipartFile file) {
		boolean resultCheck=false;
		
		//imageUrl을 담아줌
		String imageUrl=generateImageUrlByFile(userLoginId,file);
		ContentType contentType=null;
		
		//imageUrl 이 null이라면 Text 타입의 컨텐츠
		if(imageUrl==null) {
			contentType=ContentType.TEXT;
		}else {
		//null이 아니라면 Photo 타입의 컨텐츠
			contentType=ContentType.PHOTO;
		}
		
		groupPost.setContentType(contentType);
		groupPost.setContentPath(imageUrl);
		
		int row=groupPostDAO.insertGroupPost(groupPost);
		
		if(row!=0) {
			resultCheck=true;
			
			GroupTimeLine groupTimeLine=GroupTimeLine.builder()
													 .groupId(groupPost.getGroupId())
													 .postId(groupPost.getId())
													 .userId(groupPost.getGroupMemberId())
													 .build();
			
			groupTimeLineBO.createGroupTimeLine(groupTimeLine);
		}
		
		return resultCheck;
	}
	
	public void deleteGroupPostByGroupId(int groupId) {
		List<GroupPost> postList=groupPostDAO.selectGroupPostListByGroupId(groupId);
		
		if(postList!=null) {
			
			List<Integer> postIdList=new ArrayList<Integer>();
			
			groupPostDAO.deleteGroupPostByGroupId(groupId);
			
			for(GroupPost post: postList) {
				if(post.getContentPath()!=null) {
					try {
						fileManagerService.deleteFile(post.getContentPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			for(GroupPost post : postList) {
				postIdList.add(post.getId());
			}
			
			if(postIdList.size()!=0) {
				groupCommentBO.deleteCommentByPostIdList(postIdList);
				groupLikeBO.deleteGroupLikeByPostIdList(postIdList);
			}
		}
	}
	
	public void deleteGroupPostByMemberId(int groupId ,int memberId) {
		List<GroupPost> groupPostList=groupPostDAO.selectGroupPostListByGroupIdAndMemberId(groupId, memberId);
		groupPostDAO.deleteGroupPostByGroupIdAndMemberId(groupId,memberId);
		if(groupPostList!=null) {
			for(GroupPost post : groupPostList) {
				if(post.getContentPath() != null) {
					try {
						fileManagerService.deleteFile(post.getContentPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			List<Integer> groupIdList=new ArrayList<Integer>();
			for(GroupPost groupPost:groupPostList) {
				groupIdList.add(groupPost.getId());
			}
			groupCommentBO.deleteCommentByPostIdList(groupIdList);
			groupLikeBO.deleteGroupLikeByPostIdList(groupIdList);
		}
	}
	
	public void deleteGroupPostById(int postId) {
		GroupPost post=groupPostDAO.selectGroupPostById(postId);
		groupPostDAO.deleteGroupPostById(postId);
		if(post.getContentPath()!=null) {
			try {
				fileManagerService.deleteFile(post.getContentPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		groupCommentBO.deleteCommentByPostId(postId);
		groupLikeBO.deleteGroupLikeByPostId(postId);
	}
	
	public GroupPost getGroupPostById(int id) {
		return groupPostDAO.selectGroupPostById(id);
	}
	
	public List<GroupPost> getGroupPostListOnlyPhtoTypeByGroupId(int groupId,String category){
		Integer limit=null;
		List<GroupPost> groupPostList=null;
		boolean validationCheck=false;
		
		if(category==null) {
			limit=9;
			validationCheck=true;
		}else if(category.equals("photo")) {
			validationCheck=true;
		}
		
		if(validationCheck) {
			groupPostList=groupPostDAO.selectGroupPostListOnlyPhotoTypeByGroupId(groupId,limit);
		}
		
		return groupPostList;
	}

	public List<GroupPostView> getGroupPostViewListByGroupId(int groupId){
		
		Group group=groupBO.getGroupById(groupId);
		//그룹 아이디로 그룹 포스트 리스트 가져오기
		 List<GroupPost> groupPostList=groupPostDAO.selectGroupPostListByGroupId(groupId);
		 
		 if(groupPostList!=null) {//포스트 리스트가 null이 아니라면 실행할 것
			 List<Integer> groupPostIdList=new ArrayList<Integer>();
			 Set<Integer> memberIdSet=new HashSet<Integer>();
			 
			 //그룹 포스트 아이디와 멤버의 아이디만 가져오기
			 for(GroupPost groupPost : groupPostList) {
				 groupPostIdList.add(groupPost.getId());
				 //set을 이용하여 중복 아이디 값 지우기 
				 //(( 그룹멤버가 하나의 글만 쓰진 않으므로 중복되는 유저 아이디 값이 넘어올 것임 ))
				 memberIdSet.add(groupPost.getGroupMemberId());
			 }
			 
			 //중복이 제거된 유저아이디 셋을 리스트에 담아줌
			 List<Integer> groupMemberIdList=new ArrayList<Integer>();
			 
			 for(Integer groupMemberId : memberIdSet) {
				 groupMemberIdList.add(groupMemberId);
			 }
			 
			 List<GroupLikeView> groupLikeViewList=null;
			 List<GroupCommentView> groupCommentViewList=null;
			 List<User> userList=null;
			 
			 //그룹의 포스트가 있을 경우에 실행
			 if(groupPostIdList.size()!=0) {
				 //그룹라이크 뷰 리스트 , 코멘트 뷰 리스트 , 유저 리스트를 받아옴
				 groupLikeViewList=groupLikeBO.getGroupLikeViewListByPostIdList(groupPostIdList);
				 groupCommentViewList=groupCommentBO.getGroupCommentViewListByPostIdList(groupPostIdList);
				 
				 userList=userBO.getUserListByIdList(groupMemberIdList);
			 }
			 
			 //반환해줄 그룹 포스트 뷰 리스트 생성
			 List<GroupPostView> groupPostViewList=new ArrayList<GroupPostView>();
			 
			 
			 for(GroupPost groupPost: groupPostList) {
				 //그룹 포스트 뷰에 담아줄 라이크 리스트 코멘트 리스트 생성
				 List<GroupLikeView> likeList=new ArrayList<>();
				 List<GroupCommentView> commentList=new ArrayList<>();
				 //글의 주인을 담아줄 User객체
				 User owner = null;
				 
				 for(User member : userList) {
					 if(member.getId() == groupPost.getGroupMemberId()) {
						 //받아온 유저 리스트에서 글 주인의 정보를 담아줌
						 owner=member;
						 //찾은 이후 이 for문은 더이상 돌릴 필요가 없음.
						 break;
					 }
				 }
				 
				 //받아온 그룹의 라이크 뷰를 비교한다
				 for(GroupLikeView groupLikeView : groupLikeViewList) {
					 // 그룹라이크에 담겨있는 포스트 아이디와 포스트의 아이디와 비교하여
					 // 다를경우 아래 코드 실행하지 못하도록 continue 걸어줌
					 if(groupLikeView.getPostId() != groupPost.getId()) {
						 continue;
					 }
					 
					 //같을경우 likeList에 담아준다.
					 likeList.add(groupLikeView);
				 }
				 
				 // 받아온 그룹의 코멘트 뷰를 비교한다.
				 for(GroupCommentView groupCommentView : groupCommentViewList) {
					 // 그룹 코멘트에 담겨있는 포스트 아이디와 포스트의 아이디와 비교하여
					 //다를 경우 아래 코드 실행하지 못하도록 continue 걸어줌
					 if(groupCommentView.getPostId()!=groupPost.getId()) {
						 continue;
					 }
					 
					 //같을경우 commentList에 담아준다.
					 commentList.add(groupCommentView);
				 }
				 
				 //위의 코드 모두를 실행했을 경우 groupPostView를 가공할 모든 정보를 가져온 것이다.
				 //groupPostView 가공 후 groupPostViewList에 담아준다
				 GroupPostView groupPostView=setGroupPostView(owner, groupPost, group.getGroupName(), commentList, likeList);
				 
				 groupPostViewList.add(groupPostView);
			 }
			 return groupPostViewList;
		 }
		 return null;
	 }
	
	public List<GroupPostView> getGroupPostViewListByGroupTimeLine(int userId){
		List<GroupMember>groupMemberList=groupMemberBO.getGroupMemberListByGroupMemberId(userId);
		List<Integer> groupIdList=new ArrayList<Integer>();
		
		//그룹 아이디만 추출
		for(GroupMember groupMember : groupMemberList) {
			groupIdList.add(groupMember.getGroupId());
		}
		
		List<GroupTimeLine> groupTimeLineList=null;
		List<Group> groupList=null;
		
		//그룹 아이디 리스트 사이즈가 0이 아닐경우 타임라인에서 그룹 아이디 리스트를 이용해 정보를 가져옴
		//그룹을 모두 가져옴
		if(groupIdList.size()!=0) {
			groupTimeLineList=groupTimeLineBO.getGroupTimeLineListByGroupIdList(groupIdList);
			groupList=groupBO.getGroupListByIdList(groupIdList);
		}
		
		List<Integer> postIdList=new ArrayList<Integer>();
		Set<Integer> userIdSet=new HashSet<Integer>();
		List<Integer> userIdList=new ArrayList<Integer>();
		
		if(groupTimeLineList != null ) {
			for(GroupTimeLine groupTimeLine : groupTimeLineList) {
				postIdList.add(groupTimeLine.getPostId());
				userIdSet.add(groupTimeLine.getUserId());
			}
			
			for(Integer id : userIdSet) {
				userIdList.add(id);
			}
		}
		
		List<User> userList=null;
		if(userIdList.size()!=0) {
			userList=userBO.getUserListByIdList(userIdList);
		}
		
		List<GroupPost> groupPostList=null;
		List<GroupCommentView> groupCommentViewList=null;
		List<GroupLikeView> groupLikeViewList=null;
		
		if(postIdList.size()!=0) {
			groupPostList=groupPostDAO.selectGroupPostListByIdList(postIdList);
			groupCommentViewList=groupCommentBO.getGroupCommentViewListByPostIdList(postIdList);
			groupLikeViewList=groupLikeBO.getGroupLikeViewListByPostIdList(postIdList);
		}
		
		
		List<GroupPostView> groupPostViewList=new ArrayList<GroupPostView>();
		
		if(groupPostList!=null) {
			for(GroupPost groupPost : groupPostList) {
				List<GroupCommentView> commentViewList=new ArrayList<GroupCommentView>();
				List<GroupLikeView> likeViewList=new ArrayList<GroupLikeView>();
				User owner=null;
				Group postGroup=null;
				
				for(Group group : groupList ) {
					if(group.getId() == groupPost.getGroupId()) {
						postGroup=group;
						break;
					}
				}
				
				for(User user : userList) {
					if(groupPost.getGroupMemberId()==user.getId()) {
						owner=user;
						break;
					}
				}
				
				for(GroupCommentView comment : groupCommentViewList) {
					if(comment.getPostId()!=groupPost.getId()) {
						continue;
					}
					commentViewList.add(comment);
				}
				
				for(GroupLikeView like : groupLikeViewList) {
					if(like.getPostId() != groupPost.getId()) {
						continue;
					}
					likeViewList.add(like);
				}
				
				GroupPostView groupPostView=setGroupPostView(owner, groupPost, postGroup.getGroupName(), commentViewList, likeViewList);
				
				groupPostViewList.add(groupPostView);
				
			}
		}
		
		return groupPostViewList;
	}
	
	public GroupPostView getGroupPostViewById(int id) {
		GroupPost groupPost=groupPostDAO.selectGroupPostById(id);
		GroupPostView groupPostView=null;
		if(groupPost!=null) {
			List<GroupCommentView> commentList=groupCommentBO.getGroupCommentViewListByPostId(id);
			List<GroupLikeView> likeList=groupLikeBO.getGroupLikeViewListByPostId(id);
			Group group=groupBO.getGroupById(groupPost.getGroupId());
			User user=userBO.getUserById(groupPost.getGroupMemberId());
			
			groupPostView=setGroupPostView(user, groupPost, group.getGroupName(), commentList, likeList);
			logger.debug("::::::::::::::::::: groupPostView" +groupPostView);
		}
		
		return groupPostView;
	}
	
	private String generateImageUrlByFile(String userLoginId,MultipartFile file) {
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
	
	private GroupPostView setGroupPostView(User user, GroupPost groupPost ,String groupName ,List<GroupCommentView> commentList , List<GroupLikeView> likeList) {
		 GroupPostView groupPostView=GroupPostView
					.builder()
					.id(groupPost.getId())
					.groupId(groupPost.getGroupId())
					.groupMemberId(groupPost.getGroupMemberId())
					.content(groupPost.getContent())
					.contentType(groupPost.getContentType())
					.contentPath(groupPost.getContentPath())
					.createdAt(groupPost.getCreatedAt())
					.updatedAt(groupPost.getUpdatedAt())
					.groupName(groupName)
					.userLoginId(user.getLoginId())
					.userProfilePath(user.getProfileImagePath())
					.commentList(commentList)
					.likeList(likeList)
					.build();
		 return groupPostView;
	}
}
