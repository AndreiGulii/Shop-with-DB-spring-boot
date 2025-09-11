package ait.cohort63.shop.service.interfaces;

import ait.cohort63.shop.model.entity.User;

public interface EmailService {

    // Metod otpravki pisima s kodom podtverjdenija
    void sendConfirmationEmail(User user);
}
