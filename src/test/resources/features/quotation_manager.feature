Feature: quotation manager
	As a user
	I want to register the quotation of some dates
	So I can access the quotations in the future

	Scenario Outline: Add quotations
		 Given Some quotations registered in the database
		 And a quotation with the stock id '<stockId>'
		 And a <numberOfQuotes> quotes to be added
		 When I send the request to register the quotations
		 Then the response http status should be <code>
	
	Examples:
		|stockId |numberOfQuotes |code |
		|petr4   |3              |201  |
		|petr0   |3              |404  |

	Scenario Outline: list a stock quote
		Given Some quotations registered in the database
		When I try to list the quotes with the stock id '<stockId>'
		Then the response http status should be <code>
		
	Examples:
		|stockId |code |
		|petr4   |200  |
		|petr0   |404  |

    Scenario Outline: Add quotations
            Given A stock with stock id '<stockId>'
            And a quote with date '<date>' and price '<price>'
            When I send the request to store the quotation
            Then the response http status code should be <status> and the error should be '<error>'
        
        Examples:
            | stockId	|    date    | price | status | error                                           |
            | petr4		| 2021-05-21 | 17    |   200  |                                                 |
            | petr0		| 2021-05-21 | 17    |   404  | There is no stock in the database with id petr0 |
            |   		| 2021-05-21 | 17    |   400  | must not be empty                               |
            | petr4	    | 2021-05-21 |       |   400  | must not be empty                               |
            | petr4	    |            | 17    |   400  | must not be empty                               |
            | petr4	    |            |       |   400  | must not be empty                               |
            | petr4		| xxxxxxxxxx | 17    |   400  | Invalid format. The date must match YYYY-MM-DD  |
            | petr4		| 2022-05-12 | xx    |   400  | Invalid format. The price must match 00.00      |
            | petr4		| xxxxxxxxxx | xx    |   400  | Invalid format.                                 |