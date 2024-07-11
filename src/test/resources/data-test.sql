-- 데이터베이스에 샘플 데이터 삽입

INSERT INTO queue_token (token_id, user_id, concert_id, status, expired_at)
VALUES
  (1, 1, 1, 'EXPIRED', '2024-07-07 12:00:00'),
  (2, 2, 1, 'ACTIVE', '2024-07-13 14:00:00'),
  (3, 3, 2, 'WAITING', '2024-07-13 10:00:00'),
  (4, 5, 1, 'ACTIVE', '2024-07-14 11:00:00');


INSERT INTO concert (concert_name, created_at, updated_at)
VALUES
  ('콘서트1', '2024-07-05 10:00:00', '2024-07-05 10:00:00'),
  ('콘서트2', '2024-07-06 09:30:00', '2024-07-06 09:30:00');


INSERT INTO concert_schedule (concert_id, concert_date, location)
VALUES
  (1, '2024-07-15 19:00:00', '서울'),
  (1, '2024-07-16 19:00:00', '서울'),
  (2, '2024-07-17 18:30:00', '부산');



