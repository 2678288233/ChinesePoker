curl -X POST -H "Content-Type: application/json" http://localhost:80/ChinesePoker_war/login/login -d "{username:10,password:11}"

curl -X POST -H "Content-Type: application/json" http://localhost:80/ChinesePoker_war/login/login -d "{username:10,password:10}"

curl -X POST -H "Content-Type: application/json" http://localhost:80/ChinesePoker_war/login/register -d "{username:10,password:11}"

curl -X POST -H "Content-Type: application/json" http://localhost:80/ChinesePoker_war/login/register -d "{username:1111,password:1111}"

curl -X POST -H "Content-Type: application/json" http://localhost:80/ChinesePoker_war/login/login -d "{username:1111,password:1111}"

curl -X POST -H "Content-Type: application/json" http://localhost:80/ChinesePoker_war/userInfo/detail -d "{id:1111}"

curl -X POST -H "Content-Type: application/json" http://localhost:80/ChinesePoker_war/userInfo/basic -d "{id:1111}"