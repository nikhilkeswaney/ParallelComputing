// ###################################################################
//
// This file is used to find the TF-IDF score of the emails. And to select the top
// words of all the words to calculate similarity
//
// Autjor: Nikhil Keswaney, Cliffton Fernandes
// Last Modified: 07-Dec-2018
//
// ###################################################################

import edu.rit.pj2.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is to calculate the TF-IDF scores and the similarity words
 *
 */
public class MakeScoresSeq extends Task {

    private ArrayList<Word> allWords;
    private ArrayList<Email> emailsClassified;
    private ArrayList<Email> emailsUnClassified;
    private ArrayList<Email> emails;

    public void main
    (String[] args) throws IOException {
        FindImportantWords(args);
    }

    /**
     * This method will be called by either the main method or the classifier method
     * @param args args of the file address
     * @throws IOException
     */
    public void FindImportantWords(String args[]) throws IOException {
        commonFunctions cf = new commonFunctions();
        int numberOfEmails = 0, numberOfSpamEmails = 0 , numberOfHamEmails = 0;

        // An arraylist of all the emails
        emails = new ArrayList<>();

        // Arraylist of all the classified emails
        emailsClassified = new ArrayList<>();

        // An arraylist of all the unclassified emails
        emailsUnClassified = new ArrayList<>();

        // A dictionary of all the words.
        allWords = new ArrayList<>();

        // An arraylist of all the top words selected.
        ArrayList<tupleToSortWords> allWordsSelected = new ArrayList<>();

        // A hash map for storing all the word counts
        HashMap<String, Word> totalWordCount = new HashMap<>();

        // This method will be called to fill in all the arraylists of emails.
        CountOfEmails count = cf.createRecorde(args, emails, emailsClassified, emailsUnClassified);
        numberOfSpamEmails = count.getNumberOfSpamEmails();
        numberOfHamEmails = count.getNumberOfHamEmails();

        Email emailCurrent;
        String[] wordsInCurrentEmail;


        // This for loop iterates through all the emails
        for (int j = 0; j < emails.size(); j++) {
            emailCurrent = emails.get(j);
            wordsInCurrentEmail = emailCurrent.getContent().split(" ");
            String word;
            double scorEmail, maxEmailScore = 0;

            // This for loop iterates through all the words in the email
            // and generates the Term - Frequency score of that word sequentially
            for (int i = 0; i < wordsInCurrentEmail.length; i++) {
                word = wordsInCurrentEmail[i].toLowerCase();
                if(!word.equals("")) {
                    if (!emailCurrent.wordsMake.containsKey(word)) {
                        emailCurrent.wordsMake.put(word, 1.0);
                    }
                    else {
                        scorEmail = emailCurrent.wordsMake.get(word) + 1.0;
                        if (maxEmailScore < scorEmail) {
                            maxEmailScore = scorEmail;
                        }
                        emailCurrent.wordsMake.put(word, scorEmail);
                    }
                }
            }
            emailCurrent.setMaxTFScore(maxEmailScore);
            emailCurrent.makeTF();
        }

        // This method is called to make Read the IDF File, Generate TF-IDF, and
        // Create an array list of all the selected words.
        cf.readFromIDFFileAndSelect(args, totalWordCount,allWordsSelected, emails,
                numberOfHamEmails, numberOfSpamEmails, allWords);
    }

    /**
     * This method is called to get the hash-map of all the words
     * @return hashmap of all the words and their IDF scores
     */
    public ArrayList<Word> getWords() {
        return allWords;
    }

    /**
     * This method is called to get the araylist of all the classified emails
     * @return
     */
    public ArrayList<Email> getClassifiedEmails() {
        return emailsClassified;
    }

    /**
     * This method is called to get the arraylist of all the unclassified email
     * @return unclassified emails.
     */
    public ArrayList<Email> getUnClassifiedEmails() {
        return emailsUnClassified;
    }


}
