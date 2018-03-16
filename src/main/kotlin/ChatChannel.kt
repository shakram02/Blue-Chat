import shakram02.blue.BlueClient
import shakram02.blue.BlueServer

class ChatChannel(listenerIp: String, listenerPort: Int) {
    private val server = BlueServer()
    private val client = BlueClient()
    val onReceived = server.onReceived
    val onConnected = client.onConnected

    init {
        server.start(listenerIp, listenerPort)
    }

    fun connect(ip: String, port: Int) {
        client.connect(ip, port)
    }

    fun send(msg: ByteArray) {
        client.send(msg)
    }
}
