package com.faculty.fxcontrollers.email;

import com.faculty.model.Users;
import com.faculty.model.VerificationCode;
import com.faculty.support.MailerUtil;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
public class PasswordResetEmail {
    private Users user;
    private VerificationCode codeToVerify;
    private String emailSubject;
    private String emailBody;
    private MailerUtil mailerUtil;
    private Mailer mailer;

    public PasswordResetEmail(Users usr, VerificationCode vCode) {
        this(usr, vCode, null, null);
    }

    public PasswordResetEmail(Users usr, VerificationCode vCode, String subject, String body) {
        user = usr;
        codeToVerify = vCode;
        mailerUtil = MailerUtil.getInstance();
        mailer = mailerUtil.buildMailer();
        emailSubject = subject;
        emailBody = body;
    }

    private String getSubject() {
        String subject = "Reset your password";

        if (null != emailSubject) {
            subject = emailSubject;
        }

        return subject;
    }

    private String getBodyText() {
        String bodyText = "Use %s code to reset your password.";

        if (null != emailBody) {
            bodyText = emailBody;
        }

        return String.format(bodyText, codeToVerify.asString());
    }

    public void send() {
        Email email = EmailBuilder.startingBlank()
                .from(mailerUtil.getSender(), mailerUtil.getUsername())
                .to(user.getFirstName(), user.getEmail())
                .withSubject(getSubject())
                .withPlainText(getBodyText())
                .buildEmail();

        mailer.sendMail(email);
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public VerificationCode getCodeToVerify() {
        return codeToVerify;
    }

    public void setCodeToVerify(VerificationCode codeToVerify) {
        this.codeToVerify = codeToVerify;
    }
}
