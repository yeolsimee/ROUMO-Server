# ROUMO (루틴 습관 형성 앱)
![image](https://github.com/yeolsimee/ROUMO-Server/assets/18053020/65cc3f38-91bf-4722-8727-d70b400eef4b)
![image](https://github.com/yeolsimee/ROUMO-Server/assets/18053020/1fc77bb9-00c0-4a1e-92aa-7b3a34799a50)

## 기술 스택
- Java 17
- Spring Boot 2.7.9
- Spring Data JPA(QueryDSL)
- Junit5 
- PostgresSQL

## 주요 작업

### 1. CI/CD 배포
- `github actions`과 `네이버클라우드`를 이용하여 CI/CD 파이프라인 구축
![image](https://github.com/yeolsimee/ROUMO-Server/assets/18053020/0e929c6e-971b-4300-a0d0-03e8606f72a7)

### 2. 인수테스트를 이용하여 테스트 리팩토링
1. 기존에 루틴을 생성시에 routineDay라는 테이블에 일별로 최대 `1년치 컬럼`을 생성했음.(매일 반복시)
2. 직관적이었지만 한 루틴에 최대 365개의 컬럼이 생성되어 리소스 소모가 너무 컸고 미리 생성하는 로직이었기 때문에 특정 기간이 지나면 다시 재 생성해야 한다는 문제가 있다고 판단되어 리팩토링을 하기로 함.
3. 기존에 운영중인 api에 영향을 미치지 않게 구현로직은 달라지지만 리턴값은 유지하도록 리펙토링을 진행하기로 함.
4. `RestAssured`를 이용한 인수테스트가 기존에 구축해놓아서 내부 로직을 변경하면서 리턴값을 체크함.
5. 결과적으로 루틴 완료 체크시에만 히스토리 관리를 위해 해당날짜, 루틴아이디를 가지고 매핑하여 저장하는 테이블을 만듬.

### 3. jacoco를 이용하여 테스트 커버리지 체크(진행 예정 작업)


## 설계
### ER 다이어그램
<img width="688" alt="image" src="https://github.com/yeolsimee/ROUMO-Server/assets/18053020/6afe83f9-4024-4e96-bc55-73b6b1dddf0f">

### 클래스 다이어그램
1. user
   ![user](https://github.com/yeolsimee/ROUMO-Server/assets/18053020/537064a8-0da5-4536-a3fd-d79c2cd3abee)
2. category
   ![category](https://github.com/yeolsimee/ROUMO-Server/assets/18053020/2ff7cf7b-ddb9-4f38-be5e-c5f02e1e52b0)
3. routine
   ![routine](https://github.com/yeolsimee/ROUMO-Server/assets/18053020/697eb98a-e990-45f7-ad4d-bce92f0e8a72)
4. routineHistory
   ![routinehistory](https://github.com/yeolsimee/ROUMO-Server/assets/18053020/95158047-22fc-4e0f-a5d8-27855dbd6697)
