package cn.linzs.app;

import cn.linzs.app.common.utils.ShiroUtil;
import cn.linzs.app.service.CategoryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class AppApplicationTests {

	@Autowired
	private CategoryService categoryService;

	@Test
	public void contextLoads() {
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername("admin");
		token.setPassword("admin".toCharArray());
		ShiroUtil.getSubject().login(token);
	}

}
