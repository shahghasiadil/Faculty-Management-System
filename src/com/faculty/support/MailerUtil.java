package com.faculty.support;

import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import java.util.ResourceBundle;

public class MailerUtil {
    private static MailerUtil instance;

    private String smtpServer;
    private Integer smtpPort;
    private String username;
    private String secret;
    private String sender;
    private TransportStrategy transportStrategy;
    private Boolean debugLogging;
    private ResourceBundle resources;

    private MailerUtil() {
        // Default SMTP Settings
        smtpServer = "smtp.gmail.com";
        smtpPort = 587;
        username =  "shaghasy555@gmail.com";
        secret = "yjcozajityvjnggk";
        sender = "App Name";
        transportStrategy = TransportStrategy.SMTP_TLS;
        debugLogging = true;
    }


    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public TransportStrategy getTransportStrategy() {
        return transportStrategy;
    }

    public void setTransportStrategy(TransportStrategy transportStrategy) {
        this.transportStrategy = transportStrategy;
    }

    public Boolean getDebugLogging() {
        return debugLogging;
    }

    public void setDebugLogging(Boolean debugLogging) {
        this.debugLogging = debugLogging;
    }

    public Mailer buildMailer() {
        Mailer mailer = MailerBuilder
                .withSMTPServer(smtpServer, smtpPort, username, secret)
                .withTransportStrategy(transportStrategy)
                .withDebugLogging(debugLogging)
                .buildMailer();

        return mailer;
    }

    public static MailerUtil getInstance() {
        if (null == instance) {
            instance = new MailerUtil();
        }

        return instance;
    }

    public static Mailer getMailer() {
        return getInstance().buildMailer();
    }
}
