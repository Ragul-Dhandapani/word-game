package com.codesse.codetest.wordgame;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Copyright (C) 2022 Codesse. All rights reserved.
 * ••••••••••••••••••••••••••••••••••••••••••••••••
 */
public class SubmissionTest {

    static ValidWords validWords;
    WordGame service;

    @BeforeClass
    public static void oneTimeSetUp() {
        validWords = new ValidWordsImpl();
    }

    @Before
    public void setUp() throws Exception {
        service = new WordGameImpl("areallylongword", validWords);
    }

    @Test
    public void testSubmission() throws Exception {

        assertEquals(3, service.submitWord("player1", "all"));
        assertEquals(4, service.submitWord("player2", "word"));
        assertEquals(0, service.submitWord("player3", "tale")); //0
        assertEquals(0, service.submitWord("player4", "glly")); //0
        assertEquals(6, service.submitWord("player5", "woolly"));
        assertEquals(0, service.submitWord("player6", "adder")); //0
        assertEquals(3, service.submitWord("player7", "all")); //it should not be available because same word used already
        assertEquals(3, service.submitWord("player8", "aal"));


        assertEquals("player5", service.getPlayerNameAtPosition(0));
        assertEquals("player2", service.getPlayerNameAtPosition(1));
        assertEquals("player1", service.getPlayerNameAtPosition(2));

        assertEquals(3, (int) service.getScoreAtPosition(2));
        assertEquals("woolly", service.getWordEntryAtPosition(0));
    }

    //	================== > Score board ================== >
    // 0 (highest) -> player5 => 6 points
    // 1  -> player2 => 4 points
    // 2  -> player1  & Player8 => 3 points but player1 is entering first
}
