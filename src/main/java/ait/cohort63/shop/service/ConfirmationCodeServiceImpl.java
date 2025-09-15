package ait.cohort63.shop.service;

import ait.cohort63.shop.model.entity.ConfirmationCode;
import ait.cohort63.shop.model.entity.User;
import ait.cohort63.shop.repository.ConfirmationCodeRepository;
import ait.cohort63.shop.service.interfaces.ConfirmationCodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    private final ConfirmationCodeRepository repository;

    public ConfirmationCodeServiceImpl(ConfirmationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateConfirmationCode(User user) {

        // Generatsiia unikalinogo koda pri pomoshi UUID
        String code = UUID.randomUUID().toString();

        //sozdaiu obiekt confirmation coda i sohraniaiu ego v bazu
        ConfirmationCode confirmationCode = new ConfirmationCode();
        confirmationCode.setCode(code);
        confirmationCode.setUser(user);
        confirmationCode.setExpired(LocalDateTime.now().plusDays(2)); // ustanavlivaiem srok deistviia koda aktivatsii

        repository.save(confirmationCode);//sohraniaem kod v BD

        return code; // Vozvrashiaem sgenerirovanii kod
    }
}
