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

    public void insertData() {    //insert in Table
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Insert  INTO " + TABLE_NAME_RESTAURANT + " (NAME,ADRESS) Values('McTest', 'Str. Paris 23')");
        db.execSQL("Insert  INTO " + TABLE_NAME_MAP + " (PHOTOBG,RID) Values('McTest.jpg', 1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(200,100,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(120,230,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(200,350,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(120,470,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(500,100,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(650,200,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(500, 300,1)");
        db.execSQL("Insert  INTO " + TABLE_NAME_TABLE + " (X,Y, MAPID) Values(575, 650,1)");

    }

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

    public boolean checkEmail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME_USERS + " where email=?", new String[]{email});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public boolean checkCredentials(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME_USERS + " where email=? and password=?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public Cursor getAllData() {    //Get data from table
//        insertData();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_TABLE, null);
        return result;

    }

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
                "Values('Grapefruit & cucumber lemonade', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Orange lemonade', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Lavender lemonade', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Kombucha lemonade', ' ', 17,'350ml','Drink','Fresh & Lemonade')");
        db.execSQL("Insert  INTO " + TABLE_NAME_DRINKS_FOOD + " (NAME,INGREDIENTS, PRICE, QUANTITY, TYPE,SORTIMENT) " +
                "Values('Mint lemonade', ' ', 14,'350ml','Drink','Fresh & Lemonade')");
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
                "Values('Mushroom Tagliatelle', 'tagliatelle,mushrooms,parmesan,peas, garlic, butter,tomato, souce ', 29,'400g','Food','Pasta')");

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
    }
    public Cursor getallFoodFromMenu(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Food' and  RID="+ id, null);
        return result;
    }
    public Cursor getallFoodSortimentsFromMenu(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select distinct SORTIMENT  from " + TABLE_NAME_DRINKS_FOOD+ " where TYPE='Food'", null);
        return result;
    }
    public Cursor getallDrinksFromMenu(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Drink' and  RID="+ id, null);
        return result;
    }
    public Cursor getallDrinksSortimentsFromMenu(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select distinct SORTIMENT  from " + TABLE_NAME_DRINKS_FOOD+ " where TYPE='Drink'", null);
        return result;
    }

    public Cursor getallDrinkSortiment(int id, String sortiment){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Drink' and SORTIMENT ='"+sortiment+"' and RID="+ id, null);
        return result;
    }
    public Cursor getallFoodSortiment(int id, String sortiment){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_MENU+ " inner join "+TABLE_NAME_DRINKS_FOOD+" on "+TABLE_NAME_DRINKS_FOOD+".ID="+TABLE_NAME_MENU+".ITEMID where TYPE='Food' and SORTIMENT ='"+sortiment+"' and  RID="+ id, null);
        return result;
    }


    public Cursor getAllBooking() {    //Get data from table
//        insertData();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_BOOKING, null);
        return result;

    }

    public void deleteAllTables() {
        // for testing purposes only (i hope)
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete FROM " + TABLE_NAME_TABLE);
    }

    public Cursor getAllRestaurants(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+TABLE_NAME_RESTAURANT,null);
        return result;
    }


}
