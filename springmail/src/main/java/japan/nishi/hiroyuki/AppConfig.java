package japan.nishi.hiroyuki;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import japan.nishi.hiroyuki.mail.GmailReceiver;
import japan.nishi.hiroyuki.mail.MailReceiver;

@Configuration
public class AppConfig {

//	@Bean
	IExecutableService getMailReceiver() {
		return new MailReceiver();
		
	}
	
	@Bean
	IExecutableService getGmailReceiver() {
		return new GmailReceiver();
	}
}
