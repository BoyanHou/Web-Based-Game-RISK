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

## How to use GUI:
- after starting up the client, gui will automatically pop-up
- choose your desired move on the bottom of the GUI: move, attack, upgrade, fog, spy
- for choosing the desired territory for your orders, first click the textField and then click on the map. The territory that clicked on will display automatically
- for choosing units for your orders, click the checkboxes on the right; hold ctrl for multiple choices
- for making the order, click the correspoing button after finshing your selection
- all cancel buttons will lead you back to the action chosen panel
- when you finish this round, click finish, you will be prompt to wait until new round begins
- For viewing any territory details, hover on the territory(the color of territory will become darker) you want to get details. Then the informaiton will show on the right INFO panel.
    + If the color of a territory is white, the information of this territory is unavailiable.
    + If the color of a territory is gray, the information of the territory is outdated.
    + If there is a fog icon on the territory, that means you couldn't get the latest infomation anymore. You could place a fog on your territory to protect the situation of your territory.
    + If there is a spy icon on the territory with a number on the right, that means you have put spies on that territory.
- If you loose the game, you will be prompt to continue to audit the game (choose "yes") or not (choose "no")
- If somebody has won the game, the GUI will prompt you, then you could exit.