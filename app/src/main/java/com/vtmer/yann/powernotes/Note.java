package com.vtmer.yann.powernotes;

import android.annotation.TargetApi;
import android.os.Build;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Note {

	private UUID mId;
	private String mTitle;
	private String mContent;
	private Date mDate;
	private boolean mSolved;
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_CONTENT = "content";
	private static final String JSON_DATE = "date";
	private static final String JSON_SOLVED = "solved";
	
	public Note() {
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	public Note(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		if (json.has(JSON_TITLE)) mTitle = json.getString(JSON_TITLE);
		if (json.has(JSON_CONTENT)) mContent = json.getString(JSON_CONTENT);
		mDate = new Date(json.getLong(JSON_DATE));
		mSolved = json.getBoolean(JSON_SOLVED);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}

	public UUID getId() {
		return mId;
	}
	
	public String dateFormat(Date date) {
		SimpleDateFormat f  = new SimpleDateFormat("yyyy-MM-dd  E kk:mm");
		return f.format(date);
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_CONTENT, mContent);
		json.put(JSON_DATE, mDate.getTime());
		json.put(JSON_SOLVED, mSolved);
		
		return json;
	}

	public static Comparator<Note> NoteDateComparator = new Comparator<Note> () {
		
		public int compare(Note n1, Note n2) {
			Date NoteDate1 =  n1.getDate();
			Date NoteDate2 =  n2.getDate();
			
			return NoteDate1.compareTo(NoteDate2);
		}
	};
	
	public static Comparator<Note> NoteTitleComparator = new Comparator<Note> () {
		
		public int compare(Note n1, Note n2) {
			String NoteTitle1 =  n1.getTitle().toUpperCase();
			String NoteTitle2 =  n2.getTitle().toUpperCase();
			
			return NoteTitle1.compareTo(NoteTitle2);
		}
	};
	
	public static Comparator<Note> NoteSolvedComparator = new Comparator<Note> () {
		
		@TargetApi(Build.VERSION_CODES.KITKAT)
		public int compare(Note n1, Note n2) {
			boolean NoteSolved1 =  n1.isSolved();
			boolean NoteSolved2 =  n2.isSolved();
			
			
			return Boolean.compare(NoteSolved1, NoteSolved2);
		}
	};
	
}
