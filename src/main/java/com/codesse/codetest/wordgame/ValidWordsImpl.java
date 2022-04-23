package com.codesse.codetest.wordgame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Copyright (C) 2022 Codesse. All rights reserved.
 * ••••••••••••••••••••••••••••••••••••••••••••••••
 *
 * Review Comments by Ragul Dhandapani:
 *  1. Vector is not recommended for Multi-threading, and it can be accessed by only one thread at a time
 *  2. We should read the "wordlist.txt" file name from properties file and we should highly avoid the hard coding with in the code
 *  3. We should wrap the following line (this.getClass().getResourceAsStream("/wordlist.txt")) with Objects.requireNonNull
 *  	when we read any files to avoid the Null Pointer Exception
 *  3. We must catch the Specific Exception to the method like IOException, UnsupportedEncodingException ,etc.,
 *     not the Parent Exception Object.
 *	4. Considering the real-time , we should convert to lowercase while adding the words in vector variable for easy comparison
 */
public class ValidWordsImpl implements ValidWords {

	Vector v = new Vector();

	public ValidWordsImpl() {
		try {
			InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/wordlist.txt"), "utf-8");
			BufferedReader in = new BufferedReader(reader);
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				v.add(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean contains(String word) {
		return v.contains(word);
	}

	@Override
	public int size() {
		return v.size();
	}
}
