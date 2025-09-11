package ait.cohort63.shop.service;

import ait.cohort63.shop.model.entity.User;
import ait.cohort63.shop.service.interfaces.ConfirmationCodeService;
import ait.cohort63.shop.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final Configuration mailConfig;

    private final ConfirmationCodeService confirmationService;

    private final static String HOST = "http://localhost:8080/api";

    public EmailServiceImpl(JavaMailSender mailSender, Configuration configuration, ConfirmationCodeService confirmationService) {
        this.mailSender = mailSender;
        this.mailConfig = configuration;
        this.confirmationService = confirmationService;

        //Nastroika kodirovki i raspolojenije shablonov
        this.mailConfig.setDefaultEncoding("UTF-8");
        this.mailConfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/mail"));
    }

    @Override
    public void sendConfirmationEmail(User user) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            //Adres otpravitelea. Poluchaiem iz peremennoi sredi
            String fromAddress = System.getenv("MAIL_USERNAME");

            //Ukazivaiem otpravitelea
            helper.setFrom(fromAddress);

            // Ukazivaiem Poluchatelea
            helper.setTo(user.getEmail());
            helper.setSubject("registration Confirmation");
            String emailText = generateEmailText(user);
            helper.setText(emailText, true);

            mailSender.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateEmailText(User user){

        try {
            // Zagruzka shablona
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");

            // Generatsiia koda podtverjdenija i sohranenije koda v DB
            String code = confirmationService.generateConfirmationCode(user);

            // Formirujem ssilku dle podtverjdenija registratsii
            // tipo etoi http://localhost:8080/api/confirm?code=znachenije_coda
            String confirmationLink = HOST + "/confirm?code=" + code;

            //Vstavleaiem dannije polizovatelea (imea i ssilku) Modeli - {name : value, confirmationLink :value}
              Map<String, Object> model = new HashMap<>();
              model.put("name", user.getUsername());
              model.put("confirmationLink", confirmationLink);

              // Generiruiem i vozvrasheaiem tekst pisima
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        } catch (IOException |TemplateException e) {
            throw new RuntimeException(e);
        }

    }
}
