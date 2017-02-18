package com.ak.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ak.schedulers.AdvService;

@Configuration
@EnableWebMvc
@ComponentScan("com.ak")
@EnableTransactionManagement //zarzadzal bedzie spring automatycznie transakcjami
@EnableScheduling
@EnableAsync
public class AppConfig extends WebMvcConfigurerAdapter
{
	private final static String email = "liberarydamiantest@gmail.com";
	private final static String password = "Password77";
	
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable(); //wlacza domyslna konfiguracje mvc, ktore zajmuje sie przekierowaniem zadan
	}
	
	
	
	//fabryka obiektu - przygotowuje w odpowiedni sposob obiekt, ktory bedzie Autowired-owany
	@Bean //zwraca obiekt, ktory zawiera informacje o widoku - interfejsie graficznym
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/"); //tu bedziem front dodawac - jsp
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	//fabryka obiektu - przygotowuje w odpowiedni sposob obiekt, ktory bedzie Autowired-owany
	@Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);
        mailSender.setPassword(password);
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
	
	@Bean
	public AdvService getAdvService(){
		return new AdvService();
	}
}