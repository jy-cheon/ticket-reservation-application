INSERT INTO queue_token (token_id, user_id, concert_id, status, expired_at)
VALUES
    (1, 1, 1, 'EXPIRED', '2024-07-07 12:00:00'),
    (2, 2, 1, 'ACTIVE', '2025-07-13 14:00:00'),
    (3, 3, 2, 'WAITING', '2024-07-13 10:00:00'),
    (4, 5, 1, 'ACTIVE', '2024-07-14 11:00:00'),
    (5, 6, 3, 'ACTIVE', '2025-07-14 11:00:00'),
    (6, 4, 3, 'ACTIVE', '2025-07-14 11:00:00');