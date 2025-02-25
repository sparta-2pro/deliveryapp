# delivery app 

---


## 1. 팀 소개 
- 손민주: 팀원, 가게 및 배달 가능 지역 담당
- 전인종: 팀장, 주문 및 리뷰 담당
- 정우준: 팀원, 메뉴 및 ai 담당
- 정은선: 팀원, 유저 및 장바구니 담당

---

## 2. 프로젝트 목적
> 본 프로젝트는 팀 프로젝트로서 협업 활동을 바탕으로 배달 서비스 플랫폼을 개발하는 목적으로, </br>
개발 명세서의 요구 사항을 충실히 반영하면서도 유연하고 확장성 있는 서비스를 목표로 합니다. </br>
이를 위해 모듈화된 아키텍처와 도메인별 명확한 역할 분리, 확장 가능한 설계 원칙을 적용하여 </br>
지속적으로 유지보수 및 개선이 가능한 구조를 갖추고자 노력했니다.

---

## 3. 프로젝트 구조
```plaintext
ai
├── controller
├── dto
├── entity
├── exception
├── repository
└── service

cart
├── controller
├── dto
├── entity
├── repository
└── service

common
├── config
├── dto
├── entity
├── enumType
├── exceptionHandler
└── scheduler

menu
├── controller
├── dto
├── entity
├── exception
├── repository
└── service

order
├── controller
├── dto
├── entity
├── exception
├── repository
└── service

payment
├── entity
├── repository
└── service

review
├── controller
├── dto
├── entity
├── exception
├── repository
└── service

store
├── controller
├── dto
├── entity
├── repository
└── service

user
├── config
├── controller
├── dto
├── entity
├── exception
├── jwt
├── repository
├── security
└── service
```

---

## 4. 프로젝트 실행 방법
- 도메인 주소:

---

## 5. 서비스 구성 
### 1) ai
-	OpenAI API 사용
-	메뉴 설명 텍스트 자동 생성 및 저장, 동적 조회, 삭제


### 2) cart
-	장바구니 관련 기능 담당
-	사용자별 장바구니에 메뉴 추가, 조회, 수정, 삭제


### 3) common
- config : 전역적인 설정 파일 관리
- dto : 도메인과 무관한 전역 DTO 관리
- entity : 공통적으로 사용될 수 있는 엔티티 관리
- enumType : 도메인 공통 사용 Enum 타입 관리
- exceptionHandler : 전역적인 예외 처리 관리


### 4) menu
-	메뉴 관련 기능 담당
- 가게별 메뉴 저장, 조회, 수정, 삭제
- 배달 가능 지역 내 메뉴 전체 필터링 조회 


### 6) order
-	주문 관련 기능 담당
- 사용자별 주문 생성, 조회, 수정, 삭제


### 7) payment
- 결제 결과 저장, 조회


### 8) review
-	사용자 리뷰 및 평점 기능 담당


### 9) store
-	가게 관련 기능 담당
-	카테고리 관리 및 카테고리별 가게 조회
-	배달 가능 지역 관리


### 10) user
- 회원 가입 및 로그인, 로그아웃 관리
- 유저 정보 저장, 조회, 수정, 삭제
- 시큐리티 설정 및 관리
- JWT 설정 및 관리


---


## 6. ERD 및 API 문서
![ERD](https://github.com/sparta-2pro/deliveryapp/blob/dev/erd_0225.png)


![API DOCS](https://github.com/sparta-2pro/deliveryapp/blob/dev/api_docs_0225.png)

---


## 7. 기술 스택
![기술스택](https://github.com/sparta-2pro/deliveryapp/blob/dev/stack_0225.png)

---
