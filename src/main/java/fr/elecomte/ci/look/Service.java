package fr.elecomte.ci.look;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.h2.server.web.WebServer;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.AnnotatedTypeMetadata;
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
import fr.elecomte.ci.look.services.caches.BadgesCache;
import fr.elecomte.ci.look.services.caches.DefaultBadgesCache;
import fr.elecomte.ci.look.services.caches.OnTheFlyLz4BadgesCache;
import fr.elecomte.ci.look.services.demo.DemoDataLoader;
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

		@Value("${ci-look.badges-cache.type}")
		private String badgesCacheType;

		/* ######################### OPTIONS ######################### */

		/**
		 * @return
		 */
		@Bean
		@Conditional(SwaggerCondition.class)
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
		@Conditional(H2ConsoleCondition.class)
		public Server h2WebConsole() throws SQLException {
			LOGGER.info("H2 CONSOLE activated");
			return new Server(new WebServer(), "-web", "-webAllowOthers", "-webPort", "8082");
		}

		/**
		 * @return
		 */
		@Bean
		@Conditional(DemoCondition.class)
		public DemoDataLoader demoDataLoader() {
			return new DemoDataLoader();
		}

		/* ######################### FEATURES ######################### */

		/**
		 * @return cache of configured type
		 */
		@Bean
		public BadgesCache badgesCache() {

			switch (this.badgesCacheType) {
			case "compressed":
			case "lz4":
				return new OnTheFlyLz4BadgesCache();
			case "none":
			case "disabled":
				return BadgesCache.DISABLED;
			case "default":
			default:
				return new DefaultBadgesCache();
			}
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

	/**
	 * Matcher for enabled H2Console option
	 * 
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static class H2ConsoleCondition extends OptionCondition {

		@Override
		protected String getOptionParam() {
			return "h2console";
		}
	}

	/**
	 * Matcher for enabled Swagger option
	 * 
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static class SwaggerCondition extends OptionCondition {

		@Override
		protected String getOptionParam() {
			return "swagger";
		}
	}

	/**
	 * Matcher for enabled demo option
	 * 
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static class DemoCondition extends OptionCondition {

		@Override
		protected String getOptionParam() {
			return "demo-data";
		}
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static abstract class OptionCondition implements Condition {

		// TODO : Use spring-boot @ContionalOnExpression instead

		public static final String OPTIONS_KEY_PART = "ci-look.options.";

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return context.getEnvironment().getProperty(OPTIONS_KEY_PART + getOptionParam()).equals(Boolean.TRUE);
		}

		/**
		 * @return option param key bellow "ci-look.options."
		 */
		protected abstract String getOptionParam();
	}

	static interface Packages {
		String ROOT = "fr.elecomte.ci.look";
		String JPA_REPOSITORIES = ROOT + ".data.repositories";
		String JPA_ENTITIES = ROOT + ".data.model";
		String PROCESSES = ROOT + ".services.processes";
		String REST = ROOT + ".services.rest";
	}

}
