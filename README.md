## 📝개요
* **프로젝트명** : Techitlog
* **개발 환경**

* **주제** : 블로그 어플리케이션을 만드는 프로젝트입니다.

* **기간** : 2024년 7월 1일 ~ 2024년 7월 12일

<div align=center><h1>📚 STACKS</h1></div>

<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
  <br>
  
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/JPA-FF3621?style=for-the-badge&logo=mysql&logoColor=white"> 
  
  

  <br>
  
  <img src="https://img.shields.io/badge/amazon aws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 
  <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazonaws&logoColor=white"> 
  <img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonaws&logoColor=white"> 
  <img src="https://img.shields.io/badge/amazon route 53-8C4FFF?style=for-the-badge&logo=amazonaws&logoColor=white"> 
  <img src="https://img.shields.io/badge/Code Deploy-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 
  

  <br>
  
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <br>
</div>

## 📝 API 명세

- 추가 예정

## 📊 ERD

![techit velog](https://github.com/user-attachments/assets/a753db87-87e5-414f-917f-bd88d76b0dc6)


## 🔨 트러블슈팅

- 추가 예정
- 

# 🎫 TechitLog

이번 프로젝트는 **Velog** 를 클론코딩 한 프로젝트입니다.

![스크린샷 2024-07-12 173201](https://github.com/user-attachments/assets/22361235-197e-43b0-a6e1-c61313742290)

<br><br>

### **기타**

- ERDCloud
- Postman
- Rest Docs
- Dbeaver, Mysql WorkBench
- Github Action
<br><br>

## 패키지 구조

```bash
**techitlog**
├
│  
├─main
│  ├─java
│  │  └─techit
│  │      └─velog
│  │          └─api
│  │          └─domain
│  │              └─blog
│  │              │  ├─dto
│  │              │  ├─entity
│  │              │  ├─repository
│  │              │  ├─sevice
│  │              └─comment
│  │              │  ├─dto
│  │              │  ├─entity
│  │              │  ├─repository
│  │              │  ├─sevice
│  │              └─follow
│  │              │  ├─dto
│  │              │  ├─entity
│  │              │  ├─repository
│  │              │  ├─sevice
│  │              └─liks
│  │              │  ├─entity
│  │              │  ├─repository
│  │              │  ├─sevice
│  │              └─mail
│  │              │  ├─event
│  │              │  ├─sevice
│  │              └─post
│  │              │  ├─dto
│  │              │  ├─entity
│  │              │  ├─repository
│  │              │  ├─sevice
│  │              └─posttag
│  │              │  ├─dto
│  │              │  ├─entity
│  │              │  ├─repository
│  │              │  ├─sevice
│  │              └─tag
│  │              │  ├─dto
│  │              │  ├─entity
│  │              │  ├─repository
│  │              │  ├─sevice
│  │              └─uploadfile
│  │              └─user
│  │              │  ├─dto
│  │              │  ├─entity
│  │              │  ├─repository
│  │              │  ├─sevice
│  │          └─glbal
│  │              ├─dto
│  │              ├─exception
│  │              ├─exception
│  │              ├─s3
│  │              ├─security
│  │              ├─util
│  │              ├─valid
│  │          └─web
│  │              ├─AdminController
│  │              ├─BlogController
│  │              ├─CommentController
│  │              ├─HomeController
│  │              ├─LikeController
│  │              ├─PostController
│  │              ├─TagController
│  │              ├─TagController
│  │              └─UserController
│  └─resources
    └─static
    └─templates
        └─admin
        └─blog
        └─comment
        └─error
        └─layout
        └─login
        └─posts
        └─user
```
<br><br><br><br>


# 🤡 요구사항 분석
- 한 유저가 하나의 블로그를 가질 수 있고 일반 블로그처럼 글을 쓸 수 있다.
- 제목 또는 내용이나 작성자로 검색할 수 있다
- 최신순, 오래된순, 좋아요순으로 정렬이 가능하다
- 좋아요한 포스트를 따로 볼 수 있다
- 한 댓글에 여러 무한댓글을 달 수 있다
- 하나의 글에는 여러 태그들을 가질 수 있고, 해당 태그를 가진 글만 볼 수 있다
- 비밀글과 임시글은 자신만 볼 수 있다
- Admin은 모든 글과 사용자와 댓글을 삭제할 수 있다




<br><br>

# 사진
### 1. 정렬
![스크린샷 2024-07-12 182755](https://github.com/user-attachments/assets/2f2add42-c6a8-4d8b-b9d6-e05d772fc6e4)
### 2. 관리자의 경우 유저삭제와 글 삭제가 가능하다
![스크린샷 2024-07-12 182747](https://github.com/user-attachments/assets/73b799f8-7ad9-4929-a88a-01e02d2c4558)
![스크린샷 2024-07-12 181107](https://github.com/user-attachments/assets/8cc1d0ac-2dc4-42db-99a5-f0a6ff36deef)
### 3. 임시글
![스크린샷 2024-07-12 182732](https://github.com/user-attachments/assets/56ae9ca8-2889-4932-a04d-6fb5a8d5c101)
### 4. 태그와 비밀글 표시 
![스크린샷 2024-07-12 182736](https://github.com/user-attachments/assets/013e7183-b027-4c51-be71-a4170fecd6f2)
### 5. 무한계층 댓글 
![스크린샷 2024-07-12 182643](https://github.com/user-attachments/assets/b56c5c7e-5466-4782-b5d3-9c9f19aed57e)
### 6. 개인정보 표시 및 수정, 탈퇴 
![스크린샷 2024-07-12 182708](https://github.com/user-attachments/assets/d5ca2499-085f-425d-955c-846d2df56eda)
### 7. 글 삭제와 삭제, 추천 
![스크린샷 2024-07-12 182643](https://github.com/user-attachments/assets/eef36bad-bdad-4578-aaa2-54e1f8dade76)
### 8. 좋아한 포스트 
![스크린샷 2024-07-12 182747](https://github.com/user-attachments/assets/f1839d1d-07d8-457e-9706-579c4c607a4a)
### 9. 팔로우 및 팔로워
![스크린샷 2024-07-12 183330](https://github.com/user-attachments/assets/ad1a909d-3ce2-4fa3-becb-ae240b922bc6)

## **Git Commit Message Type**

![스크린샷 2024-07-12 174832](https://github.com/user-attachments/assets/0e0f4ad9-c41b-49c9-959a-f4ead3136e15)


```bash
# feat: 새로운 기능 추가
# fix: 버그 수정
# docs: 문서 수정
# style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
# refactor: 코드 리팩토링
# test: 테스트 코드, 리팩토링 테스트 코드 추가
# chore: 빌드 업무 수정, 패키지 매니저 수정, production code와 무관한 부분들 (.gitignore, build.gradle 같은)
# !BREAKING CHANGE: 커다란 API변경의 경우
# comment: 주석 추가 및 변경
# remove: 파일, 폴더 삭제
# rename: 파일, 폴더명 수정
# !HOTFIX: 급하게 치명적인 버그를 고쳐야 하는 경우
```
<br><br>

# 😈 CI/CD

### Github Action
![스크린샷 2024-07-12 175126](https://github.com/user-attachments/assets/f3ce943d-f623-4e0f-acfe-b1c03543dced)
<br><br>

## AWS(EC2, S3, Code Deploy)
![스크린샷 2024-07-12 180524](https://github.com/user-attachments/assets/2d0c234d-1907-4910-9d82-07135a6911ca)
<br><br><br><br>
