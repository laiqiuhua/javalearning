package com.learning.javalearning.locale;

import java.text.BreakIterator;
import java.util.Locale;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class BreakIteratorTest {
    public static void main(String args[]) {
        String stringToExamine = "examine test test!";
        //print each word in order
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(stringToExamine);
        printEachForward(boundary, stringToExamine);
        //print each sentence in reverse order
        boundary = BreakIterator.getSentenceInstance(Locale.US);
        boundary.setText(stringToExamine);
        printEachBackward(boundary, stringToExamine);
        printFirst(boundary, stringToExamine);
        printLast(boundary, stringToExamine);
    }

    /**
     * Print each element in order:
     *
     * @param boundary
     * @param source
     */
    public static void printEachForward(BreakIterator boundary, String source) {
        int start = boundary.first();
        for (int end = boundary.next();
             end != BreakIterator.DONE;
             start = end, end = boundary.next()) {
            System.out.println(source.substring(start, end));
        }
    }

    /**
     * Print each element in reverse order:
     *
     * @param boundary
     * @param source
     */
    public static void printEachBackward(BreakIterator boundary, String source) {
        int end = boundary.last();
        for (int start = boundary.previous();
             start != BreakIterator.DONE;
             end = start, start = boundary.previous()) {
            System.out.println(source.substring(start, end));
        }
    }

    /**
     * Print first element:
     *
     * @param boundary
     * @param source
     */
    public static void printFirst(BreakIterator boundary, String source) {
        int start = boundary.first();
        int end = boundary.next();
        System.out.println(source.substring(start, end));
    }

    /**
     * Print last element:
     *
     * @param boundary
     * @param source
     */
    public static void printLast(BreakIterator boundary, String source) {
        int end = boundary.last();
        int start = boundary.previous();
        System.out.println(source.substring(start, end));
    }

    /**
     * Print the element at a specified position:
     *
     * @param boundary
     * @param pos
     * @param source
     */
    public static void printAt(BreakIterator boundary, int pos, String source) {
        int end = boundary.following(pos);
        int start = boundary.previous();
        System.out.println(source.substring(start, end));
    }

    /**
     * Find the next word:
     *
     * @param pos
     * @param text
     * @return
     */
    public static int nextWordStartAfter(int pos, String text) {
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(text);
        int last = wb.following(pos);
        int current = wb.next();
        while (current != BreakIterator.DONE) {
            for (int p = last; p < current; p++) {
                if (Character.isLetter(text.codePointAt(p)))
                    return last;
            }
            last = current;
            current = wb.next();
        }
        return BreakIterator.DONE;
    }

}
