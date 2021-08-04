package br.inatel.quotationmanagement;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class QuotationManagementApplicationTests {

	@Test
	void contextLoads() {
		Assert.assertTrue(true);
	}

}
