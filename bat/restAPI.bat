curl -X POST http://localhost:8080/ChinesePoker_war/login/login -d "username=10&password=11"

curl -X POST http://localhost:8080/ChinesePoker_war/login/login -d "username=10&password=10"

curl -X POST http://localhost:8080/ChinesePoker_war/login/register -d "username=10&password=11"

curl -X POST http://localhost:8080/ChinesePoker_war/login/register -d "username=101&password=101"

curl -X POST http://localhost:8080/ChinesePoker_war/login/login -d "username=101&password=101"

curl -X POST http://localhost:8080/ChinesePoker_war/userInfo/detail -d "id=10"

curl -X POST http://localhost:8080/ChinesePoker_war/userInfo/basic -d "id=10"