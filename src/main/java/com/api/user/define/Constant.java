package com.api.user.define;

import com.api.user.uitls.ConfigurationLoader;

public class Constant {
    public static final class MailConfiguration {
        public static final String MAIL_USERNAME = ConfigurationLoader.getInstance().getAsString("mail.username", "quanghuongitus@gmail.com");
        public static final String MAIL_PASSWORD = ConfigurationLoader.getInstance().getAsString("mail.password", "hmzmmvfxsjtcpzde");
        public static final String MAIL_HOST = ConfigurationLoader.getInstance().getAsString("mail.host", "125.235.240.36");
        public static final int MAIL_PORT = ConfigurationLoader.getInstance().getAsInteger("mail.port", 465);

        public static final int MAIL_SMTP_SOCKET_FACTORY_PORT = ConfigurationLoader.getInstance().getAsInteger("mail.smtp.socketFactory.port", 465);
        public static final boolean MAIL_SMTP_AUTH = ConfigurationLoader.getInstance().getAsBoolean("mail.smtp.auth", true);
        public static final boolean MAIL_SMTP_STARTTLS_ENABLE = ConfigurationLoader.getInstance().getAsBoolean("mail.smtp.starttls.enable", true);
        public static final String MAIL_SMTP_SSL_TRUST = ConfigurationLoader.getInstance().getAsString("mail.smtp.ssl.trust", "*");
        public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = ConfigurationLoader.getInstance().getAsString("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        public static final boolean MAIL_SMTP_SOCKETFACTORY_FALLBACK = ConfigurationLoader.getInstance().getAsBoolean("mail.smtp.socketFactory.fallback", false);
        public static final String MAIL_STORE_PROTOCOL = ConfigurationLoader.getInstance().getAsString("mail.store.protocol", "pop3");
        public static final String MAIL_TRANSPORT_PROTOCOL = ConfigurationLoader.getInstance().getAsString("mail.transport.protocol", "smtp");
    }
}
