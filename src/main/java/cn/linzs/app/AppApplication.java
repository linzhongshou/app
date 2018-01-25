package cn.linzs.app;

import cn.linzs.app.common.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan("cn.linzs.app")
@ServletComponentScan
public class AppApplication {

	public static void main(String[] args) {
		final ApplicationContext context = SpringApplication.run(AppApplication.class, args);
		SpringContextUtil.setApplicationContext(context);
	}
}
