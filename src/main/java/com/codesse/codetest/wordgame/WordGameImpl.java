package com.codesse.codetest.wordgame;


import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import org.json.JSONArray;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is the shell implementation of the WordGame interface.
 * It is the class that you should focus on when developing your solution to the Challenge.
 */
public class WordGameImpl implements WordGame {

    private static final String WORD = "Word";
    private static final String USER_SCORE = "UserScore";
    private static final String PLAYER_NAME = "PlayerName";
    private static final AtomicInteger playerCounter = new AtomicInteger(0);

    //using Multimap from guava
    private final static Multimap<Integer, ConcurrentHashMap<String, Object>> playerInformation = Multimaps.synchronizedListMultimap(MultimapBuilder.treeKeys().linkedListValues().build());
    public static final String INSERTION_ORDER = "InsertionOrder";
    private final ValidWords validWords;
    Map<Character, Integer> startingStringCharMap;

    public WordGameImpl(String startingString, ValidWords validWords) {
        this.validWords = validWords;
        startingStringCharMap = convertStringToMap(startingString);
    }

    @Override
    public int submitWord(String playerName, String word) {
        AtomicInteger userScore = new AtomicInteger(0);

        if (validWords.contains(word)) {
            Map<Character, Integer> wordMap = convertStringToMap(word);
            for (char letter : wordMap.keySet()) {
                if (startingStringCharMap.containsKey(letter)) {
                    if (wordMap.get(letter) <= startingStringCharMap.get(letter)) {
                        userScore.set(userScore.get() + wordMap.get(letter));
                    } else {
                        userScore.set(0);
                        break;
                    }
                } else {
                    userScore.set(0);
                    break;
                }
            }
        }

        ConcurrentHashMap<String, Object> playerDetails = new ConcurrentHashMap<>();
        playerDetails.put(PLAYER_NAME, playerName);
        playerDetails.put(USER_SCORE, userScore.get());
        playerDetails.put(INSERTION_ORDER, playerCounter.getAndIncrement());
        playerDetails.put(WORD, word);

        // If a player submits a word that is already in the leaderboard it should not be added to the leaderboard again => this is done based on userScore
        if (playerInformation.containsKey(userScore.get()) && userScore.get() != 0) {
            Iterator<ConcurrentHashMap<String, Object>> iterator = playerInformation.get(userScore.get()).iterator();
            while (iterator.hasNext()) {
                ConcurrentHashMap<String, Object> values = iterator.next();
                if (!values.get(WORD).equals(playerDetails.get(WORD))) {
                    playerInformation.put(userScore.get(), playerDetails);
                    break;
                }
            }
        } else {
            // This is new word and UserScore which does not exist already
            playerInformation.put(userScore.get(), playerDetails);
        }

        return userScore.get();
    }

    @Override
    public String getPlayerNameAtPosition(int position) {
        System.out.println("Player Information =>" +playerInformation);
        return getPlayerInformation(PLAYER_NAME, position);
    }

    @Override
    public String getWordEntryAtPosition(int position) {
        return getPlayerInformation(WORD, position);
    }

    @Override
    public Integer getScoreAtPosition(int position) {
        String scoreValue = getPlayerInformation(USER_SCORE, position);
        return scoreValue != null ? Integer.parseInt(scoreValue) : 0;
    }

    private Map<Character, Integer> convertStringToMap(String inputWord) {
        Map<Character, Integer> resultMap = new ConcurrentHashMap<>();
        inputWord.chars().mapToObj(x -> (char) x).forEach(letter -> {
            resultMap.put(letter, resultMap.get(letter) != null ? (resultMap.get(letter) + 1) : 1);
        });
        return resultMap;
    }

    private String getPlayerInformation(String keyName, int position) {
        String output = null;
        AtomicInteger counter = new AtomicInteger(playerInformation.keySet().size() - 1);

        //Storing keys and userScore to retrieve directly the position value (avoiding loop);
        ConcurrentHashMap<Integer, Integer> value = new ConcurrentHashMap<>();
        for (int keys : playerInformation.keySet()) {
            value.put(counter.getAndDecrement(), keys);
        }

        if (value.containsKey(position)) {
            JSONArray jsonArray = new JSONArray(playerInformation.get(value.get(position)));
            if (jsonArray.length() == 1)
                output = String.valueOf(jsonArray.getJSONObject(0).get(keyName));
            else {
                int dataIndex = 0;
                for (int index = 0; index < jsonArray.length(); index++) {
                    int nextIndex = index + 1;
                    if (nextIndex < jsonArray.length()) {
                        if (Integer.parseInt(String.valueOf(jsonArray.getJSONObject(index).get(INSERTION_ORDER)))
                                < Integer.parseInt(String.valueOf(jsonArray.getJSONObject(nextIndex).get(INSERTION_ORDER)))) {
                            dataIndex = index;
                        }
                    }
                }
                output = String.valueOf(jsonArray.getJSONObject(dataIndex).get(keyName));
            }
        }
        return output;
    }
}