# 인덱스 분석

인덱스별 속도비교와 EXPLAIN 명령어를 고려하여 최적의 성능을 위한 인덱스를 설계하였다.
### 1) 특정 스케줄의 가능한 좌석 조회

![요약.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F%EC%9A%94%EC%95%BD.png)

**인덱스 미사용시**
![0번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F0%EB%B2%88.png)

**idx_concert_schedule_id 사용**
![1번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F1%EB%B2%88.png)

**idx_concert_schedule_id, idx_status 사용**
![2번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F2%EB%B2%88.png)

**idx_concert_schedule_id_status 사용(최종선택)**
![3 번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F1%EB%B2%88%2F3%20%EB%B2%88.png)


### 2) 특정 스케줄의 특정 좌석 조회

![정리.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F%EC%A0%95%EB%A6%AC.png)

**인덱스 미사용시**
![0번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F0%EB%B2%88.png)

**idx_concert_schedule_id 사용**
![1번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F1%EB%B2%88.png)

**idx_concert_schedule_id_seat_number 사용**
![2번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F2%EB%B2%88.png)

**idx_seat_number 사용(최종선택)**
![3번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F2%EB%B2%88%2F3%EB%B2%88.png)


### 3) 예약,결제 조인 조회

![정리.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F%EC%A0%95%EB%A6%AC.png)

**인덱스 미사용시**
![1 번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F1%20%EB%B2%88.png)

**idx_status_created_at 사용**
![2번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F2%EB%B2%88.png)

**idx_status_created_at, idx_reservation_id 사용**
![3번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F3%EB%B2%88.png)

**idx_reservation_id 사용(최종선택)**
![4번.png](document%2F%EC%9D%B8%EB%8D%B1%EC%8A%A4%2F3%EB%B2%88%2F4%EB%B2%88.png)










