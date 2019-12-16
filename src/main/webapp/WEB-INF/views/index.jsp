<html>
<body>
<h2>Hello World!</h2>


<script language="JavaScript" type="text/javascript">
    var projectName="ChinesePoker_war";
    var path="117.78.0.217:8080/"+projectName;//localhost:80
    var poker_websocket;
    // 创建一个Socket实例
    //参数为URL，ws表示WebSocket协议。onopen、onclose和onmessage方法把事件连接到Socket实例上。每个方法都提供了一个事件，以表示Socket的状态。

    //不同浏览器的WebSocket对象类型不同
    //alert("ws://" + path + "/ws?uid="+uid);
    if ('WebSocket' in window) {
        poker_websocket = new WebSocket("ws://" + path + "/ws?user=10");
        console.log("=============WebSocket");
        //火狐
    } else if ('MozWebSocket' in window) {
        poker_websocket = new MozWebSocket("ws://" + path + "/ws?user=10");
        console.log("=============MozWebSocket");
    } else {
        poker_websocket = new SockJS("http://" + path + "/ws/sockjs?user=10");
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

    function sendCreateRoom() {
        var text='{"type":"createRoom","roomId":"123","roomDescription":"roomDescription"}';
        doSend(text)
    }
    function sendEnterRoom() {
        var text='{"type":"enterRoom","roomId":"111"}';
        doSend(text)
    }
    function sendLeaveRoom() {
        var text='{"type":"leaveRoom"}';
        doSend(text)
    }
</script>
<button onclick="sendCreateRoom()">CreateRoom</button>
<button onclick="sendEnterRoom()">EnterRoom</button>
<button onclick="sendLeaveRoom()">LeaveRoom</button>
</body>
</html>
