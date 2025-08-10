package japan.nishi.hiroyuki.mail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Binding mailaccount.properties file.
 */
@Component
@ConfigurationProperties
@PropertySource("classpath:mailaccount.properties")
public class MailProperty extends Properties{
	
	@Value("${mail.store.protocol}")
	@Getter
	private String protocol;

	@Value("${mail.imaps.host}")
	@Getter
	private String host;

	@Value("${mail.imaps.port}")
	@Getter
	private String port;
	
	@Value("${mail.imaps.starttls.enable}")
	@Getter
	private String enableStartTls;
	
	@Value("${mailaccount.username}")
	@Getter
	private String username;
	
	@Value("${mailaccount.password}")
	@Getter
	private String password;
	 
}
