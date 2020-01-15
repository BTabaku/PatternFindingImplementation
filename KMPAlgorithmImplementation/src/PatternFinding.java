import java.util.Stack;

/**
 * Project Name: KMPAlgorithmImplementation
 * Created With: IntelliJ IDEA.
 * Author: Baftjar TABAKU
 **/
public class PatternFinding {
    /*Indexes*/

    /*Main boolean function that check */
    private boolean check_if_is_Checked(int value_to_check, Stack<Integer> stack_of_indexes) {
        for (int i = 0; i < stack_of_indexes.size(); i++) {
//            I added a special case where some index text location might be 2x times more than threshold
            if (stack_of_indexes.elementAt(i) + 2 > value_to_check && value_to_check + 2 * 3 > stack_of_indexes.elementAt(i)) {
                return false;
            }
        }
        return true;
    }

    //    An approach of KMP algorithm for pattern matching
    public void KMPSearch(String pat, String txt, int cnt_offset, Stack<Integer> stack_of_indexes) {

        int M = pat.length();
        int N = txt.length();

        int lps[] = new int[M];
        int jPrev = 0, j = 0;

//        Part of KMP Algorithm, compute the prefix table
        computeLPSArray(pat, M, lps);

        int i = 0; // index for txt[]
        while (i < N) {
//            We add I so we can recheck
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }

            if (j == M) {
                //Store the value
                String content = "Sequence of length = " + pat.length() + " found at haystack offset " + (i - j) + " , needle offset " + (cnt_offset);

//               ----------main check part--------------------
//                This will check when and index of an certain location in the text is checked or not
//                before proceeding further, i made it simpler with just a print but i could add
//                an array object of a streing content and index and so on , but in this case I am not sorting too

                if (check_if_is_Checked(i, stack_of_indexes)) {
                    System.out.println(content);
                }

                /*Before adding an reference index we avoid adding the same index for 2 times*/
                if (!check_if_exist(stack_of_indexes, i))
                    stack_of_indexes.add(i);

                j = lps[j - 1];
//                Inserting content to obtain an certain location or index
            }
            // mismatch after j matches
            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
    }

    //  if an index of an occurrence  it wont add for the second time one the stack
    private boolean check_if_exist(Stack<Integer> stack, int value) {
        for (int i = 0; i < stack.size(); i++) {
            int tmp = stack.elementAt(i);
            if (tmp == value) {
                return true;
            }
        }
        return false;
    }

    private void computeLPSArray(String pat, int M, int lps[]) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                if (len != 0) {
                    len = lps[len - 1];

                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    // Driver program to test above function, the main text and the patterns
    public static void main(String args[]) {
        String haystack = "vnk2435kvabco8awkh125kjneytbcd12qjhb4acd123xmnbqwnw4";
        String temp = "", needle = "abcd1234";

        int threshold = 3;
        Stack<Integer> stack_of_indexes = new Stack<Integer>();

        for (int cnt = needle.length(); cnt >= threshold; cnt--)
            for (int i = 0; i <= needle.length(); ++i) {
                try {
                    temp = String.copyValueOf(needle.toCharArray(), i, cnt);
                    new PatternFinding().KMPSearch(temp, haystack, i, stack_of_indexes);
                } catch (Exception e) {
//                since it was a out border Warning (not error) I set it to null
                }
            }

    }

}
