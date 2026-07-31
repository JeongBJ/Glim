# Glim(글:림)

<img src = "./assets/image/glim_logo_image_large.png" width="160" height="160"> 


## ✍ 프로젝트 개요
`글귀 + 울림`, ‘글귀 + film’ 또는 ‘glimpse(흘낏보다)’의 조합. 
짧지만 깊은 인상을 남기는 글귀를 공유하며, 
숏츠(글림으로 명명) 폼으로 다른 사람들과 감성을 공유하며 
글림을 통해 책에 대한 관심을 유발하고,
알라딘 등 온라인 서점으로 연결하여 구매를 유도

---

### 기획의도
현대인의 디지털 과부하 문제
- 무분별한 숏폼 콘텐츠 소비 (틱톡, 인스타 릴스)
- 자극적이고 빠른 콘텐츠에 익숙해진 뇌
- 집중력 저하 및 깊이 있는 사고 부족
- 책 읽기 시간과 독서량 급격한 감소

글림의 디지털 디톡스 접근법
"완전한 차단이 아닌, 건전한 대안 제시"


## 핵심 서비스



| **글림 생성** | **글림 둘러보기** | **글림 공유 & 저장** | **잠금화면 글귀 설정** |
|--------|------|------|------|
| ![스크린샷](/assets/gif/Post.gif) | ![스크린샷](/assets/gif/Glim.gif) |  ![스크린샷](/assets/gif/Home.gif) | ![스크린샷](/assets/gif/LockScreen.gif) |
| 텍스트 인식, 편집<br>이미지 생성 | 숏폼<br>좋아요, 공유, 저장 | 글림 및 도서 추천 | 잠금화면에서 바로 보기 |

-----
<br>

| **검색** | **도서 정보** | **내정보** | **공유** | **설정** |
|--------|------|------|------|------|
| ![스크린샷](/assets/image/Search.png) | ![스크린샷](/assets/image/Book.png) | ![스크린샷](/assets/image/MyPage.png) | ![스크린샷](/assets/image/Share.png) | ![스크린샷](/assets/image/Setting.png) |
| 도서, 글림 검색<br>제목, 작가 검색 | 도서 정보 확인<br>구매 페이지 | 잔디<br>등록, 좋아요한 글림 | 딥링크로 공유, 연결 | 설정 화면

----


## 프로젝트 구성

### Android - 
- Multi Module + Clean Architecture + MVI
- 구성:
  - `app` - Main, Navigation
  - `core-android` - Android 전용 유틸리티
  - `core` - 공통 Kotlin 모듈
  - `data` - 데이터 계층, 리포지토리
  - `domain` - 도메인 모델 및 비즈니스 로직
  - `presentation` - UI 및 Compose 화면



### BackEnd
- Feature-based MVC
- 구성:
  - `Controller / Web` - HTTP 요청 처리, REST API 및 공유 페이지 렌더링
  - `Service` - 비즈니스 로직 처리, 트랜잭션 관리, Repository 및 외부 서비스 조합
  - `Repository` - JPA/Redis 기반 영속성 계층
  - `Entity / DTO` - 도메인 모델, 요청/응답 데이터 변환
  - `External` - Kakao/Google OAuth, Firebase, Oracle OCI 등 외부 연동 클라이언트
  - `Infra` - 인프라 관련 구현과 공통 구성
  - `Security` - 인증, 권한, JWT, Spring Security 설정
  - `Common` - 공통 응답, 예외 처리, 유틸리티

### Infra
- Oracle Cloud Infrastructure
    - Instance - 클라우드 서버
        - PostgreSQL : 데이터베이스
        - Redis : JWT, 도서 검색, 홈 화면 캐싱    
        - Jenkins: CI/CD 파이프라인 자동화 및 빌드/배포 관리
        - Docker: 컨테이너 기반 배포 환경 및 이미지 관리
    - Bucket - 글림 및 프로필 이미지 저장

- Hugging Face  
  - Space - GPU 서버
    - FLUX.1 Schnell
    - 이미지 생성

## 기술 스택

| Package | Version | Comment |
| --- | --- | --- |
| Kotlin | 2.4.10 | Android, Backend 공통 |
| JDK | 21 |  |
| Gradle | 9.6.1 | |

### Android
| Package | Version | Comment |
| --- | --- | --- |
| Android Gradle Plugin | 9.3.1 | Android 빌드 도구 |
| Jetpack Compose | 2026.06.01 | Compose UI |
| Navigation Compose | 2.9.8 | Compose 네비게이션 |
| Hilt | 2.60.1 | 의존성 주입 라이브러리 |
| Hilt Navigation Compose | 1.4.0 | Compose와 Hilt 통합 |
| Firebase Messaging | 25.1.1 | 푸시 알림 서비스 |
| Google ID | 1.2.0 | Google 로그인 |
| Kakao SDK | 2.24.0 | 카카오 로그인 |
| Coil | 3.5.0 | 이미지 로딩 |
| Retrofit | 3.0.0 | REST API 클라이언트 |
| OkHttp | 5.4.0 | HTTP 클라이언트 |
| Paging | 3.5.0 | 리스트 페이징 지원 |
| DataStore | 1.2.1 | 로컬 데이터 저장 |
| ML Kit Text Recognition | 16.0.1 | OCR 텍스트 인식 |
| Lottie Compose | 6.7.1 | 애니메이션 렌더링 |
| Splash Screen | 1.2.0 | 스플래시 화면 지원 |
| Timber | 5.0.1 | 로깅 라이브러리 |
| Kotlin Serialization JSON | 1.11.0 | JSON 직렬화 |
| MockK | 1.14.11 | 테스트 목킹 라이브러리 |
| Truth | 1.4.5 | 테스트 어서션 |
| Turbine | 1.2.1 | Flow 테스트 지원 |

### Backend
| Package | Version | Comment |
| --- | --- | --- |
| Spring Boot | 4.0.6 | 백엔드 프레임워크 |
| Spring Framework | 7.0.7 | Spring Framework |
| Spring Security | 7.0.7 | 인증 및 권한 관리 |
| Spring Data JPA | 4.0.5 | JPA 기반 데이터 액세스 |
| Spring WebFlux | 7.0.7 | 리액티브 웹 프레임워크 |
| Spring WebMVC | 7.0.7 | MVC 웹 프레임워크 |
| Spring Cache | 7.0.7 | 캐시 추상화 |
| Spring Cloud OpenFeign | 5.0.1 | REST 클라이언트 |
| PostgreSQL | 16.13 | RDBMS |
| PostgreSQL JDBC | 42.7.3 | PostgreSQL 드라이버 |
| Spring Data Redis | 4.0.5 | Redis 데이터 액세스 |
| Thymeleaf | 3.1.3 | 서버 템플릿 렌더링 |
| Flyway | 11.12.0 | 데이터베이스 마이그레이션 |
| Jackson Kotlin Module | - | JSON 직렬화/역직렬화 |
| Kotlin Reflect | - | 리플렉션 지원 |
| JJWT API | 0.12.7 | JWT 토큰 처리 |
| Firebase Admin | 9.8.0 | Firebase 서버 SDK |
| Oracle OCI SDK Object Storage | 3.86.2 | Oracle Object Storage |
| Kotlin Logging JVM | 8.0.3 | 로깅 유틸리티 |
| QueryDSL JPA | 5.1.0 | 타입 세이프 쿼리 DSL |
| Jasypt Spring Boot Starter | 4.0.4 | 암호화 설정 |

### Infra
| Name | Service | Comment |
| --- | --- | --- |
| Oracle Cloud | Instance | Ubuntu 22.04<br>Ampere A1 4 OCPUs, 24GB Memory, 200GB Block Volume |
|  | Bucket | 20GB Object Storage |
|  | Jenkins | CI/CD 파이프라인 자동화 |
|  | Docker | 컨테이너 기반 배포 |
| Hugging Face | Space | Nvidia RTX Pro 6000 Blackwell (ZeroGPU) |
|  | Model | FLUX.1 Schnell|


