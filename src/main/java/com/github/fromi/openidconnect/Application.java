package com.github.fromi.openidconnect;

import org.glycoinfo.rdf.SparqlException;
import org.glycoinfo.rdf.dao.SparqlDAO;
import org.glycoinfo.rdf.dao.SparqlDAOSesameImpl;
import org.glycoinfo.rdf.scint.ClassHandler;
import org.glycoinfo.rdf.scint.InsertScint;
import org.glycoinfo.rdf.scint.SelectScint;
import org.glycoinfo.rdf.service.UserProcedure;
import org.glycoinfo.rdf.utils.TripleStoreProperties;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.spring.RepositoryConnectionFactory;
import org.openrdf.spring.SesameConnectionFactory;
import org.openrdf.spring.SesameTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ScopedProxyMode;

import com.github.fromi.openidconnect.security.SecurityConfiguration;

@ComponentScan(scopedProxy = ScopedProxyMode.INTERFACES, basePackages = {"com.github.fromi.openidconnect", "org.glycoinfo.rdf.scint"}, excludeFilters={
		  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=Configuration.class)})
//@ComponentScan(basePackages = {"com.github.fromi.openidconnect"})
//@ComponentScan(basePackages = {"org.glycoinfo.rdf.service", "org.glycoinfo.rdf.scint", "com.github.fromi.openidconnect"})
//@SpringApplicationConfiguration(classes = {SesameDAOTestConfig.class})
@SpringBootApplication
//@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    
	@Bean
	SparqlDAO getSparqlDAO() {
		return new SparqlDAOSesameImpl();
	}
	
	@Bean(name = "userProcedure")
	UserProcedure getUserProcedure() throws SparqlException {
   	 	logger.debug("url:>" + url + "<");
		UserProcedure user = new org.glycoinfo.rdf.service.impl.UserProcedure();
		return user;
	}
    
	@Bean(name = "selectscintperson")
	SelectScint getSelectPersonScint() throws SparqlException {
		SelectScint select = new SelectScint();
		
		select.setClassHandler(getPersonClassHandler());
		return select;
	}

	@Bean(name = "insertscintperson")
	InsertScint getInsertPersonScint() throws SparqlException {
		InsertScint insert = new InsertScint("http://rdf.glytoucan.org/users");
		insert.setClassHandler(getPersonClassHandler());
		return insert;
	}

	@Bean(name = "selectscintregisteraction")
	SelectScint getSelectRegisterActionScint() throws SparqlException {
		SelectScint select = new SelectScint();
		select.setClassHandler(getRegisterActionClassHandler());
		return select;
	}

	@Bean(name = "insertscintregisteraction")
	InsertScint getInsertRegisterActionScint() throws SparqlException {
		InsertScint insert = new InsertScint("http://rdf.glytoucan.org/users");
		insert.setClassHandler(getRegisterActionClassHandler());
		return insert;
	}

	ClassHandler getPersonClassHandler() throws SparqlException {
		ClassHandler ch = new ClassHandler("schema", "http://schema.org/", "Person");
		ch.setSparqlDAO(getSparqlDAO());
		return ch; 
	}
	
	ClassHandler getRegisterActionClassHandler() throws SparqlException {
		ClassHandler ch = new ClassHandler("schema", "http://schema.org/", "RegisterAction");
		ch.setSparqlDAO(getSparqlDAO());
		return ch; 
	}
	
	ClassHandler getDateTimeClassHandler() throws SparqlException {
		ClassHandler ch = new ClassHandler("schema", "http://schema.org/", "DateTime");
		ch.setSparqlDAO(getSparqlDAO());
		return ch; 
	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
    @Value("${triplestore.sesame.url}")
    private String url;
	
	@Bean
	SesameConnectionFactory getSesameConnectionFactory() {
		return new RepositoryConnectionFactory(getRepository());
	}

	@Bean
	public Repository getRepository() {
		return new HTTPRepository(url);
	}
	
	@Bean
	SesameTransactionManager transactionManager() throws RepositoryException {
		return new SesameTransactionManager(getSesameConnectionFactory());
	}


}
