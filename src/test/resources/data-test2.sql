-- 데이터베이스에 샘플 데이터 삽입

INSERT INTO queue_token (token_id, user_id, concert_id, status, expired_at)
VALUES
  (1, 1, 1, 'EXPIRED', '2024-07-07 12:00:00'),
  (2, 2, 1, 'ACTIVE', '2025-07-13 14:00:00'),
  (3, 3, 2, 'WAITING', '2024-07-13 10:00:00'),
  (4, 5, 1, 'ACTIVE', '2024-07-14 11:00:00'),
  (5, 6, 3, 'ACTIVE', '2025-07-14 11:00:00'),
  (6, 4, 3, 'ACTIVE', '2025-07-14 11:00:00'),
  (7, 10, 2, 'ACTIVE', '2024-07-20 10:00:00');




INSERT INTO concert (concert_id, concert_name, created_at, updated_at)
VALUES
  (1,'콘서트1', '2024-07-05 10:00:00', '2024-07-05 10:00:00'),
  (2,'콘서트2', '2024-07-06 09:30:00', '2024-07-06 09:30:00'),
  (3,'콘서트3', '2024-07-06 09:30:00', '2024-07-06 09:30:00');


INSERT INTO concert_schedule (concert_schedule_id, concert_id, concert_date, location)
VALUES
  (1,1, '2025-07-15 19:00:00', '서울'),
  (2,1, '2025-07-16 19:00:00', '서울'),
  (3,2, '2025-07-17 18:30:00', '부산'),
  (4,3, '2025-07-19 18:30:00', '구미');

INSERT INTO seat (seat_id,concert_schedule_id,seat_number,ticket_price,status,created_at,updated_at)
VALUES
    (1,3,'A01',5000,'AVAILABLE',now(),now()),
    (2,3,'A02',5000,'AVAILABLE',now(),now()),
    (3,3,'A03',5000,'RESERVED',now(),now()),
    (4,3,'A04',5000,'AVAILABLE',now(),now()),
    (5,3,'A05',1555000,'RESERVED',now(),now()),
    (6,3,'B01',5000,'PAID',now(),now()),
    (7,3,'B02',5000,'PAID',now(),now()),
    (8,3,'B03',5000,'AVAILABLE',now(),now()),
    (9,1,'C01',5000,'AVAILABLE',now(),now()),
    (10,1,'C02',5000,'AVAILABLE',now(),now());
