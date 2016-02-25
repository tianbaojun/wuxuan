package com.vtmer.yann.powernotes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class NoteJSONSerializer {
	private static final String TAG = "NoteJsonSerializer";
	
	private Context mContext;
	private String mFileName;

	/**
	 * 构造方法，传入两个参数，一个上下文对象，一个文件名也就是json文件的名称
	 * @param c
	 * @param f
	 */
	public NoteJSONSerializer (Context c, String f) {
		mContext = c;
		mFileName = f;
	}

	/**
	 * 得到json中的Note集合
	 * @return 返回json中的Note集合
	 * @throws JSONException
	 * @throws IOException
	 */
	
	public ArrayList<Note> loadNotes() throws JSONException, IOException {
		ArrayList<Note> notes = new ArrayList<Note>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFileName);
			reader  = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			for (int i = 0; i < array.length(); i++) {
				notes.add(new Note(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
			Log.d(TAG, "file not found:",e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return notes;
	}

	/**
	 * 将 Note集合写入json文件
	 * @param notes Note集合
	 * @throws JSONException JSON异常
	 * @throws IOException IO异常
	 */
	public void saveNotes(ArrayList<Note> notes)
			throws JSONException, IOException {
		JSONArray array = new JSONArray();
		for (Note n : notes) {
			array.put(n.toJSON());
		}
		
		Writer writer = null;
		try {
			OutputStream out = mContext
					.openFileOutput(mFileName, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
			Log.d(TAG,"成功保存？");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
