class Item(val code:String, val name:String, val price:Double){
    /**
     * Get string format for the detail to sent over
     * This method will return the object in string format to send
     * @return String in the format to send
     */
    def sendDetail:String = {
        return s"$code;$name;$price"
    }
}

object Item{
    /**
     * Create new object without using new word
     * This method will return a new object
     * @param code:String the code of the item
     * @param name:String the name of the item
     * @param price:Double the price of the item
     * @return Item that are created
     */
    def apply(code:String, name:String, price:Double):Item = new Item(code, name, price)
}
