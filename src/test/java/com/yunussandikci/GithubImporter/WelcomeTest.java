package com.yunussandikci.GithubImporter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WelcomeTest {

	@Test
	public void testDefaultMessage() {
		GithubImporterApplication homeController = new GithubImporterApplication();
		ResponseEntity result = homeController.home();
		assertThat(result.getStatusCodeValue(), equalTo(200));
	}

}
