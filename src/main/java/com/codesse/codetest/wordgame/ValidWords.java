package com.codesse.codetest.wordgame;

/**
 * Copyright (C) 2022 Codesse. All rights reserved.
 * ••••••••••••••••••••••••••••••••••••••••••••••••
 *
 * Implementations of this interface guarantee to be thread-safe.
 */
public interface ValidWords {

	/**
	 * @param word the word to check against the valid words collection
	 * @return true if the valid words collection contains the word
	 */
	public boolean contains(String word);

	/**
	 * The size of the valid words collection.
	 *
	 * @return the size of the collection
	 */
	public int size();
}
