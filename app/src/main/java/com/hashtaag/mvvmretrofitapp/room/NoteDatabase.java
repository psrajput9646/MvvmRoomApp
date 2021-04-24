package com.hashtaag.mvvmretrofitapp.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase noteDatabase;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (noteDatabase == null) {
            noteDatabase = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class,
                    "notes.db")
                    .fallbackToDestructiveMigration()
                    .build();
            /* if create run time database then add this method
            .addCallback(roomcallback)*/
        }

        return noteDatabase;
    }

    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
    // new populateAsynctask(noteDatabase).execute();
        }
    };

/*    private static class populateAsynctask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private populateAsynctask(NoteDatabase db) {
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("title1", "description1"));
            noteDao.insert(new Note("title2", "description2"));
            noteDao.insert(new Note("title3", "description3"));
            return null;
        }
    }*/
}
