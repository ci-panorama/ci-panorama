package fr.elecomte.ci.look;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.h2.server.web.WebServer;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.elecomte.ci.look.Service.Packages;
import fr.elecomte.ci.look.Service.ServiceConfiguration;
import fr.elecomte.ci.look.services.processes.ServerInformation;
import fr.elecomte.ci.look.services.rest.mappers.JsonPayloadModule;
import fr.elecomte.ci.look.services.rest.mappers.LocalDateModule;
import fr.elecomte.ci.look.services.rest.mappers.LocalDateTimeModule;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author elecomte
 * @since 0.1.0
 */
@EnableSwagger2
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(Packages.JPA_REPOSITORIES)
@EntityScan(Packages.JPA_ENTITIES)
@Import(ServiceConfiguration.class)
@ComponentScan({ Packages.ROOT })
public class Service {

	static final Logger LOGGER = LoggerFactory.getLogger(Service.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LOGGER.debug("######################### starting CI-LOOK ############################");

		ServerInformation server = SpringApplication.run(Service.class, args).getBean(ServerInformation.class);

		LOGGER.info("CI-LOOK v{} \"{}\", build {}", server.getVersion(), server.getCodeName(), server.getBuild());
		
		LOGGER.info("########################### CI-LOOK started ############################");
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	@Configuration
	public static class ServiceConfiguration extends WebMvcConfigurerAdapter {

		/**
		 * @return
		 */
		@Bean
		@Profile(Profiles.SWAGGER_UI)
		public Docket swaggerApi() {
			LOGGER.info("SWAGGER UI activated");
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.regex("/.*"))
					.build()
					.directModelSubstitute(LocalDate.class, String.class)
					.directModelSubstitute(LocalDateTime.class, String.class);
		}

		/**
		 * @return
		 * @throws SQLException
		 */
		@Bean(initMethod = "start", destroyMethod = "stop")
		@Profile(Profiles.H2_CONSOLE)
		public Server h2WebConsole() throws SQLException {
			LOGGER.info("H2 CONSOLE activated");
			return new Server(new WebServer(), "-web", "-webAllowOthers", "-webPort", "8082");
		}

		/**
		 * @return
		 */
		@Bean
		public freemarker.template.Configuration freemarkerConfiguration() {
			String resourcesLocation = "/badges/svg";

			freemarker.template.Configuration freemarkerConfiguration = new freemarker.template.Configuration(
					freemarker.template.Configuration.VERSION_2_3_25);

			freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), resourcesLocation);

			LOGGER.debug("Freemarker configuration loaded with resources from {}", resourcesLocation);

			return freemarkerConfiguration;
		}

		/**
		 * @param converters
		 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureMessageConverters(java.util.List)
		 */
		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

			// http
			StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
			converters.add(stringConverter);

			// string
			FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
			converters.add(formConverter);

			// json
			MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
			converters.add(jsonConverter);
			setModules(jsonConverter.getObjectMapper());

			// xml
			MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
			converters.add(xmlConverter);
			setModules(xmlConverter.getObjectMapper());

			LOGGER.debug("Jackson modules configured");
		}

		/**
		 * @param mapper
		 */
		private static void setModules(ObjectMapper mapper) {
			mapper.registerModule(new LocalDateModule());
			mapper.registerModule(new LocalDateTimeModule());
			mapper.registerModule(new JsonPayloadModule());

			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		}
	}

	static interface Packages {
		String ROOT = "fr.elecomte.ci.look";
		String JPA_REPOSITORIES = ROOT + ".data.repositories";
		String JPA_ENTITIES = ROOT + ".data.model";
		String PROCESSES = ROOT + ".services.processes";
		String REST = ROOT + ".services.rest";
	}

	static interface Profiles {
		String H2_CONSOLE = "h2console";
		String SWAGGER_UI = "swagger";
	}
}
