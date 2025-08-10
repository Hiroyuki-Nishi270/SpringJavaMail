package japan.nishi.hiroyuki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringConsoleApplication {
	
	@Autowired
	IExecutableService executableService;

	// main method
	public static void main(String[] args) {
		
		// call execute method
		SpringApplication.run(SpringConsoleApplication.class, args)
		.getBean(SpringConsoleApplication.class)
		.execute();

	}
	
	// actual processing
	private void execute() {
		executableService.execute();
	}

}
