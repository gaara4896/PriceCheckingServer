import java.io.{DataInputStream, DataOutputStream}
import java.net.{ServerSocket, Socket}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Server extends App{
    val server = new ServerSocket(8888)

    while(true){
        val socket = server.accept()

        Future{
            val client = socket
            try {
                val is = new DataInputStream(client.getInputStream())
                val os = new DataOutputStream((client.getOutputStream()))
                var line:Array[String] = is.readLine().split(";")

                if(line(0) == "item"){
                    
                }else if(line(0) == "code"){

                }
            } catch {
                case e: Exception => e.printStackTrace
            } finally {
                client.close()
            }
        }
    }
}
