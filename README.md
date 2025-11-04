# 🛒 이커머스 백오피스 프로젝트

> 실제 백오피스 환경을 가정하고, 고객과 상품 정보를 등록·조회·수정·삭제(CRUD) 할 수 있는 기능을 직접 구현
>
> 관리자가 고객과 상품 데이터를 쉽고 정확하게 관리할 수 있는 기본 시스템을 구축하는 것

---

## 🧩 기술 스택

| 구분 | 기술 |
|------|------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x |
| **ORM / DB** | Spring Data JPA, MySQL |
| **Security** | Spring Security + JWT |
| **Build Tool** | Gradle |
| **IDE** | IntelliJ IDEA |
| **API Test** | Postman |
| **Version Control** | Git / GitHub |

---

## 📁 디렉토리 구조
```
customer-product-system
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.sparta.customerproductsystem
│   │   │       ├── config               # Security, JWT 등 환경설정
│   │   │       ├── controller           # API 요청/응답 처리 (User, Product 등)
│   │   │       ├── domain
│   │   │       │   └── entity           # JPA 엔티티 클래스
│   │   │       ├── dto                  # 요청/응답 DTO 클래스
│   │   │       ├── jwt                  # JWT 인증 관련 클래스 (JwtUtil, Filter, UserInfo)
│   │   │       ├── repository           # JPA Repository 인터페이스
│   │   │       └── service              # 비즈니스 로직 처리 (UserService, ProductService 등)
│   │   └── resources
│   │       ├── application.yml          # DB 및 Security 설정
│   │       └── static / templates       # (필요 시) 정적 리소스 / 템플릿
│   └── test
│       └── java …                     # 단위 테스트
│
├── build.gradle
├── settings.gradle
├── .gitignore
└── README.md
```
---

## ⚙️ 환경설정

### 1️⃣ 전제조건
- JDK 17 이상
- MySQL (로컬 혹은 Docker)
- Gradle 8.x 이상

### 2️⃣ 환경 변수 (.env)
> `.env` 파일은 Git에 커밋되지 않으며, 각자 로컬에서 직접 설정해야 합니다.

> **참고:** `.gitignore`에 `.env`가 포함되어 있어 커밋되지 않습니다.

---

## 🚀 실행방법

```
# 1. git clone
git clone https://github.com/LeoOH5/customer-product-system.git

# 2. 환경 변수 설정
.env 예시 파일 생성 후 application.properties 내 변수에 맞춰 설정 필요

# 3. 실행
```

## 🔐 인증 절차

1. 사용자는 /login 또는 /auth/signup 으로 AccessToken 발급
2. JWT 토큰은 Authorization: Bearer 형식으로 전송
3.	JwtAuthenticationFilter 가 토큰을 검증하고
→ UserInfo 객체 생성
→ SecurityContext 에 Authentication 등록

---
## 🧩 주요 기능

### 회원 관리
- 회원 등록, 로그인, 조회, 수정 기능 제공
- 본인 정보 수정은 본인만 가능 (`@PreAuthorize` 기반 접근 제어)
- 관리자(Admin)는 전체 사용자 정보 조회 및 권한(Role) 변경 가능

### 상품 관리
- 상품 등록, 수정, 삭제, 조회 기능
- 상품 상태(Status), 재고(Stock) 및 카테고리(Category) 관리


### 인증 및 인가
- JWT 기반 로그인 및 인증 처리
- Spring Security를 통한 Role 기반 접근 제어 (`CUSTOMER`, `ADMIN`)
- 토큰 내 Role 정보를 SecurityContext에 반영하여 `@PreAuthorize` 동작 보장

### 시스템 관리
- `.env` 환경 변수 파일 기반 설정 (DB, JWT Secret 등)
- `.gitignore`로 민감 정보 비공개 유지

---
## API 명세서

- 어떻게 담을지 고민...