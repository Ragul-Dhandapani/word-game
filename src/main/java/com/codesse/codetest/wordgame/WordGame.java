package com.codesse.codetest.wordgame;

/**
 * Copyright (C) 2022 Codesse. All rights reserved.
 * ••••••••••••••••••••••••••••••••••••••••••••••••
 * <p/>
 * A service interface to support a word game.
 * <p/>
 * The objective of the game is to make words using only the letters contained in a supplied starting set e.g. the
 * letters in the string "areallylongword". You can assume that the starting set of letters is all lowercase and there
 * are no whitespace or special characters.
 * <p/>
 * Implementations of this interface are intended to support multiple players playing the game for example through a
 * web or desktop application user interface. Multiple players will see the same set of starting letters and will
 * compete against each other to produce the longest valid word from those starting letters. Submitted words are validated against
 * a collection of valid words supplied in the resources/wordlist.txt file (this text file is loaded in the ValidWordsImpl class).
 * Players score one point for each letter in a valid submitted word.
 * <p/>
 * A leaderboard is built showing the highest scoring words. The maximum number of word submissions in the leaderboard
 * is 10. The leaderboard should contain the player name, the word and score, with the highest scoring word first and
 * the lowest scoring word last.
 * <p/>
 * If two submitted words have the same score, the one submitted first should have a higher position in the
 * leaderboard.
 * <p/>
 * A specific word can only appear once in the leaderboard. If a player submits a word that is already in the
 * leaderboard it should not be added to the leaderboard again (i.e. the words in the leaderboard should be unique).
 * <p/>
 * A single player can submit multiple different words. Each submission should be treated independently and be added to
 * the leaderboard if appropriate.
 * <p/>
 * Given the starting string "areallylongword", the following submissions are scored as detailed below (these are
 * included in the outline test class SubmissionTest).
 * <ul>
 * <li>"all" is an acceptable submission, with score 3. It is a valid word of length 3</li>
 * <li>"word" is an acceptable submission, with score 4. It is a valid word of length 4</li>
 * <li>"tale" is NOT an acceptable, submission. There is no "t" in the starting string</li>
 * <li>"glly" is NOT an acceptable submission. The letters are in the starting string but the word is not present
 * in the ValidWords collection</li>
 * <li>"woolly" is an acceptable submission with score 6</li>
 * <li>"adder" is NOT an acceptable submission, since there is only one "d" in the starting string</li>
 * </ul>
 * <p/>
 * Throughout this test, you need not worry about whitespace, special characters, or uppercase. You may assume that both
 * the original starting string of letters and all submissions contain none of these.
 */
public interface WordGame {

	/**
	 * Submit a word for a player.
	 * <p/>
	 * A submitted word is accepted if its letters are contained in the starting string that is provided to the game
	 * constructor AND if the word is found in the valid words collection in the ValidWords implementation.
	 * <p/>
	 * If the word is accepted and its score is high enough, the player's submission should be added to the top-ten
	 * leaderboard. If there are multiple submissions with the same score, all are accepted, but the first submission with
	 * that score should rank higher.
	 * <p/>
	 * If there are multiple submissions with the same playerName, each should be treated as a separate entry to the game
	 * and be recorded independently.
	 * <p/>
	 * NOTE: This method must be thread-safe; multiple players may be submitting words simultaneously.
	 *
	 * @param playerName name under which to record the word submission
	 * @param word       the player's submitted word. All word submissions may be assumed to be lowercase and contain no
	 *                   whitespace or special characters.
	 * @return the score for the submitted word. 0 if the word is not accepted.
	 */
	public int submitWord(String playerName, String word);

	/**
	 * Return the player name at the supplied position in the leaderboard. 0 being the highest (the best score) and 9 the
	 * lowest. You may assume that this method will never be called with position > 9.
	 *
	 * @param position index in leaderboard
	 * @return the player name at the given position in the leaderboard, or null if there is no entry at that position
	 */
	String getPlayerNameAtPosition(int position);

	/**
	 * Return the word entry at the supplied position in the leaderboard. 0 being the highest (the best score) and 9 the
	 * lowest. You may assume that this method will never be called with position > 9.
	 *
	 * @param position index on leaderboard
	 * @return word entry at given position in the leaderboard, or null if there is no entry at that position
	 */
	String getWordEntryAtPosition(int position);

	/**
	 * Return score at given position in the leaderboard, 0 being the highest (best score) and 9 the lowest. You may assume
	 * that this method will never be called with position > 9.
	 *
	 * @param position index in leaderboard
	 * @return score at given position in the leaderboard, or null if there is no entry at that position
	 */
	Integer getScoreAtPosition(int position);
}
