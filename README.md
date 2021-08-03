# Instructions:

- Git clone this repo.
- Open the folder in an IDE (such as IntelliJ) and run it from there, or just run it from the command line using the command - `mvnw spring-boot:run`. Make sure you have installed Maven and JRE 1.8 or above.
- Once the application is running, you can start the batch job by visiting `localhost:8080/api/start-job-async` in your browser.
- You can check the terminal to see all the logs as the job is running, or read the generated logs in the `defaultlog.log` file at the root folder.

# Notes:

- The async job processes all the customers in batches, finds the right strategy for each customer, fetches the current customer portfolio, finds the trades to rebalance it, and submits it to the FPS.
- The batch processing size is set to `3` by default. You can change it by editing the `batchSize` variable in `Constants.kt` and re-running the app.
- The external FPS service has been mocked with a simple in-memory hashmap in , but a real FPS service can be used as well. You just need to set the correct API endpoint by editing the `fpsBaseUrl` variable in `Constants.kt`. Also, you need to change `@Qualifier("mock")` to `@Qualifier("real")` in `PortfolioService.kt` to use the real FPS service. Then re-run the app.
- The customers and strategy CSV files are in the `\theRebalancer\src\main\resources\data` folder. Feel free to change these.
- The tests are incomplete due to time constraints. Took more time than expected to get familiar with how Spring does things and finish the bulk of the main application.
