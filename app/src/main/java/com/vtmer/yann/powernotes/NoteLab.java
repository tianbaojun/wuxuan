package com.vtmer.yann.powernotes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vtmer.yann.powernotes.osCalender.MyCalenderEventsLab;

import java.util.ArrayList;
import java.util.UUID;


public class NoteLab {

//	日志打印标签，对程序无影响
	private static final String TAG = "NoteLab";

//	json文件名
	private static final String FILENAME = "notes.json";

//  操作json的工具类
	private NoteJSONSerializer mSerializer;

//	日程集合
	private ArrayList<Note> mNotes;

	private static NoteLab sNoteLab;

//	app的上下文对象
	private Context mAppContext;

//	构造器为私有，则说明是一个单例类，只能产生一个实例对象
	private NoteLab(Context appContext) {
		mAppContext = appContext;
		mSerializer = new NoteJSONSerializer(mAppContext, FILENAME);
		mNotes = new ArrayList<Note>();
		try {
			mNotes= (ArrayList<Note>) MyCalenderEventsLab.getMyCalenderEventList(appContext);
			mNotes.addAll(mSerializer.loadNotes());
			Log.d(TAG, "load date from file");
		} catch (Exception e) {
			Log.d(TAG, "第一次启动应用?");
		}
	}

//	将一个日程添加到日程集合中
	public void addNote(Note n) {
		mNotes.add(n);
	}
//	删除一个日程，传入该日程
	public void deleteNote(Note n) {
		mNotes.remove(n);
		if (n.getAndroid_id()!=null) {
			if(MyCalenderEventsLab.deleteNote(mAppContext, n)){
				Toast.makeText(mAppContext,"删除系统日程成功！",Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(mAppContext,"删除系统日程失败！",Toast.LENGTH_SHORT).show();
			}
		}
	}
//	保存日程集合至json文件
	public boolean saveNotes() {
		try {
			mSerializer.saveNotes(mNotes);
			Log.d(TAG, "saved to file");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
//	得到日程集合
	public ArrayList<Note> getNotes() {
		return mNotes;
	}

	//	通过id得到日程对象  基本用不上，因为不可能知道uuid值
	public Note getNote(UUID id) {
		for (Note c : mNotes) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	// 单例,得到当前NoteLab的实例
	public static NoteLab get(Context c) {
		if (sNoteLab == null) {
			Log.d(TAG, "单例");
			sNoteLab = new NoteLab(c.getApplicationContext());
		}
		return sNoteLab;
	}
}
