package com.example.hangman;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    private static final String INSERT_WORDS =
            "INSERT INTO Words (word, level, hint) VALUES " +
                    "('cat', 'BEGINNER', 'a small domestic animal known for catching mice.'), " +
                    "('meal', 'BEGINNER', 'Food eaten or prepared for eating at one time.'), " +
                    "('hope', 'BEGINNER', 'to want something to happen or be true.'), " +
                    "('ice', 'BEGINNER', 'Frozen water.'), " +
                    "('red', 'BEGINNER', 'The color range between a moderate orange and russet.'), " +
                    "('tree', 'BEGINNER', 'a woody perennial plant having a single usually elongate main stem generally with few or no branches on its lower part.'), " +
                    "('wall', 'BEGINNER', 'One of the sides of a room or building connecting floor and ceiling or foundation and roof.'), " +
                    "('wise', 'BEGINNER', 'Having or showing the ability to make good judgments and decisions, based on experience, knowledge, and understanding.'), " +
                    "('town', 'BEGINNER', 'a compactly settled area usually larger than a village but smaller than a city.'), " +
                    "('test', 'BEGINNER', 'Something (such as a series of questions) for measuring the skill, knowledge, intelligence, capacities of an individual or group.'), " +
                    "('sky', 'BEGINNER', 'The upper atmosphere that constitutes an apparent great vault  over the earth.'), " +
                    "('silk', 'BEGINNER', 'a lustrous tough elastic fiber produced by silkworms and used for textiles.'), " +
                    "('sea', 'BEGINNER', 'a great body of salt water that covers much of the eart.'), " +
                    "('salt', 'BEGINNER', 'It is abundant in nature, and is used especially to season or preserve food or in industry.'), " +
                    "('road', 'BEGINNER', 'an open way for vehicles, persons, and animals.'), " +
                    "('roof', 'BEGINNER', 'The cover of a building.'), " +
                    "('rice', 'BEGINNER', 'The starchy seeds of an annual southeast Asian cereal grass that are cooked and used for food.'), " +
                    "('leaf', 'BEGINNER', 'a flat, usually green, organ of a plant that is attached to the stem and that contains chlorophyll for photosynthesis.'), " +
                    "('dog', 'BEGINNER', 'Domesticated canid that is often kept as a pet or used for working purposes.'), " +
                    "('ball', 'BEGINNER', 'a spherical body used in a game or sport.'), " +
                    "('apple', 'INTERMEDIATE', 'Usually rounded red, yellow, or green edible pome fruit.'), " +
                    "('paper', 'INTERMEDIATE', 'a thin material made from cellulose fibers that are pressed together. It is used for writing, printing, and wrapping.'), " +
                    "('plane', 'INTERMEDIATE', 'a living organism that typically has leaves, stems, and roots, and that can make its own food using sunlight.'), " +
                    "('power', 'INTERMEDIATE', 'The ability or capacity to do something or act in a particular way.'), " +
                    "('river', 'INTERMEDIATE', 'a natural stream of water of usually considerable volume.'), " +
                    "('smile', 'INTERMEDIATE', 'a facial expression that shows pleasure, amusement, or happiness.'), " +
                    "('sweet', 'INTERMEDIATE', 'Marked by one of the five basic taste sensations that is usually pleasing to the taste and typically induced by sugars.'), " +
                    "('water', 'INTERMEDIATE', 'a colorless, odorless, tasteless liquid that is essential for life.'), " +
                    "('sugar', 'INTERMEDIATE', 'a major source of energy for the body that is found in many foods, including fruits and grains, and can be added to many processed foods, such as candy and cookies.'), " +
                    "('stone', 'INTERMEDIATE', 'a concretion of earthy or mineral matter of indeterminate size or shape.'), " +
                    "('metal', 'INTERMEDIATE', 'a chemical element that is typically shiny, has a high melting point, and is a good conductor of electricity and heat.'), " +
                    "('laugh', 'INTERMEDIATE', 'to show emotion (such as mirth or joy,) with a chuckle or explosive  sound.'), " +
                    "('judge', 'INTERMEDIATE', 'a person who decides the outcome of a legal case.'), " +
                    "('house', 'INTERMEDIATE', 'a building that serves as living quarters for one or a few families.'), " +
                    "('green', 'INTERMEDIATE', 'a color between cyan and yellow on the visible spectrum.'), " +
                    "('fruit', 'INTERMEDIATE', 'The sweet and fleshy product of a tree or other plant that contains seed and can be eaten as food.'), " +
                    "('tooth', 'INTERMEDIATE', 'a hard, bony structure in the mouth of vertebrates that is used for biting and chewing.'), " +
                    "('sheep', 'INTERMEDIATE', 'a domesticated ruminant mammal with a thick woolly coat and (typically only in the male) curving horns. It is kept in flocks for its wool or meat, and is proverbial for its tendency to follow others in the flock.'), " +
                    "('heart', 'INTERMEDIATE', 'a hollow muscular organ that by its rhythmic contraction acts as a force pump maintaining the circulation of the blood.'), " +
                    "('beach', 'INTERMEDIATE', 'a shore of a body of water covered by sand, gravel, or larger rock fragments.'), " +
                    "('banana', 'ADVANCE', 'a long, curved fruit with a yellow skin and soft, sweet flesh.'), " +
                    "('family', 'ADVANCE', 'The basic unit in society traditionally consisting of two parents rearing their children.'), " +
                    "('office', 'ADVANCE', 'a room or building where people work, especially sitting at desks with computers, phones, etc., as a part of a business or organization.'), " +
                    "('orange', 'ADVANCE', 'a round, orange-colored citrus fruit that is a good source of vitamin C.'), " +
                    "('pencil', 'ADVANCE', 'a writing or drawing implement with a solid pigment core in a protective casing.'), " +
                    "('flower', 'ADVANCE', 'a reproductive structure of angiosperms, typically consisting of sepals, petals, stamens, and pistils.'), " +
                    "('pleasure', 'ADVANCE', 'a feeling of enjoyment or satisfaction.'), " +
                    "('school', 'ADVANCE', 'an institution where children are taught.'), " +
                    "('serious', 'ADVANCE', '\"important, weighty, or requiring careful thought.\" It can also mean \"not joking or trifling\"'), " +
                    "('sponge', 'ADVANCE', 'a soft, porous material that can absorb water and other liquids. It is used for cleaning and bathing.'), " +
                    "('summer', 'ADVANCE', 'The warmest season of the year, between spring and autumn.'), " +
                    "('theory', 'ADVANCE', 'a well-substantiated explanation of some aspect of the natural world, based on a body of facts that have been repeatedly confirmed through observation and experiment.'), " +
                    "('yellow', 'ADVANCE', 'a bright, warm color that is between green and orange on the spectrum of visible light.'), " +
                    "('window', 'ADVANCE', 'an opening in a wall, roof, or vehicle that allows light and air to enter and that can be closed or opened.'), " +
                    "('history', 'ADVANCE', 'a chronological record of significant events (such as those affecting a nation or institution) often including an explanation of their causes.'), " +
                    "('umbrella', 'ADVANCE', 'a collapsible shade for protection against weather consisting of fabric stretched over hinged ribs radiating from a central pole'), " +
                    "('spring', 'ADVANCE', 'a season that occurs between winter and summer. It is characterized by warmer weather, the growth of plants, and the appearance of flowers.'), " +
                    "('winter', 'ADVANCE', 'The coldest season of the year, between autumn and spring. It is characterized by cold temperatures, shorter days, and longer nights.'), " +
                    "('silver', 'ADVANCE', 'a soft, white, precious metal that is used in jewelry, coins, and other objects. It is also a good conductor of heat and electricity.'), " +
                    "('reward', 'ADVANCE', 'Something that is given in return for good behavior or service.'), " +
                    "('needle', 'ADVANCE', 'a thin, pointed piece of metal, usually with a hole in one end, used for sewing or injecting.') ";

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
    public String getLevelBasedOnScore(int score) {
        if (score < INTERMEDIATE_THRESHOLD) {
            return "BEGINNER";
        } else if (score < ADVANCED_THRESHOLD) {
            return "INTERMEDIATE";
        } else {
            return "ADVANCED";
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Words_Table);
        db.execSQL(INSERT_WORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS Words_Table";
        db.execSQL(dropTable);
        onCreate(db);    }
}
