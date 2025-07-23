package za.co.simplitate.hotelbooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.simplitate.hotelbooking.entities.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    // Define methods for payment-related operations here
    // For example:
    // List<Payment> findPaymentsByUserId(Long userId);
    // Optional<Payment> findPaymentByTransactionId(String transactionId);
    // boolean existsByBookingId(Long bookingId);

    // Add custom queries as needed
}
