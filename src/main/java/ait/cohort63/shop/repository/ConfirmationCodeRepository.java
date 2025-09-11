package ait.cohort63.shop.repository;

import ait.cohort63.shop.model.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    // Metod dlea poiska koda po ego poliu kod

    Optional<ConfirmationCode> findByCode(String code);

}
