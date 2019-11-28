<html>
<body>
<h2>Hello World!</h2>

<button onclick="test()">submit</button>
<button onclick="testJ()">submit</button>
<script>
    //发送POST请求跳转到指定页面
    function httpPost(URL, PARAMS) {
        var temp = document.createElement("form");
        temp.action = URL;
        temp.method = "post";
        temp.style.display = "none";

        for (var x in PARAMS) {
            var opt = document.createElement("textarea");
            opt.name = x;
            opt.value = PARAMS[x];
            temp.appendChild(opt);
        }

        document.body.appendChild(temp);
        temp.submit();

        return temp;
    }
    function test(){

        var params = {
            "username": "10",
            "password": "10"
        };

        httpPost("http://localhost:8080/ChinesePoker_war/login/login", params);
    }
    function testJ() {

        var xhr=new XMLHttpRequest();
        xhr.open("POST","http://localhost:8080/ChinesePoker_war/login/login",true,"10","10");
        xhr.setRequestHeader('content-type', 'application/json');
        xhr.setRequestHeader('Accept', '*');
        var params = {
            username: "10",
            password: "10"
        };

        xhr.send(JSON.stringify(params));//

        xhr.onload=function (ev) { alert(ev.returnValue) ;alert(xhr.responseText) }
        //alert(xhr.responseText)
    }

</script>
</body>
</html>
