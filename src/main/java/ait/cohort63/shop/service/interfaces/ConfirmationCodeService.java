package ait.cohort63.shop.service.interfaces;

import ait.cohort63.shop.model.entity.User;

public interface ConfirmationCodeService {

    // metod dlea generatsii koda podtverjdenija
    String generateConfirmationCode(User user);
}
