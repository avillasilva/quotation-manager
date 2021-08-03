package br.inatel.quotationmanagement.acceptance;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.cucumber.junit.CucumberOptions;

@RunWith(SpringRunner.class)
@CucumberOptions(features = "classpath:features")
public class QuotationManagementRunner {
	
}
