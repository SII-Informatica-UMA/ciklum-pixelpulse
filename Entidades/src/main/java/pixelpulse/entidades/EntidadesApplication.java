package pixelpulse.entidades;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.qos.logback.classic.Logger;


@SpringBootApplication
public class EntidadesApplication implements CommandLineRunner{


	
	private static Logger LOG = (Logger) LoggerFactory.getLogger(EntidadesApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(EntidadesApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");
 
        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }
		

	}
	

	
}