import scalikejdbc._
import scala.util.{Try, Failure}

object Inventory extends DatabaseDriver{

    /**
     * Initialize the database table
     * This method will initialize the table for the database
     */
    def initializeTable() = {
		DB autoCommit { implicit session =>
			sql"""
			create table inventory (
		        code varchar(200) not null primary key,
                name varchar(200) not null,
                price float not null
			)
			""".execute.apply()
		}
	}

    /**
     * Insert item into the database
     * This method are used to insert item into database
     * @param code:String code of the item
     * @param name:String name of the item
     * @param price:Double price of the item
     * @return Try[Int] result on success or failure
     */
    def insertItem(code:String, name:String, price:Double):Try[Int] = {
        if(!isExist(code)){
            Try(DB autoCommit { implicit session =>
    			sql"""
    				insert into inventory
                    (code, name, price) values
    				($code, $name, $price)
    			""".update.apply()
    		})
        } else {
            new Failure(new Exception(s"Item($code, $name, $price) already exist"))
        }
    }

    /**
     * Search item by item code
     * This method will search the item by item code and return it
     * @param code:String code of the item
     * @return Option[Item] option of item if any
     */
    def searchByCode(code:String):Option[Item] = {
        println("Checking")
        DB readOnly { implicit session =>
			sql"select * from inventory where code = $code".map(
                rs => Item(rs.string("code"),
				rs.string("name"),rs.double("price"))
            ).single.apply()
		}
    }

    /**
     * Search item by name of item
     * This method will search item by item name and return it
     * @param name:String name of the item
     * @return List[Item] list of item that name contain the keyword search by user
     */
    def searchByName(name:String):List[Item] = {
        val searchName = s"%$name%"
        DB readOnly { implicit session =>
            sql"select * from inventory where name like $searchName".map(
                rs => Item(rs.string("code"),
                rs.string("name"), rs.double("price"))
            ).list.apply()
        }
    }

    /**
     * Return if item exist
     * This method will return if the item exist or not
     * @param cost:String code of the item
     * @return Boolean true if exist, false if not exist
     */
    def isExist(code:String): Boolean = {
        searchByCode(code) match {
            case Some(x) => true
            case None => false
        }
    }
}
