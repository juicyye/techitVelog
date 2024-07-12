# 📣 Techitlog Application
7월 1일 ~ 7월 12까지 했던 프로젝트 경험에 대해 적었습니다
<br><br>

## 📝개요
* **프로젝트명** : Techitlog
* **개발 환경**

* **주제** : 블로그 어플리케이션의 기본 - 심화 기능을 연습하는 프로젝트입니다.

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
**Woowahan-Coupons**
├─docs
│  └─asciidoc
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

배달의 민족 앱을 분석해 보면서 도메인과 기능을 도출하고 쿠폰 서비스에 대한 이해를 하고자 했습니다.

- 쿠폰 발급 주체
    - 쿠폰 어드민
    - 매장 어드민
- 쿠폰 scope 주체
    - 배민 자체 (ex 배민 첫 주문 만원 할인, B마트 추석맞이 할인 쿠폰)
    - 음식점 브랜드  → brand (ex 네네치킨 브랜드관 쿠폰)
    - 음식점 → store (ex 맹호수제돈까스 부천점의 최소 주문금액 별 할인 쿠폰)
- 쿠폰 발급 대상
    - 음식을 주문하는 배민 모바일 앱 사용자
- 쿠폰 발급 방식
    - 쿠폰 번호 등록
    - 클릭으로 바로 등록
- 쿠폰 사용 조건
    - 최소 주문 금액
    - 사용 기간
        - 발급 후 N일 동안 사용
        - yyyy.MM.dd ~ yyyy.MMdd 까지
    - 첫 주문
    - ~~물건 N개 이상 구매 (라이브 쇼핑)~~
    - ~~N개월간 현대카드 사용하지 않음~~
    - ~~포장 or 배달~~
    - 사용후 특정 시간 후 재발급 가능
    - 사람 별 제한
    - 수량 제한(선착순 이벤트)
- 쿠폰 종류
    - 할인 쿠폰
        - 고정 금액 할인 (ex 천원 할인 쿠폰)
        - 퍼센트 할인 (ex 20% 할인 쿠폰)
    - ~~무료 쿠폰~~
        - ~~배달비 무료 쿠폰~~
    - ~~랜덤쿠폰~~
<br><br>


## **Git Commit Message Type**

![Untitled 6](https://user-images.githubusercontent.com/36220595/140631424-24896a5c-8a35-4886-b75a-3df17870c7cf.png)

```bash
1- ⭐ feat : 새로운 기능에 대한 커밋
2- ⚙️ chore : 그 외 자잘한 수정에 대한 커밋
3- 🐞 fix : 버그 수정에 대한 커밋
4- 📖 docs : 문서 수정에 대한 커밋
5- 💅 style : 코드 스타일 혹은 포맷 등에 관한 커밋
6- ♻️ refactor : 코드 리팩토링에 대한 커밋
7- 🚦 test : 테스트 코드 수정에 대한 커밋
8- 🚀 CI : CI/CD
9- 🔖 Release : 제품 출시
10- 🎉 init : 최초 커밋
11- 🛠️ Config : 환경설정에 대한 커밋
12- 🦔 Revert : 리버트
```
<br><br>

## 코드

코드 컨벤션은 **구글의 자바 컨벤션**을 따르기로 결정했습니다.

또한 `sonar lint`를 적용해서 리뷰를 받기 전에 컨벤션에 대한 검사를 수행하도록 했습니다.
<br><br>

# 😈 CI/CD

### Github Action

![image](https://user-images.githubusercontent.com/36220595/140631504-5ae2e61f-a25d-4cf9-88bd-27c54a68fcc6.png)

![image](https://user-images.githubusercontent.com/36220595/140631508-9bf28ab9-308e-41e0-b91a-6842cc123b8d.png)
- java ci with gradle : ci 및 빌드 확인
- Sonar-build : push 된 commit  코드스멜 등 코드 분석
- deploy : 배포
<br><br>

## AWS(EC2, S3, Code Deploy)

![image](https://user-images.githubusercontent.com/36220595/140631512-25e208e9-4745-4e4a-8914-8fd3c43b272e.png)
<br><br><br><br>

# API Docs

- [http://ec2-3-36-59-242.ap-northeast-2.compute.amazonaws.com:8080/docs/index.html](http://ec2-3-36-59-242.ap-northeast-2.compute.amazonaws.com:8080/docs/index.html)
<br><br><br><br>
