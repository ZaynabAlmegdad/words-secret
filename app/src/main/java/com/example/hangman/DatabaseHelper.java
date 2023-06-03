package com.example.hangman;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SecretWord.db";
    private static final int DATABASE_VERSION = 1;
    private int BEGINNER_THRESHOLD = 0;
    private int INTERMEDIATE_THRESHOLD = 10;
    private int ADVANCED_THRESHOLD = 20;
    private static final String Words_Table =
            "CREATE TABLE Words (_id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT, level TEXT, hint TEXT)";

    private static final String Users_Table =
            "CREATE TABLE users (email TEXT primary key, password TEXT)";


    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    private static final String INSERT_WORDS =
            "INSERT INTO Words (word, level, hint) VALUES " +
                    "('cat', 1, 'a small domestic animal known for catching mice.'), " +
                    "('meal', 1, 'Food eaten or prepared for eating at one time.'), " +
                    "('hope', 1, 'to want something to happen or be true.'), " +
                    "('ice', 1, 'Frozen water.'), " +
                    "('red', 1, 'The color range between a moderate orange and russet.'), " +
                    "('tree', 1, 'a woody perennial plant having a single usually elongate main stem generally with few or no branches on its lower part.'), " +
                    "('wall', 1, 'One of the sides of a room or building connecting floor and ceiling or foundation and roof.'), " +
                    "('wise', 1, 'Having or showing the ability to make good judgments and decisions, based on experience, knowledge, and understanding.'), " +
                    "('town', 1, 'a compactly settled area usually larger than a village but smaller than a city.'), " +
                    "('test', 1, 'Something (such as a series of questions) for measuring the skill, knowledge, intelligence, capacities of an individual or group.'), " +
                    "('sky', 1, 'The upper atmosphere that constitutes an apparent great vault  over the earth.'), " +
                    "('silk', 1, 'a lustrous tough elastic fiber produced by silkworms and used for textiles.'), " +
                    "('sea', 1, 'a great body of salt water that covers much of the eart.'), " +
                    "('salt', 1, 'It is abundant in nature, and is used especially to season or preserve food or in industry.'), " +
                    "('road', 1, 'an open way for vehicles, persons, and animals.'), " +
                    "('roof', 1, 'The cover of a building.'), " +
                    "('rice', 1, 'The starchy seeds of an annual southeast Asian cereal grass that are cooked and used for food.'), " +
                    "('leaf', 1, 'a flat, usually green, organ of a plant that is attached to the stem and that contains chlorophyll for photosynthesis.'), " +
                    "('dog', 1, 'Domesticated canid that is often kept as a pet or used for working purposes.'), " +
                    "('ball', 1, 'a spherical body used in a game or sport.'), " +
                    "('apple', 2, 'Usually rounded red, yellow, or green edible pome fruit.'), " +
                    "('paper', 2, 'a thin material made from cellulose fibers that are pressed together. It is used for writing, printing, and wrapping.'), " +
                    "('plane', 2, 'a living organism that typically has leaves, stems, and roots, and that can make its own food using sunlight.'), " +
                    "('power', 2, 'The ability or capacity to do something or act in a particular way.'), " +
                    "('river', 2, 'a natural stream of water of usually considerable volume.'), " +
                    "('smile', 2, 'a facial expression that shows pleasure, amusement, or happiness.'), " +
                    "('sweet', 2, 'Marked by one of the five basic taste sensations that is usually pleasing to the taste and typically induced by sugars.'), " +
                    "('water', 2, 'a colorless, odorless, tasteless liquid that is essential for life.'), " +
                    "('sugar', 2, 'a major source of energy for the body that is found in many foods, including fruits and grains, and can be added to many processed foods, such as candy and cookies.'), " +
                    "('stone', 2, 'a concretion of earthy or mineral matter of indeterminate size or shape.'), " +
                    "('metal', 2, 'a chemical element that is typically shiny, has a high melting point, and is a good conductor of electricity and heat.'), " +
                    "('laugh', 2, 'to show emotion (such as mirth or joy,) with a chuckle or explosive  sound.'), " +
                    "('judge', 2, 'a person who decides the outcome of a legal case.'), " +
                    "('house', 2, 'a building that serves as living quarters for one or a few families.'), " +
                    "('green', 2, 'a color between cyan and yellow on the visible spectrum.'), " +
                    "('fruit', 2, 'The sweet and fleshy product of a tree or other plant that contains seed and can be eaten as food.'), " +
                    "('tooth', 2, 'a hard, bony structure in the mouth of vertebrates that is used for biting and chewing.'), " +
                    "('sheep', 2, 'a domesticated ruminant mammal with a thick woolly coat and (typically only in the male) curving horns. It is kept in flocks for its wool or meat, and is proverbial for its tendency to follow others in the flock.'), " +
                    "('heart', 2, 'a hollow muscular organ that by its rhythmic contraction acts as a force pump maintaining the circulation of the blood.'), " +
                    "('beach', 2, 'a shore of a body of water covered by sand, gravel, or larger rock fragments.'), " +
                    "('banana', 3, 'a long, curved fruit with a yellow skin and soft, sweet flesh.'), " +
                    "('family', 3, 'The basic unit in society traditionally consisting of two parents rearing their children.'), " +
                    "('office', 3, 'a room or building where people work, especially sitting at desks with computers, phones, etc., as a part of a business or organization.'), " +
                    "('orange', 3, 'a round, orange-colored citrus fruit that is a good source of vitamin C.'), " +
                    "('pencil', 3, 'a writing or drawing implement with a solid pigment core in a protective casing.'), " +
                    "('flower', 3, 'a reproductive structure of angiosperms, typically consisting of sepals, petals, stamens, and pistils.'), " +
                    "('pleasure', 3, 'a feeling of enjoyment or satisfaction.'), " +
                    "('school', 3, 'an institution where children are taught.'), " +
                    "('serious', 3, '\"important, weighty, or requiring careful thought.\" It can also mean \"not joking or trifling\"'), " +
                    "('sponge', 3, 'a soft, porous material that can absorb water and other liquids. It is used for cleaning and bathing.'), " +
                    "('summer', 3, 'The warmest season of the year, between spring and autumn.'), " +
                    "('theory', 3, 'a well-substantiated explanation of some aspect of the natural world, based on a body of facts that have been repeatedly confirmed through observation and experiment.'), " +
                    "('yellow', 3, 'a bright, warm color that is between green and orange on the spectrum of visible light.'), " +
                    "('window', 3, 'an opening in a wall, roof, or vehicle that allows light and air to enter and that can be closed or opened.'), " +
                    "('history', 3, 'a chronological record of significant events (such as those affecting a nation or institution) often including an explanation of their causes.'), " +
                    "('umbrella', 3, 'a collapsible shade for protection against weather consisting of fabric stretched over hinged ribs radiating from a central pole'), " +
                    "('spring', 3, 'a season that occurs between winter and summer. It is characterized by warmer weather, the growth of plants, and the appearance of flowers.'), " +
                    "('winter', 3, 'The coldest season of the year, between autumn and spring. It is characterized by cold temperatures, shorter days, and longer nights.'), " +
                    "('silver', 3, 'a soft, white, precious metal that is used in jewelry, coins, and other objects. It is also a good conductor of heat and electricity.'), " +
                    "('reward', 3, 'Something that is given in return for good behavior or service.'), " +
                    "('needle', 3, 'a thin, pointed piece of metal, usually with a hole in one end, used for sewing or injecting.') ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public List<Question> loadWordsByLevel(String level) {
        List<Question> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {"hint", "word"};
        String selection = "level=?";
        String[] selectionArgs = {level};

        Cursor cursor = db.query("Words", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int hintIndex = cursor.getColumnIndex("hint");
                int wordIndex = cursor.getColumnIndex("word");


                // Check if both indices are valid
                if (wordIndex >= 0 && hintIndex >= 0) {
                    String hint = cursor.getString(hintIndex);
                    String word = cursor.getString(wordIndex);
                    words.add(new Question(hint, word));
                }
            }
            cursor.close();
        }
        return words;
    }
    public String getLevelFromScore(int score) {
        String level;

        if (score < 20) {
            level = "BEGINNER";
        } else if (score < 40) {
            level = "INTERMEDIATE";
        } else {
            level = "ADVANCED";
        }

        return level;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Words_Table);
        db.execSQL(Users_Table);
        db.execSQL(INSERT_WORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS Words_Table";
        db.execSQL(dropTable);

        String dropTableU = "DROP TABLE IF EXISTS Users_Table";
        db.execSQL(dropTableU);

        onCreate(db);}


}
