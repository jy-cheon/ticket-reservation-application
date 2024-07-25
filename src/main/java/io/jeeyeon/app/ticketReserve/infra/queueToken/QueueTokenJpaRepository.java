package io.jeeyeon.app.ticketReserve.infra.queueToken;

import io.jeeyeon.app.ticketReserve.domain.queueToken.TokenStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QueueTokenJpaRepository extends JpaRepository<QueueTokenEntity, Long> {

    List<QueueTokenEntity> findByConcertIdAndStatus(Long concertId, TokenStatus status);

    List<QueueTokenEntity> findByUserIdAndConcertId(Long userId, Long concertId);

    @Query(value = "SELECT COALESCE(MAX(SEQUENCE_ID), 0) + 1 FROM QUEUE_TOKEN WHERE CONCERT_ID = :concertId", nativeQuery = true)
    Long getNextSequenceIdForConcert(@Param("concertId") Long concertId);

    List<QueueTokenEntity> findByStatusAndExpiredAtBefore(TokenStatus status, LocalDateTime now);

    @Query("SELECT q FROM QueueTokenEntity q WHERE q.concertId = :concertId AND q.status = :status ORDER BY q.createdAt ASC")
    List<QueueTokenEntity> findTopNByConcertIdAndStatusOrderByCreatedAtAsc(
            @Param("concertId") Long concertId,
            @Param("status") TokenStatus status,
            Pageable pageable
    );

    @Query("SELECT q FROM QueueTokenEntity q WHERE q.concertId = :concertId AND q.tokenId = :tokenId")
    Optional<QueueTokenEntity> findByTokenIdAndConcertId(@Param("concertId") Long concertId, @Param("tokenId") Long tokenId);

    @Query("SELECT COUNT(t) FROM QueueTokenEntity t WHERE t.concertId = :concertId AND t.sequenceId < :sequenceId AND t.status = :status")
    Long findWaitingAheadCount(Long concertId, Long sequenceId, TokenStatus status);

}
