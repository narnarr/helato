# Snowflake

총 64bit(time 41, machine id 10, sequence number 12)으로 구성된 ID Generator.

### 개발 목적
- PK 타입이 문자열보다 숫자일 때 공간효율성도 높고 비교연산 속도도 더 빠르다.
- PK를 정렬할 수 있으면 좋다.
  - `created_at`과 같은 필드도 있지만, 해당 필드에 따로 인덱스를 거는 일은 부담이 될 수 있다.
  - DB 파티셔닝이 용이해진다.

### 구현 시 고려사항
- 시간이 거꾸로 흐르면 안된다.
- sequence가 다 차면 다음 millis까지 대기해야 한다.
- 멀티 스레드 환경에서 동시에 같은 ID를 생성하지 못하도록 동시성 제어를 해야 한다.

### 참고
- Twitter Snowflake : https://github.com/twitter-archive/snowflake/tree/snowflake-2010
- Snowflake in Java : https://github.com/callicoder/java-snowflake/blob/master/src/main/java/com/callicoder/snowflake/Snowflake.java
