import com.xenomachina.argparser.ArgParser

class Args(parser: ArgParser) {
    // accept 1 regex followed by n filenames
    val localIp by parser.positional("LOCAL-IP",
            help = "IP to listen on")
    val localPort by parser.positional("LOCAL-PORT",
            help = "Port to listen on") { toInt() }
    val remoteIp by parser.positional("REMOTE-IP",
            help = "IP of the remote end")
    val remotePort by parser.positional("REMOTE-PORT",
            help = "Port of the remote end") { toInt() }
}

fun main(args: Array<String>) {
    val parsedArgs = ArgParser(args).parseInto(::Args)
    val channel = ChatChannel(parsedArgs.localIp, parsedArgs.localPort)

    println("Press enter to connect")
    readLine()

    channel.connect(parsedArgs.remoteIp, parsedArgs.remotePort)

    channel.onReceived += {
        println(">>")
    }

    var connected = false
    channel.onConnected += {
        connected = true
    }

    print("Connecting")
    while (!connected) {
        Thread.sleep(10)
        print(".")
    }
    println()

    var message = readLine()!!

    while (message.isNotEmpty()) {
        channel.send(message.toByteArray())

        message = readLine()!!
    }
}