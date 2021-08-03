Feature: Add quotations
	As a user
	I want to register the quotation of some dates
	So I can access the quotations in the future

	Scenario: Create a new quotation with two quotes
		 Given a quotation with the stock id 'petr4'
		 And a quote with date '2021-01-03' and value 11
		 And a quote with date '2021-01-07' and value 17
		 Then I should be able to access the quote with date '2021-01-03'