package io.jeeyeon.app.ticketReserve.infra.reservation;

import io.jeeyeon.app.ticketReserve.domain.reservation.Reservation;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public void save(Reservation reservation) {
        reservationJpaRepository.save(new ReservationEntity(reservation));
    }

    @Override
    public List<Reservation> findUnpaidReservations(LocalDateTime fiveMinutesAgo) {
        List<ReservationEntity> reservationEntities = reservationJpaRepository.findUnpaidReservations(fiveMinutesAgo);
        return reservationEntities.stream()
                .map(ReservationEntity::toReservation)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return reservationJpaRepository.findById(reservationId).stream()
                .map(ReservationEntity::toReservation).findAny();
    }
}
