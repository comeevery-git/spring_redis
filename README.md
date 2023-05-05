# Redis 사용기록

## 개요
###주요내용
- Redis 사용했던 경험 기록
  - lock, cache, pub/sub
- 테스트 코드
- 새로 적용한 기록

###branch
  - master
    - 기본적으로 feature/redisson Merge
  - issue
    - 동시성 이슈발생 상황 커밋 56eb192a
  - feature
    - lettuce: lettuce client 사용
    - redisson: redisson client 사용

## 개발환경
- Java IDE: IntelliJ
- JDK 17

### Database
- H2
- 접속정보
  - Url: /h2-console
  - Port: 8080
  - Login Id: sa
  - Login Password: abcde

### Redis
Redis server v=7.0.11
Url: localhost
Port: 6379