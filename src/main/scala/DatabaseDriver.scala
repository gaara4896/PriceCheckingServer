import scalikejdbc._

trait DatabaseDriver {
    val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
    val dbURL = "jdbc:derby:myDB;create=true;";
    // initialize JDBC driver & connection pool
    Class.forName(derbyDriverClassname)

    ConnectionPool.singleton(dbURL, "me", "mine")

    // ad-hoc session provider on the REPL
    implicit val session = AutoSession
}

object DatabaseDriver extends DatabaseDriver{

    /**
     * Setup database table if not exist
     * This methd will check if table exist, if not will initialize
     */
    def setupDB() = {
  	     if (!hasDBInitialize)
         Inventory.initializeTable()
    }

    /**
     * Check if table exist
     * This method will check if the table exist
     * @return Boolean true if exist, false if not exist
     */
    def hasDBInitialize : Boolean = {
        DB getTable "inventory" match {
            case Some(x) => true
            case None => false
        }
    }
}
