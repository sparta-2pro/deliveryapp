# 📱AI 활용 비즈니스 프로젝트

## 1. 팀 소개

| **이름**   | **역할**   | **담당**                     |
|------------|------------|-------------------------------|
| 손민주      | 팀원       | `가게`, `배달 가능 지역`          |
| 전인종      | 팀장       | `주문`, `리뷰`                   |
| 정우준      | 팀원       | `메뉴`, `AI`                     |
| 정은선      | 팀원       | `사용자`, `장바구니`                |

<br>

## 2. 프로젝트 목적

> 본 프로젝트는 **배달 서비스 플랫폼**을 개발하는 **팀 프로젝트**로, **협업을 기반**으로 합니다.<br>
> **개발 명세서**의 요구 사항을 **충실히 반영**하며, **유연하고 확장성 있는 서비스**를 목표로 합니다.<br>
> 이를 위해 **모듈화된 아키텍처**와 **도메인별 역할 분리**, **확장 가능한 설계 원칙**을 적용하여 <br>
> **지속적인 유지보수와 개선이 가능한 구조**를 지향합니다.

<br>

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

<br>

## 4. 프로젝트 실행 방법
- 서비스 주소: [링크](https://3.25.217.12:8080)

<br>

## 5. 서비스 구성

| **기능**     | **세부사항**                                                                 |
|--------------|-----------------------------------------------------------------------------|
| `ai`       | - OpenAI API 사용 <br> - 메뉴 설명 텍스트 자동 생성 및 저장, 동적 조회, 삭제 |
| `cart`     | - 장바구니 관련 기능 담당 <br> - 사용자별 장바구니에 메뉴 추가, 조회, 수정, 삭제 |
| `common`   | - `config`: 전역적인 설정 파일 관리 <br> - `dto`: 도메인과 무관한 전역 DTO 관리 <br> - `entity`: 공통적으로 사용될 수 있는 엔티티 관리 <br> - `enumType`: 도메인 공통 사용 Enum 타입 관리 <br> - `exceptionHandler`: 전역적인 예외 처리 관리 |
| `menu`     | - 메뉴 관련 기능 담당 <br> - 가게별 메뉴 저장, 조회, 수정, 삭제 <br> - 배달 가능 지역 내 메뉴 전체 필터링 조회 |
| `order`    | - 주문 관련 기능 담당 <br> - 사용자별 주문 생성, 조회, 수정, 삭제 |
| `payment`  | - 결제 결과 저장, 조회 |
| `review`   | - 사용자 리뷰 및 평점 기능 담당 |
| `store`    | - 가게 관련 CRUD 및 검색 기능 담당 <br> - 카테고리 관리 및 카테고리별 가게 조회 <br> - 배달 가능 지역 관리 |
| `user`     | - 회원 가입 및 로그인, 로그아웃 관리 <br> - 유저 정보 저장, 조회, 수정, 삭제 <br> - 시큐리티 설정 및 관리 <br> - JWT 설정 및 관리 |

<br>


## 6. ERD 및 API 문서

| **ERD**                                                                                         | **API 명세서**                                                                                       |
|------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| ![ERD](https://github.com/sparta-2pro/deliveryapp/blob/dev/erd_0225.png)                       | ![API](https://github.com/user-attachments/assets/0835d47f-65b0-4eb0-878f-707903e673cf)   |

<br>

## 7. 기술 스택

| **인프라 설계도** | **기술 스택** |
|------------------|------------------|
| ![인프라](https://github.com/user-attachments/assets/1ad1114a-36fe-4625-abbd-5ab2b1e46fd2) | - **Backend**: ``Spring Boot 3.3.8``, ``Java 17``, ``JPA``, ``Security``, ``QueryDSL``, ``Validation``, ``Logging`` <br/> - **Database**: ``PostgreSQL`` <br/> - **AI**: ``OpenAI API`` <br/> - **의존성**: ``JWT``, ``Shedlock`` |

<br>

## 8. 프로젝트 결과물 정리본
- 발표자료 주소: [링크](https://drive.google.com/file/d/1yYybMAYgwWfkAySlf8aJn610XoZDVXBP/view?usp=sharing)
