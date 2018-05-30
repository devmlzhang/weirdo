
/**
 * 连接方法
 */
function mqtt_connect(){
    var hostname="127.0.0.1";
    var port="61614";
    var clientId = "weirdo-cliendId"+ parseInt(Math.random() * 100, 10);;

    // Create a client instance
    mqtt_client = new Paho.MQTT.Client(hostname, Number(port), clientId);
    mqtt_client.onConnectionLost = onConnectionLost;
    mqtt_client.onMessageArrived = onMessageArrived;

    // connect the client
    mqtt_client.connect({
        onSuccess:onConnect,
        onFailure:onFailure,
        keepAliveInterval:30,
        timeout:30,
        cleanSession:false,
        invocationContext: {host : hostname, port: port, clientId: clientId}

    });
}

/**
 * 成功连接到服务器
 * 打印日志
 * 页面显示信息
 * @param context
 */
function onConnect(context) {
    //Once a connection has been made, make a subscription and send a message.
    console.log("Client Connected");
    console.log(context);
    subscribe("Topic/Weirdo/DaChange",0);
}

function unsubscribe(topic) {
    // console.info('Unsubscribing from ', topic);
    mqtt_client.unsubscribe(topic, {
        onSuccess: unsubscribeSuccess,
        onFailure: unsubscribeFailure,
        invocationContext: {topic: topic}
    });
}

function unsubscribeSuccess(context){
    console.info('Successfully unsubscribed from ', context.invocationContext.topic);
}

function unsubscribeFailure(context){
    console.info('Failed to  unsubscribe from ', context.invocationContext.topic);
}

function subscribe(topic,qos) {
    //  console.info('Subscribing to: Topic: ', topic, '. QoS: ', qos);
    mqtt_client.subscribe(topic,
        {
            qos: qos,
            onFailure: function (res) {
                subscribe(topic, qos);
            },
            onSuccess: function (res) {
            },
            invocationContext:{topic:topic, qos:qos}
        });
}

function onFailure(context){
    console.log("connect onFailure");
    console.dir(context);
    mqtt_connect();
}

//链接丢失
function onConnectionLost(context) {
//    info("正在连接服务器");
    console.log("connect onConnectionLost");
    console.dir(context);
    if (context.errorCode !== 0) {
        console.log("onConnectionLost:"+context.errorMessage);
    }
    mqtt_connect();
}

//called when a message arrives
function onMessageArrived(message) {

  //  console.log("message:"+message.payloadString);
    var json = eval('('+message.payloadString+')');
    console.log("message1:"+message.payloadString);
    console.log("message2:"+json.data);
    //处理消息
    handler(json.data);

}

/**
 * 断开动态WEBSOCKET数据连接
 */
function closeMqttSocket() {
    if (mqtt_client) {
        isConnected=false;
        unsubscribe("Topic/Weirdo/DaChange");
        mqtt_client.disconnect();
        console.log('关闭mqtt_client connection')
    }
}

window.onbeforeunload = function(){
    closeMqttSocket();
    console.log('关闭mqtt_client connection')
};