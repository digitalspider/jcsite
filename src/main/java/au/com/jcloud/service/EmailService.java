package au.com.jcloud.service;

import java.util.Properties;

import javax.mail.Message;

import org.apache.commons.lang3.StringUtils;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;

/**
 * Created by david.vittor on 4/08/16.
 */
public class EmailService {

	public static final String EMAIL_PROPERTIES = "email.properties";
	public static final String PROP_EMAIL_HOST = "email.host";
	public static final String PROP_EMAIL_PORT = "email.port";
	public static final String PROP_EMAIL_USERNAME = "email.username";
	public static final String PROP_EMAIL_PASSWORD = "email.password";
	public static final String PROP_DEFAULT_FROM_USERNAME = "default.from.username";
	public static final String PROP_DEFAULT_FROM_EMAIL = "default.from.email";

	private static Properties properties = new Properties();
	private static PropertyReaderService propertyReaderService = new PropertyReaderService();
	private static Mailer mailer;

	public EmailService() throws Exception {
		if (properties.isEmpty() || mailer == null) {
			init();
		}
	}

	public void init() throws Exception {
		properties = propertyReaderService.loadProperties(EMAIL_PROPERTIES);
		validateProperties(properties);

		mailer = new Mailer(
				new ServerConfig(properties.getProperty(PROP_EMAIL_HOST),
						Integer.parseInt(properties.getProperty(PROP_EMAIL_PORT)),
						properties.getProperty(PROP_EMAIL_USERNAME),
						properties.getProperty(PROP_EMAIL_PASSWORD)),
				TransportStrategy.SMTP_TLS);
	}

	public void validateProperties(Properties properties) throws Exception {
		if (StringUtils.isBlank(properties.getProperty(PROP_EMAIL_HOST))) {
			throw new Exception("Property " + PROP_EMAIL_HOST + " has not been set! Please update " + EMAIL_PROPERTIES);
		}
		if (StringUtils.isBlank(properties.getProperty(PROP_EMAIL_PORT))) {
			throw new Exception("Property " + PROP_EMAIL_PORT + " has not been set! Please update " + EMAIL_PROPERTIES);
		}
		if (Integer.parseInt(properties.getProperty(PROP_EMAIL_PORT)) < 0) {
			throw new Exception("Property " + PROP_EMAIL_PORT + " needs to be a positive number! Please update " + EMAIL_PROPERTIES);
		}
		if (StringUtils.isBlank(properties.getProperty(PROP_EMAIL_USERNAME))) {
			throw new Exception("Property " + PROP_EMAIL_USERNAME + " has not been set! Please update " + EMAIL_PROPERTIES);
		}
		if (StringUtils.isBlank(properties.getProperty(PROP_EMAIL_PASSWORD))) {
			throw new Exception("Property " + PROP_EMAIL_PASSWORD + " has not been set! Please update " + EMAIL_PROPERTIES);
		}
	}

	public void sendEmail(String toName, String toEmail, String subject, String message) throws Exception {
		if (StringUtils.isBlank(properties.getProperty(PROP_DEFAULT_FROM_EMAIL))) {
			throw new Exception("Property " + PROP_DEFAULT_FROM_EMAIL + " has not been set! Please update " + EMAIL_PROPERTIES);
		}
		if (StringUtils.isBlank(properties.getProperty(PROP_DEFAULT_FROM_USERNAME))) {
			throw new Exception("Property " + PROP_DEFAULT_FROM_USERNAME + " has not been set! Please update " + EMAIL_PROPERTIES);
		}
		String fromName = properties.getProperty(PROP_DEFAULT_FROM_USERNAME);
		String fromEmail = properties.getProperty(PROP_DEFAULT_FROM_EMAIL);
		sendEmail(fromName, fromEmail, toName, toEmail, subject, message);
	}

	public void sendEmail(String fromName, String fromEmail, String toName, String toEmail, String subject, String message) throws Exception {
		Email emailMessage = new Email();
		emailMessage.addRecipient(toName, toEmail, Message.RecipientType.TO);
		emailMessage.setFromAddress(fromName, fromEmail);
		emailMessage.setReplyToAddress(fromName, fromEmail);
		emailMessage.setSubject(subject);
		emailMessage.setText(message);

		mailer.sendMail(emailMessage);
	}
}
