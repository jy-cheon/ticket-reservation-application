# 콘서트 예약 시스템 API 명세서

## 1. 유저 토큰 발급 API

### Endpoint 
POST /token


### 요청

```json
{
  "uuid": "dsjfl2kjd0kkdslfkj09"
}

```

### 응답
```json
{
  "token": "jeeyeon.iuconcert01.2024-07-04"
}
```
설명 : 사용자 인증을 통해 토큰을 발급받습니다.


## 2-1. 예약 가능 날짜 API

### Endpoint
POST /concerts/{concertId}/dates


### 요청
Authorization: {token}

### 응답
```json
{
  "datesResponse":[
    "2024-07-05",
    "2024-07-06",
    "2024-07-07"
  ]
}
```
설명 : 예약 가능 날짜 조회 리스트를 받는다

## 2-2. 예약 가능 좌석 API

### Endpoint
POST /concerts/{concertId}/dates/{date}/seats


### 요청
Authorization: {token}

### 응답
```json
{
  "availableSeats": [
    {
      "seatId": "1",
      "seatNumber": "b48",
      "price": 80000
    },
    {
      "seatId": "2",
      "seatNumber": "c32",
      "price": 60000
    },
    {
      "seatId": "3",
      "seatNumber": "a17",
      "price": 75000
    }
  ]
}

```
설명 : 특정 날짜에 가능한 좌석 정보 리스트를 받는다.



## 3. 좌석 예약 요청 API

### Endpoint
POST /concerts/reservation

### 요청
Authorization: {token}
```json
{
  "userId": "string",
  "concertId": "string",
  "date": "string",
  "seatNumber": "string"
}
```
### 응답
```json
{
  "reservationId": "ABC123",
  "expirationTime": "2024-07-05T03:41:08.090557"
}
```
설명 : 예약 신청 후 5분간 임시 배정을 받으며, 임시배정 종료시간을 응답받는다.

## 4-1. 잔액 조회 API

### Endpoint
GET /users/{userId}/balance

### 요청


### 응답
```json
{
  "userId": "user01",
  "balance": 50000
}
```
설명 : 현재 가지고 있는 잔액 확인이다.

## 4-2. 잔액 충전 API

### Endpoint
POST /users/charge

### 요청

```json
{
  "userId": "user",
  "amount" :  3000
}
```
### 응답
```json
{
  "userId": "user",
  "balance": 500000
}
```
설명 : 결제에서 사용할 잔액을 충전한다.


## 5. 결제 API

### Endpoint
POST /payment

### 요청
Authorization: {token}
```json
{
  "reservationId": "reserv03334",
  "amount" : 230000
}
```
### 응답
```json
{
  "paymentId": "PAY123456",
  "paymentTime": "2024-07-05T04:54:26.726285"
}
```
설명 : 예약된 콘서트를 결제한다.이때 해당 토큰은 만료된다.


