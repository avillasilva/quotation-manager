Feature: quotation manager
	As a user
	I want to register the quotation of some dates
	So I can access the quotations in the future

	Scenario: Add quotations
		 Given Some quotations registered in the database
		 And a quotation with the stock id '<stockId>'
		 And a <numberOfQuotes> quotes to be added
		 When I send the request to register the quotations
		 Then the response http status should be <code>
	
	Examples:
		|stockId |numberOfQuotes |code |
		|petr4   |3              |201  |
		|petr0   |3              |404  |

	Scenario: list a stock quote
		Given Some quotations registered in the database
		When I try to list the quotes with the stock id '<stockId>'
		Then the response http status should be <code>
		
	Examples:
		|stockId |code |
		|petr4   |200  |
		|petr0   |404  |