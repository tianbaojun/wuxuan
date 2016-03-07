package com.vtmer.yann.powernotes.osCalender;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.widget.Toast;

import com.vtmer.yann.powernotes.Note;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tabjin on 2016/2/27.
 */
public class MyCalenderEventsLab {

    private static String calanderEventURL = "";
    private static Uri uri;
    private static List<Note> list;
    private static  ContentValues contentValues;
    static {
        if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
            calanderEventURL = "content://com.android.calendar/events";
        } else{
            calanderEventURL = "content://calendar/events";
        }
       uri = Uri.parse(calanderEventURL);
    }
//无法创建实例，private修饰
    private MyCalenderEventsLab() {
    }
//得到系统中所有的日程，返回一个note集合
    public static List<Note> getMyCalenderEventList(Context context) throws JSONException {
        ContentResolver cr = context.getContentResolver();
        list = new ArrayList<Note>();
        // 日历里面相应的Event的URI
        Cursor cursor = cr.query(uri, null, null, null, null);
        String str = null;
        while (cursor.moveToNext())

        {
            Note note = new Note();
            note.setAndroid_id(cursor.getString(cursor
                    .getColumnIndex(CalendarContract.Events._ID)));
            note.setTitle(cursor.getString(cursor
                    .getColumnIndex(CalendarContract.Events.TITLE)));
            note.setContent(cursor.getString(cursor
                    .getColumnIndex(CalendarContract.Events.DESCRIPTION)));
            note.setDate(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART)));
            list.add(note);
            note.toJSON();
        }
        return list;
    }

    /**
     *
     * @param context
     * @param values
     */
    public static boolean insertOrUpdateEvent(Context context, Note values){
        ContentResolver cr = context.getContentResolver();
        getContentValues(values);
//        if(has(values)){
            if(-1!=cr.update(uri,contentValues,
                    CalendarContract.Events._ID+"=?",new String[]{values.getAndroid_id()})){
                Toast.makeText(context,"更新成功！",Toast.LENGTH_SHORT).show();
                return true;
            }else{
                Toast.makeText(context,"更新失败！",Toast.LENGTH_SHORT).show();
            }
            return false;
//        }else{
//            cr.insert(uri,contentValues);
//            Toast.makeText(context,"插入成功！",Toast.LENGTH_SHORT).show();
//        }
    }
//判断是否含有该日程
    public static boolean has(Note n){
        for(Note myNote:list){
            if(n.getAndroid_id().equals(myNote.getId())){
                return true;
            }
        }
        return false;
    }

    public static boolean deleteNote(Context context,Note n){
        ContentResolver cr = context.getContentResolver();
        if (-1!=cr.delete(uri, CalendarContract.Events._ID+"=?",new String[]{n.getAndroid_id()})) {
            return true;
        }
        return false;
    }


    private static ContentValues getContentValues(Note n) {
        contentValues = new ContentValues();
        contentValues.put(CalendarContract.Events._ID, n.getAndroid_id());
        contentValues.put(CalendarContract.Events.DTSTART,n.getDate().getTime());
        contentValues.put(CalendarContract.Events.DESCRIPTION, n.getContent());
        return contentValues;
    }

}