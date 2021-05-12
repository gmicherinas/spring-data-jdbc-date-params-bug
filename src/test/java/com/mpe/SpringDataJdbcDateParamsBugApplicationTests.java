package com.mpe;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@AutoConfigureJdbc
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = SpringDataJdbcDateParamsBugApplicationTests.DockerPostgreDataSourceInitializer.class)
@ComponentScan(basePackages = "com.mpe")
@SpringBootTest
class SpringDataJdbcDateParamsBugApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringDataJdbcDateParamsBugApplicationTests.class);

	public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:12.4-alpine");

	static {
		postgreDBContainer.withInitScript("initdb.sql");
		postgreDBContainer.start();
	}

	@SuppressWarnings({ "FieldCanBeLocal", "unused" })
	private final JdbcTemplate jdbcTemplate;
	private final MonitoringRepository monitoringRepository;

	@Autowired
	public SpringDataJdbcDateParamsBugApplicationTests(JdbcTemplate jdbcTemplate, MonitoringRepository monitoringRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.monitoringRepository = monitoringRepository;
	}

	@Test
	void given_nativeUpdateQuer_WithLocaDateTimeParam_WithoutCast_ShouldWork() {
		LOGGER.debug("1/5: Monitoring with id 1: {}", monitoringRepository.findById(1L));
		monitoringRepository.updateWithCast(1L, LocalDateTime.now());
		LOGGER.debug("2/5: Monitoring with id 1 after native query with CAST: {}", monitoringRepository.findById(1L));
	}

	@Test
	void given_nativeUpdateQuer_WithLocaDateTimeParam_AndCast_ShouldWork() {
		LOGGER.debug("Monitoring with id 1: {}", monitoringRepository.findById(1L));
		monitoringRepository.updateWithoutCast(1L, LocalDateTime.now().plusMinutes(2));
		LOGGER.debug("Monitoring with id 1 after native query without CAST and LocalDateTime: {}", monitoringRepository.findById(1L));
	}


	public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(@NonNull  ConfigurableApplicationContext applicationContext) {
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					applicationContext,
					"spring.datasource.url=" + postgreDBContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgreDBContainer.getUsername(),
					"spring.datasource.password=" + postgreDBContainer.getPassword()
			);
		}
	}
}