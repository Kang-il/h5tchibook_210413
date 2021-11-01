# Spring Boot Project -- H5tchibook 소개

## 배포
http://54.180.108.245:8080/user/sign_in_view

## PORTFOLIO
https://www.notion.so/nosic/PORTFOLIO-21762e61fdfe4282ac444ab31e29ed59

## 개발기간
2021 .09 .02 - 2021 .10 .03 (1개월)<br>

- 기획서 작성
- 프로젝트 일정표
- DB설계, API 설계 , URL 설계
- View 페이지 설계 및 구현
- 기능 구현 

---
## 사용 기술
- Java
- HTML, CSS, JavaScript, JQuery, Bootstrap
- Spring 4.11, Tomcat 9.0 , MySQL, MyBatis
- MVC Pattern, Ajax
- Git, AWS
---
## View 페이지 구성
- 로그인 view
- check password (유저 정보 수정 전 본인확인 view)
- 포스트 디테일 view
- 유저 피드 view
- 유저 타임라인 view
- 그룹 만들기 view
- 그룹 타임라인 view
- 그룹 관리 리스트 view
- 그룹 정보 변경 view
- 그룹 피드 view

---
## 구현 기능
- User
  - 회원가입
  - 회원가입 유효성 체크
  - 로그인
  - 피드 배경이미지 변경
  - 자기소개 변경
  - 비밀번호를 통한 본인확인
  - 회원정보 변경
  - 변경 정보 유효성 확인
- Post
  - 게시글 등록
  - 게시글 삭제
  - 그룹 게시물 등록
  - 그룹 게시물 삭제 
- Like
  - 좋아요 누르기/취소
  - 좋아요 목록 가져오기
  - 그룹 게시물 좋아요 누르기 / 취소
  - 그룹 게시물 좋아요 목록 가져오기
- Comment
  - 댓글등록
  - 댓글목록 가져오기
  - 댓글 삭제
  - 그룹 게시물에 댓글달기
  - 그룹 댓글 목록 가져오기
  - 그룹 댓글 삭제
- Friend
  - 친구 요청
  - 친구 요청 수락
  - 친구 요청 거절
  - 친구 요청 취소
  - 친구 삭제
- Group
  - 그룹 생성
  - 그룹 삭제
  - 그룹 가입요청
  - 그룹 가입요청 응답(수락, 거절)
  - 그룹 멤버 삭제
  - 그룹 프로필 이미지 변경
  - 그룹 커버 이미지 변경
- TimeLine
  - 그룹/유저 타임라인
- Alert
  - comment(user/group)
  - like(user/group)
  - friend_request
  - group_join_request
  --- 
  <br>
# PREVIEW
## 로그인 화면
![1 png](https://user-images.githubusercontent.com/86404952/139694073-eb1e8a59-b708-4555-9976-050561420c00.jpg)
## 회원가입 modal 노출
![2](https://user-images.githubusercontent.com/86404952/139698387-45a5c20b-be08-4661-8277-064be3eb09c6.jpg)
## 회원가입 유효성 위배 시
![3](https://user-images.githubusercontent.com/86404952/139699380-4dcd34b5-6b31-4fcf-a92e-7d6152eff16a.png)
## 타임라인 페이지 (로그인 후 최초 페이지)
![3](https://user-images.githubusercontent.com/86404952/139700508-587df6d1-732d-4b1f-886e-efe03cdecd38.jpg)
## 게시물 등록 모달 노출
![4](https://user-images.githubusercontent.com/86404952/139701381-a153320e-7de1-4682-9b2f-22f930b08f91.jpg)
## 유저 피드 뷰
![5](https://user-images.githubusercontent.com/86404952/139701673-bdccc6c9-1f9e-40cf-80c7-b349a6d770a0.jpg)
## 포스트 디테일 뷰
![14](https://user-images.githubusercontent.com/86404952/139703716-ecf91028-bc14-46c7-b3e3-f91920712139.jpg)
## 유저 프로필 / 커버 이미지 변경 모달
![8](https://user-images.githubusercontent.com/86404952/139702212-bd0d7062-3d6c-44e2-bc57-238420071e11.jpg)
![9](https://user-images.githubusercontent.com/86404952/139702215-ac626372-8bee-427e-b707-cf42288e66f4.jpg)
## check password 뷰
![6](https://user-images.githubusercontent.com/86404952/139701863-a8b7a5e2-34e0-47e1-9142-434c10574e99.jpg)
## 개인정보 수정 뷰
![7](https://user-images.githubusercontent.com/86404952/139702026-ad9b95ad-0633-4dfd-9b0f-028639afddec.jpg)
## 그룹 피드 뷰
![10](https://user-images.githubusercontent.com/86404952/139702873-afeb2b5f-0f51-48f8-8aa2-a9ed253a09a9.jpg)
## 그룹관리 목록 뷰
![11](https://user-images.githubusercontent.com/86404952/139703016-af22d768-274c-40cf-adbf-3a6de1c08457.jpg)
## 그룹 관리 뷰
![12](https://user-images.githubusercontent.com/86404952/139703192-6b91ada5-4376-48e0-98ed-b0f50fd61d92.jpg)
## 그룹 타임라인 뷰
![13](https://user-images.githubusercontent.com/86404952/139703453-e6868eff-2249-4ebf-a63d-c50159f4bf8e.jpg)
## 알람창 / 유저 인포 창
![16](https://user-images.githubusercontent.com/86404952/139704055-858b84bf-a7f8-48eb-a13a-86fc35551f79.jpg)
![15](https://user-images.githubusercontent.com/86404952/139704049-5f71d838-ca9f-4aaf-a32e-b9597bf9bd7a.jpg)
