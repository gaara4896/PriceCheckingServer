import java.io.{DataInputStream, DataOutputStream}
import java.net.{ServerSocket, Socket}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Success, Failure, Try}

object Server extends App{

    DatabaseDriver.setupDB()

    val server = new ServerSocket(8888)

    while(true){
        val socket = server.accept()

        Future{
            val client = socket
            try {
                val is = new DataInputStream(client.getInputStream())
                val os = new DataOutputStream((client.getOutputStream()))
                var line:Array[String] = is.readLine().split(";")

                if(line(0).equals("item")){
                    val result = Inventory.searchByName(line(1))
                    result.foreach( x => println(x.sendDetail))
                    os.writeBytes(result.foldLeft(""){(a, b) => s"$a${b.sendDetail};"} + "\n")
                }else if(line(0).equals("code")){
                    Inventory.searchByCode(line(1)) match{
                        case Some(x) => os.writeBytes(s"${x.sendDetail}\n")
                        case None => os.writeBytes(s"Items with code ${line(1)} not exists\n")
                    }
                } else if(line(0).equals("add")){
                    Inventory.insertItem(line(1), line(2), line(3).toDouble) match{
                        case Success(x) => os.writeBytes(s"Successfully added item with code: ${line(1)} and name ${line(2)}\n")
                        case Failure(x) => os.writeBytes(s"${x.getMessage}\n")
                    }
                }
            } catch {
                case e: Exception => e.printStackTrace
            } finally {
                client.close()
            }
        }
    }
}
