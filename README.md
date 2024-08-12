Task Description:
Create a simple REST micro-service using Spring Boot that communicates with an external API to 
fetch foreign exchange rates for USD from European Central Bank, store this data in a database, 
and provide endpoints to access this data. External API should only be called when there is no data 
available in database of the application. Additionally, migrations for the database (Postgres or 
MySql) should be created separately using a migration tool. Finally, endpoints should be covered by
tests.
• GET /fx: Returns fx rates from USD to EUR, GBP, JPY, CZK. Response should contain 
date of conversion, source currency, target currency, exchange rate. It should be possible to 
specify target currency.
• GET /fx/{targetCurrency}: Returns 3 latest fx rates in form of time series from 
USD to target currency with a step of 1 day.
External API to use:
https://api.frankfurter.app/2024-03-18?to=USD
