/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenizers;


 

import java.io.File;
import java.text.BreakIterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import org.apache.commons.lang.ArrayUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import tokenizers.Token.TokenType;

import com.ibm.icu.text.RuleBasedBreakIterator;


public class WordTokenizer {
  
 // private final Log log = LogFactory.getLog(getClass());

  
  @SuppressWarnings("unchecked")
  private final static Map<Integer,TokenType> RULE_ENTITY_MAP = 
    ArrayUtils.toMap(new Object[][] {
      {new Integer(0), TokenType.UNKNOWN},
      {new Integer(100), TokenType.NUMBER},
      {new Integer(200), TokenType.WORD},
      {new Integer(500), TokenType.ABBREVIATION},
      {new Integer(501), TokenType.WORD},
      {new Integer(502), TokenType.INTERNET},
      {new Integer(503), TokenType.INTERNET},
      {new Integer(504), TokenType.MARKUP},
      {new Integer(505), TokenType.EMOTICON},
  });
  
  private String text;
  private int index = 0;
  
  private  RuleBasedBreakIterator breakIterator;
  
  
  public WordTokenizer() throws Exception {
    super();
    this.breakIterator = new RuleBasedBreakIterator(
      FileUtils.readFileToString(
      new File("D:\\Dropbox\\sandeep2\\build\\classes\\tokenizers\\word_break_rules.txt"), "UTF-8"));
  }
  
  public void setText(String text) {
    this.text = text;
    this.breakIterator.setText(text);
    this.index = 0;
  }
  
  public Token nextToken() throws Exception {
    for (;;) {
      int end = breakIterator.next();
      if (end == BreakIterator.DONE) {
        return null;
      }
      String nextWord = text.substring(index, end);
//      log.debug("next=" + nextWord + "[" + breakIterator.getRuleStatus() + "]");
      index = end;
      return new Token(nextWord, 
        RULE_ENTITY_MAP.get(breakIterator.getRuleStatus()));
    }
  }
}
