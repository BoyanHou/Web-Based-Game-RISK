## How to play the game:  
- gradle build (this will take a while because the amount of test cases)
- For running the server:
    - `gradle run --args='server <PortNum> <PlayerNum>'`
- For running the client:
    - `gradle run -- args='client <ServerPortNum> <ServerIP>'`
- NOTICE: 
    - You have to first run the server
    - You have to then run a correct amount of clients to get the game started

## Other Notices
- For the test coverage: 
    - A large amount of reason that test coverage is not 100% is that although we have tested using pre-entered "user input", the main function in `RISKGame.java` and the run() function in `RISKGameServer.java` and `RISKGameClient.java` function are solely wrapper for the user-input gameplay and we have personally played through the game to make sure it works well.

## CI/CD
![pipeline](https://gitlab.oit.duke.edu/bh214/ece651-spr20-g7/badges/master/pipeline.svg)  
![coverage](https://gitlab.oit.duke.edu/bh214/ece651-spr20-g7/badges/master/coverage.svg?job=test)
## Coverage
[Detailed coverage](https://bh214.pages.oit.duke.edu/ece651-spr20-g7/dashboard.html)

