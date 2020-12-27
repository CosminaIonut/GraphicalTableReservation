package com.example.restaurantreservation;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RestaurantApp.db";

    public static final String TABLE_NAME_RESTAURANT = "restaurant";

    public static final String TABLE_NAME_MAP = "map";
    public static final String TABLE_NAME_TABLE = "table_positioning";

    public static final String TABLE_NAME_USERS = "users";
    public static final String TABLE_NAME_BOOKING = "booking";
    public static final String TABLE_NAME_DRINKS_FOOD = "drinks_food";
    public static final String TABLE_NAME_MENU = "menu";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);  //when construcor is called -> databese is created
//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {  // create table

        sqLiteDatabase.execSQL("create table " + TABLE_NAME_RESTAURANT + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, ADRESS TEXT )");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_MAP + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PHOTOBG TEXT,  RID INTEGER, FOREIGN KEY (RID) REFERENCES " + TABLE_NAME_RESTAURANT + "( ID) )");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, X  FLOAT, Y FLOAT , MAPID INTEGER, FOREIGN KEY (MAPID) REFERENCES " + TABLE_NAME_MAP + "( ID)  )");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_USERS + "(name TEXT, email TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_BOOKING + "(name TEXT, telefon TEXT, date DATE, startH DATETIME, endH DATETIME ,USERID TEXT, TABLEID INTEGER, FOREIGN KEY (USERID) REFERENCES " + TABLE_NAME_USERS + "(email) ,  FOREIGN KEY (TABLEID) REFERENCES " + TABLE_NAME_TABLE + "(ID)  )");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_DRINKS_FOOD+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, INGREDIENTS TEXT, PRICE FLOAT, QUANTITY TEXT,TYPE TEXT,SORTIMENT TEXT)");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_MENU+ "(ITEMID INTEGER, RID INTEGER, PRIMARY KEY (ITEMID, RID),FOREIGN KEY (RID) REFERENCES " + TABLE_NAME_RESTAURANT + "( ID), " +
                                                                    "FOREIGN KEY (ITEMID) REFERENCES " + TABLE_NAME_DRINKS_FOOD + "( ID) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {   //delete table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RESTAURANT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MAP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BOOKING);
        onCreate(sqLiteDatabase);

    }

    /////       TABLES FUNCTIONS        ////////////////////////////////////////////////////////////

    /*
    * Add coordinates and assign map/restaurant id to tables.
    * */
    public void insertData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Table coordinates for restaurant 1

        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(200,100,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(120,230,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(200,350,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(120,470,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(500,100,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(650,200,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(500, 300,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(575, 650,1)");


        // Table coordinates for restaurant 2
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(150,200,2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(450,100,2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(600,240,2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(600,460,2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(150,350,2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(150,500,2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(150,700,2)");
    }

    /*
    * Get all tables with a specific id from the tables table.
    * */
    public Cursor getAllData(int id) {
//        insertData();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_TABLE+ " where MAPID="+id, null);
        return result;

    }

    /*
    * Use this to delete all tables from a specific restaurant.
    * Just change variable mapId value to the id of the map from which to delete.
    * */
    public void deleteSpecificTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        int mapId = 2;

        db.execSQL("Delete FROM " + TABLE_NAME_TABLE + " WHERE MAPID = " + mapId);
    }

    /*
    * Delete all tables from the tables table.
    * Complete annihilation.
    * ( Use with caution. Do not allow madmen access to this. Or any delete function whatsoever.)
    * */
    public void deleteAllTables() {
        // for testing purposes only (i hope)
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete FROM " + TABLE_NAME_TABLE);
    }

    /////       RESTAURANT FUNCTIONS       /////////////////////////////////////////////////////////

    /*
    * Used this to add additional restaurants.
    * Yes, the typo is intentional. Smartypants.
    * */
    public void insertMoarRestaurants(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Insert  INTO " + TABLE_NAME_RESTAURANT + " (NAME,ADRESS) Values('McTest', 'Str. Paris 23')");
        db.execSQL("Insert  INTO " + TABLE_NAME_RESTAURANT + " (NAME,ADRESS) Values('Refresh', 'Str. General Magheru 2')");
        db.execSQL("Insert  INTO " + TABLE_NAME_RESTAURANT + " (NAME,ADRESS) Values('Stories', 'Str. Victor Deleu 1')");
    }

    /*
    * Use this to add img name (obsolete considering we implemented a file naming rule)
    * and restaurant id. This table is still necessary for tables coordinates table.
    * */
    public void insertMapToRestaurant(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Insert  INTO " + TABLE_NAME_MAP + " (PHOTOBG,  RID) Values('restaurantmap2', 2)");
    }

    /*
    * Delete ala data in the restaurants table.
    * Then reset the autoincrement value to 0 to prevent it from increasing further.
    * */
    public void deleteAllRestaurants() {
        // for testing purposes only (i hope)
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete FROM " + TABLE_NAME_RESTAURANT);
        db.execSQL("UPDATE `sqlite_sequence` SET `seq` = 0 WHERE `name` = 'restaurant';");
    }

    /*
    * Returns all restaurants in the table.
    * */
    public Cursor getAllRestaurants(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+TABLE_NAME_RESTAURANT,null);
        return result;
    }

    /*
    * Search function used in the OnTextChangeListener.
    * Returns all restaurants containing the string given as parameter.
    * */
    public Cursor getSearchRestaurant(String text){
        System.out.println("In SEARCH-----------------------------");
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+TABLE_NAME_RESTAURANT+ " where NAME like '%"+text +"%'",null);
        return result;
    }

    /////       BOOKING FUNCTIONS       ////////////////////////////////////////////////////////////

    /*
    * Insert into Booking table the data given as parameter from the booking form.
    * */
    public boolean insertBooking(String name, String phone, String date, String startHour, String endHour, Integer idTable) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("telefon", phone);
        contentValues.put("date", date);
        contentValues.put("startH", startHour);
        contentValues.put("endH", endHour);
        contentValues.put("TABLEID", idTable);
        contentValues.put("USERID", 0);
        long results = sqLiteDatabase.insert(TABLE_NAME_BOOKING, null, contentValues);
        if (results == -1)
            return false;
        else
            return true;

    }

    /*
    * Returns all the booked tables between the hours given as parameters.
    * */
    public Cursor getBookedTables(String date, String startHour, String endHour, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(date);
        System.out.println(startHour);
        System.out.println(endHour);
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_BOOKING + " where date='" + date +
                        "' and TABLEID = " + id + " and ((startH <= Datetime('" + startHour + "') and endH >=  Datetime('" + endHour + "')) " +
                        "or (startH <= Datetime('" + startHour + "') and endH >=  Datetime('" + startHour + "')) " +
                        "or (startH <= Datetime('" + endHour + "') and endH >=  Datetime('" + endHour + "'))" +
                        "or (startH >= Datetime('" + startHour + "') and endH <=  Datetime('" + endHour + "')))"
                , null);
        System.out.println(result);
        return result;

    }

    /*
    * Returns all booking records.
    * Useful for them spies and federal agents looking for shady people.
    * Or stalkers.
    * And sometimes devs.
    * */
    public Cursor getAllBooking() {
//        insertData();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_BOOKING, null);
        return result;

    }

    /////       USER/USER DATA FUNCTIONS      //////////////////////////////////////////////////////

    /*
    * Pretty self explanatory but here we go..
    * Add user data given as parameters to the user table.
    * */
    public boolean insertUser(String name, String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long results = sqLiteDatabase.insert(TABLE_NAME_USERS, null, contentValues);
        if (results == -1)
            return false;
        else
            return true;

    }

    /*
    * You know, some apps and sites only allow 1 account per email.
    * We are one of those.
    * So we check if it's already existing in our db or not.
    * */
    public boolean checkEmail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME_USERS + " where email=?", new String[]{email});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    /*
    * Gotta check if we homies before we let ya in, no?
    * Check if there is an account with the given email and password.
    * */
    public boolean checkCredentials(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME_USERS + " where email=? and password=?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public String getName(String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME_USERS + " where email='"+email +"'", null);
        while(cursor.moveToNext()){
            return cursor.getString(0);
        }
        return "";

    }


    /////       MENU FUNCTIONS      ////////////////////////////////////////////////////////////////
    // WARNING: BIG inserts. And when I say BIG, I mean BIG.

    public void insertFoodDrinks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                    "Values('Espresso', ' ', 7,'30ml','Drink','Coffee')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Espresso Macchiato', ' ', 7,'80ml','Drink','Coffee')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Latte', ' ', 10,'200ml','Drink','Coffee')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Flavoured Latte', 'cinnamon, gingerbread, salted caramel, butterscotch, almond ', 12,'200ml','Drink','Coffee')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Hot Chocolate', 'black/white ', 12,'200ml','Drink','Coffee')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Green', 'organic samba acai', 12,'200ml','Drink','Tea')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Black', 'earl grey, assam thyme, darjeeling ', 12,'200ml','Drink','Tea')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Fruit', 'fruit symphony, rose apricot, apple elderflower ', 12,'200ml','Drink','Tea')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Herbal', 'refreshing mint, chamomile, ginger lemongrass ', 12,'200ml','Drink','Tea')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Orange fresh', ' ', 13,'250ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Classic lemonade', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Grapefruit&cucumber', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Orange lemonade', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Lavender lemonade', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Kombucha lemonade', ' ', 17,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Mint lemonade', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Gin Tonic', 'Beefeater 24, Schweppes Kinley', 23,'300ml','Drink','Gin')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Tanqueray Rangpur', ' ', 17,'40ml','Drink','Gin')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Brockmans', ' ', 25,'40ml','Drink','Gin')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Gin Mare', ' ', 25,'40ml','Drink','Gin')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Hendriks', ' ', 18,'40ml','Drink','Gin')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Monkey 47', ' ', 20,'40ml','Drink','Gin')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Old Fashioned Rum', 'Diplomatico Mantuano, bitter plum, banana liquor, brown sugar', 25,'210ml','Drink','Cocktail')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Moscow Muler', 'Absolut Blue, ginger beer, lime', 25,'210ml','Drink','Cocktail')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Old Fashioned Whisky', 'Bullelt Bourbon, Angostura Bitters, Orange Bitters, banana liquor, brown sugar', 27,'210ml','Drink','Cocktail')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Another Martini', 'Beefeater 24, Cinzano Extra Dry, cocoa liquer', 25,'210ml','Drink','Cocktail')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Whisk(e)y Sour', 'Four Roses, lemon juice, spicy mango syrup, sugar syrup', 25,'210ml','Drink','Cocktail')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Hotel Nacional', 'Havana 3 years, lime juice, pineapple puree, Angostura Bitters, apricot brandy', 25,'210ml','Drink','Cocktail')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Angelico Negroni', 'Beefeater 24, Cinzano Rosso, Campari, Frangelico', 25,'210ml','Drink','Cocktail')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Aperol Spritz', 'Aperol, sparkling wine, sparkling water, thyme, olives', 25,'300ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Hugo', 'sparkling wine, ederflower syrup, sparkling water, mint, lime, thyme', 25,'300ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Campari Orange', 'Campari, orange juice, basil', 22,'350ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Cuba Libre', 'Havana 3 years, Coca-Cola, lime, cinnamon stick', 18,'350ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Mojito', 'Havana 3 years, mint, sparkling wine, lime juice, sugar syrup, melone liquor', 18,'350ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Vodka Mixers', 'vodka, tonic water/ cranberry juice/ apple juice', 18,'350ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Amerie tonic', 'Amerie, Schweppes Kinley, menta', 22,'300ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Campari Soda', 'Campari, sparkling water, thyme', 22,'300ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Fresh Lillet', 'Lillet Rose, Schweppes Kinley, cucumber, strawberry', 22,'300ml','Drink','Long Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Heineken Draught', ' ', 9,'400ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Heineken Draught', ' ', 9,'250ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Ciuc Draught', ' ', 7,'400ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Ciuc Draught', ' ', 5,'250ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Edelweiss Draught', ' ', 15,'500ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Edelweiss Draught', ' ', 10,'300ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Heineken Bottle', ' ', 9,'330ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Ciuc Bottle', ' ', 7,'330ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Ciuc IPA Bottle', ' ', 10,'330ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Silva Blonde Bottle', ' ', 9,'330ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Silva Black Bottle', ' ', 10,'330ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Heineken NA', ' ', 9,'400ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Ciuc Radler Raspberry & Lemon NA', ' ', 10,'500ml','Drink','Beer')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Strongbow ', 'Red Berries/Rose/Pear', 10,'330ml','Drink','Cider')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Strongbow ', 'Red Berries/Rose/Pear ', 10,'330ml','Drink','Cider')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Old Mout Kiwi&Lime/Passionfruit&Apple/Summer Berries', ' ', 13,'500ml','Drink','Cider')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Coca-Cola', ' ', 7,'250ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Coca-Cola Zero', ' ', 7,'250ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Fanta Orange', ' ', 7,'250ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Sprite', ' ', 7,'250ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Schweppes tonic', ' ', 7,'250ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Fuzetea peach', ' ', 7,'250ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Izvorul alb', ' ', 7,'330ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Dorna sparkling water', ' ', 7,'330ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Red Bull', ' ', 12,'250ml','Drink','Soft Drinks')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Cottage Cheese Bruschetta', 'black bread with seeds cottage cheese, cherry tomatoes, black olives,  ', 14,'150g','Food','Starters')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Mozzarella Bufala', 'buffalo mozzarella, cherry,tomatoes, capers, basil emulson, black olives, toast ', 21,'260g','Food','Starters')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Baked Veggies', 'baked vegetables, homemeade cheese biscuites, basil infused sour cream, cherry tomatoes ', 19,'250g','Food','Starters')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Crispy Chicken Salad', 'chicken breast, green salad, cherry tomatoes, lemon ', 25,'335g','Food','Salads')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Halloumi Salad', 'grilled Halloumi, homeycomb, popcorn, pickeld radish, seasonal slaad, almond flakes, pomegranate ', 27,'250g','Food','Salads')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Mushroom Tagliatelle', 'tagliatelle, mushrooms, parmesan, peas, garlic, butter, tomato souce', 29,'400g','Food','Pasta')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Scrambled Egg Bun', 'scrambled eggs, homemade bun, baby spanac, truffle sauce', 25,'230g','Food','Brunch')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('French Omlette', 'french omlette, parsley panko, cottage cheese', 23,'250g','Food','Brunch')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Frittata', 'egg, baby spinach, parmesan cheese, truffle oil, cherry tomatoes, bacon, arugula, red onion, parsley', 27,'280g','Food','Brunch')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Pain Perdu', 'toast, egg, wild berries yoghurt, cinnamon', 29,'230g','Food','Brunch')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Seafood Linguine', 'linguine, vongole, mussels, shrimps, garlic, cherry tomatoes, chilli peppers, parsley', 35,'350g','Food','Pasta')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Shrimp Linguine', 'linguine, shrimps, cream, white wine, lemon, parsley', 38,'350g','Food','Pasta')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Pumpkin Tagliatelle', 'tagliatelle, pumpkin, speck, cottage cheese, rosemary, green onion', 31,'460g','Food','Pasta')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Thai Noodles', 'rice noodles, seasonal vegetables, soy souce, oyster souce, ginger, hazelnuts', 29,'400g','Food','Pasta')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Roasted Cauliflower Steak', 'cauliflower steak, cauliflower mousse with truffles, Chimchurri sauce, crispy onion', 27,'300g','Food','Main Courses')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Asian Ribs', 'asian pork ribs, green onion, sesame', 34,'500g','Food','Main Courses')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Pork Belly with Cabbage', 'pork belly, speck, mushrooms, pumpkin, cabbage strudel', 28,'440g','Food','Main Courses')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Pumpkin Chicken Risotto', 'rice, chicken breast, pumpkin, parmesan, butter', 31,'460g','Food','Main Courses')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Salmon with Green beans & Basil', 'grilled salmon, green beans, tomatoes, garlic, basil, butter', 47,'375g','Food','Main Courses')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Drunken Mussels', 'mussels, tomatoes, garlic, parsley, white wine, toast', 49,'800g','Food','Main Courses')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Burger au Camembert', 'grilled camembert cheese, home made bun, arugula, orange jam', 26,'250g','Food','Urban Goodies')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Stories Burger', 'Black Angus chuck, home made bun, arugula, grilled zucchini, tomatoes, emmentaler cheese, truffle sauce', 33,'320g','Food','Urban Goodies')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Schnitzel Chalupa', 'chicken breast, panko bread, tomatoes, avocado, onion, yoghurt, capers, green salad', 26,'360g','Food','Urban Goodies')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Paella Pan', 'rice, seafood, tomatoes, bell pepper, saffron, lemon', 35,'540g','Food','Urban Goodies')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('French Fries', '', 9,'200g','Food','Sides')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('French Fries', '', 9,'200g','Food','Sides')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Sweet Potato Fries', '', 15,'200g','Food','Sides')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Anna Potatoes', '', 15,'200g','Food','Sides')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Buttered rosemary potatoes', '', 15,'200g','Food','Sides')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Rice', '', 9,'200g','Food','Sides')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Grilled vegetables', '', 15,'200g','Food','Sides')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Cream soup of the day', '', 15,'400g','Food','Soup')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Flan Patissier', 'vanilla tart, wild berries', 17,'150g','Food','Desserts')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Chololate Cremeux', 'chocolate cremeux with vanilla cream, caramelized hazelnuts, sour cherries', 21,'150g','Food','Desserts')");

    }
    public void insertMenu(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(1, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(2, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(3, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(4, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(5, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(6, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(7, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(8, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(9, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(10, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(11, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(12, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(13, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(14, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(15, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(16, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(17, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(18, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(19, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(20, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(21, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(22, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(23, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(24, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(31, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(30, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(39, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(40, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(52, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(53, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(55, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(56, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(64, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(65, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(67, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(68, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(69, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(70, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(71, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(74, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(78, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(79, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(84, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(85, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(88, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(89, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(96, 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(97, 1)");







        // Restaurant 2 Menu
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(1, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(2, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(3, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(4, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(5, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(6, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(7, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(8, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(9, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(10, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(11, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(12, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(13, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(14, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(15, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(16, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(17, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(18, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(19, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(20, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(21, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(22, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(23, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(24, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(31, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(30, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(39, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(40, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(52, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(53, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(55, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(56, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(64, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(65, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(67, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(68, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(69, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(70, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(71, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(74, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(78, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(79, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(84, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(85, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(88, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(89, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(96, 2)");
        db.execSQL("Insert  INTO " + TABLE_NAME_MENU + " (ITEMID,RID) " +
                "Values(97, 2)");
    }

            //------    FOOD FUNCTIONS    ------//

    public Cursor getallFoodFromMenu(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Food' and  RID="+ id, null);
        return result;
    }

    public Cursor getallFoodSortimentsFromMenu(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select distinct SORTIMENT  from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Food' and  RID="+ id, null);
        return result;
    }

    public Cursor getallFoodSortiment(int id, String sortiment){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Food' and SORTIMENT ='"+sortiment+"' and  RID="+ id, null);
        return result;
    }

            //------    DRINKS FUNCTIONS    ------//

    public Cursor getallDrinksFromMenu(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Drink' and  RID="+ id, null);
        return result;
    }

    public Cursor getallDrinksSortimentsFromMenu(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select distinct SORTIMENT  from "+ TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Drink' and  RID="+ id, null);
        return result;
    }

    public Cursor getallDrinkSortiment(int id, String sortiment){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Drink' and SORTIMENT ='"+sortiment+"' and RID="+ id, null);
        return result;
    }

}
