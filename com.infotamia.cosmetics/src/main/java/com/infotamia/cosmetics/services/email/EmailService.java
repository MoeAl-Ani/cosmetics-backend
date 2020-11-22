package com.infotamia.cosmetics.services.email;

import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.EmailConfiguration;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.UnknownException;
import com.infotamia.logger.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author Mohammed Al-Ani
 **/
@ApplicationScoped
public class EmailService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    @ConfigServiceQualifier(ConfigEnum.EMAIL)
    private EmailConfiguration emailConfiguration;
    private Session mailSession;

    public EmailService() {
        //
    }

    @PostConstruct
    public void init() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", emailConfiguration.getSmtpHost());
        properties.setProperty("mail.smtp.port", emailConfiguration.getPort());
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.mime.allowencodedmessages", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.port", emailConfiguration.getPort());
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.ssl.trust", emailConfiguration.getSmtpHost());
        properties.setProperty("mail.debug", String.valueOf(emailConfiguration.isDebug()));
        mailSession = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfiguration.getUserName(), emailConfiguration.getPassword());
            }
        });
    }


    public void send(String emailAddr, String emailSubject, String emailContent) throws UnknownException {
        logger.debug("sending email from {} to {}", "info@infotamia.com", emailAddr);
        MimeMessage msg = new MimeMessage(mailSession);
        logger.debug("creating new MimiMessage");
        setFrom(msg);
        addRecipient(msg, emailAddr);
        setSubject(msg, emailSubject);
        setContent(msg, emailContent, "html");

        try {
            logger.debug("sending email");
            Transport.send(msg);
            logger.debug("sending email succeeded from {} to {}", "info@infotamia.com", emailAddr);

        } catch (MessagingException e) {
            logger.error("sending email failed from {} to {}", "info@infotamia.com", emailAddr);
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private void setFrom(MimeMessage msg) throws UnknownException {
        try {
            msg.setFrom(new InternetAddress("info@infotamia.com", "WomenConsmetics"));
        } catch (AddressException e) {
            throw new UnknownException("Email error in EmailService when setting From: info@infotamia.com", BaseErrorCode.EMAIL_ADDRESS_INCORRECT);
        } catch (MessagingException e) {
            throw new UnknownException("Message error in EmailService when setting From: info@infotamia.com", BaseErrorCode.UNKNOWN);
        } catch (UnsupportedEncodingException e) {
            throw new UnknownException("Encoding error in EmailService when setting From: info@infotamia.com", BaseErrorCode.UNKNOWN);
        }
    }

    private void addRecipient(MimeMessage msg, String to) throws UnknownException {
        try {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        } catch (AddressException e) {
            throw new UnknownException("Email error in EmailService when setting Recipient: " + to, BaseErrorCode.EMAIL_ADDRESS_INCORRECT);
        } catch (MessagingException e) {
            throw new UnknownException("Message error in EmailService when setting Recipient: " + to, BaseErrorCode.UNKNOWN);
        }
    }

    private void setSubject(MimeMessage msg, String subject) throws UnknownException {
        try {
            msg.setSubject(subject, "UTF-8");
        } catch (MessagingException e) {
            throw new UnknownException("Message error in EmailService when setting Subject: " + subject, BaseErrorCode.UNKNOWN);
        }
    }

    private void setContent(MimeMessage msg, String content, String contentType) throws UnknownException {
        try {
            if (StringUtils.isNotBlank(contentType)) {
                msg.setText(content, "UTF-8", contentType);
            } else msg.setText(content, "UTF-8");
        } catch (MessagingException e) {
            throw new UnknownException("Message error in EmailService when setting Content: " + content, BaseErrorCode.UNKNOWN);
        }
    }
}
