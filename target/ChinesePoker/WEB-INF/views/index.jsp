<html>
<body>
<h2>Hello World!</h2>


<script language="JavaScript" type="text/javascript">
    var projectName="ChinesePoker_war";
    var path="localhost:8080/"+projectName;
    var poker_websocket;
    // 创建一个Socket实例
    //参数为URL，ws表示WebSocket协议。onopen、onclose和onmessage方法把事件连接到Socket实例上。每个方法都提供了一个事件，以表示Socket的状态。

    //不同浏览器的WebSocket对象类型不同
    //alert("ws://" + path + "/ws?uid="+uid);
    if ('WebSocket' in window) {
        poker_websocket = new WebSocket("ws://" + path + "/ws");
        console.log("=============WebSocket");
        //火狐
    } else if ('MozWebSocket' in window) {
        poker_websocket = new MozWebSocket("ws://" + path + "/ws");
        console.log("=============MozWebSocket");
    } else {
        poker_websocket = new SockJS("http://" + path + "/ws/sockjs");
        console.log("=============SockJS");
    }




        poker_websocket.onopen=function (ev) {
            alert("connection successful");
        };
        poker_websocket.onclose=function (ev) {
            alert("close");
        }
        poker_websocket.onmessage=function (ev) {
            alert("receive "+ev.data)
        }


    function doSend(message) {
        poker_websocket.send(message);
        alert("send "+message)
    }

</script>
<input onclick="doSend('1')">
</body>
</html>
