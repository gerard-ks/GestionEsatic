package ci.esatic;

import ci.esatic.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GestionEsaticApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEsaticApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }


	/*@Bean
	CommandLineRunner commandLineRunnerTest(JdbcUserDetailsManager jdbcUserDetailsManager)
	{
		PasswordEncoder passwordEncoder = passwordEncoder();
		return args -> {
			jdbcUserDetailsManager.createUser(
					User.withUsername("admin1").password(passwordEncoder.encode("admin@01")).roles("ADMIN","ENSEIGNANT","ETUDIANT").build()
			);
			jdbcUserDetailsManager.createUser(
					User.withUsername("enseignant1").password(passwordEncoder.encode("enseignant@01")).roles("ENSEIGNANT").build()
			);
			jdbcUserDetailsManager.createUser(
					User.withUsername("etudiant1").password(passwordEncoder.encode("etudiant@01")).roles("ETUDIANT").build()
			);
		};
	}*/

	/*@Bean
	CommandLineRunner commandLineNiveau(NiveauRepository niveauRepository)
	{
		return args -> {
			niveauRepository.save(new Niveau(null,"Licence 1"));
			niveauRepository.save(new Niveau(null,"Licence 2"));
			niveauRepository.save(new Niveau(null,"Licence 3"));
			niveauRepository.save(new Niveau(null,"Master 1"));
			niveauRepository.save(new Niveau(null,"Master 2"));
			niveauRepository.save(new Niveau(null,"Doctorat 1"));
			niveauRepository.save(new Niveau(null,"Doctorat 2"));
			niveauRepository.save(new Niveau(null,"Doctorat 3"));
		};
	}*/


	@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService)
	{
		return args -> {
	/*		accountService.addNewRole("ADMIN");
			accountService.addNewRole("ENSEIGNANT");
			accountService.addNewRole("ETUDIANT");

			accountService.addNewUser("admin1", "qwerty1", "admin1@mail.ci", "qwerty1");
			accountService.addNewUser("user1", "qwerty2", "user1@mail.ci", "qwerty2");
			accountService.addNewUser("user2", "qwerty3", "user2@mail.ci", "qwerty3");
*/
			/*accountService.addRoleToUser("admin1", "ADMIN");
			accountService.addRoleToUser("admin1", "ENSEIGNANT");
			accountService.addRoleToUser("admin1", "ETUDIANT");

			accountService.addRoleToUser("user1", "ENSEIGNANT");*/
			//accountService.addRoleToUser("test1", "ETUDIANT");
			//accountService.removeRoleFromUser("test1", "ETUDIANT");
			//accountService.addNewUser("admin1", "qwerty1", "admin1@mail.ci", "qwerty1");
			//accountService.addRoleToUser("admin1", "ADMIN");
		};
	}

}
