Собирать так:
1) В папке ChaseLogic: mvn clean install
2) В папке Game: mvn clean package
3) В той же папке Game: java -jar target/game.jar --enemiesCount=2 --wallsCount=10 --size=10 --profile=production