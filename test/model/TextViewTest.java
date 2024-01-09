package model;

import org.junit.Assert;
import org.junit.Test;

import view.ReversiTextualView;
import view.TextualView;

/**
 * Test class for a Reversi model textual view representation.
 */

public class TextViewTest {
  private final ReversiModel model = new BasicReversi(11);
  private final TextualView view = new ReversiTextualView(model);

  @Test
  public void testStandardBoardTextualView() {

    System.out.println(view);
    Assert.assertEquals("     _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ _ _ \n" +
            " _ _ _ _ X O _ _ _ _ \n" +
            "_ _ _ _ O _ X _ _ _ _ \n" +
            " _ _ _ _ X O _ _ _ _  \n" +
            "  _ _ _ _ _ _ _ _ _   \n" +
            "   _ _ _ _ _ _ _ _    \n" +
            "    _ _ _ _ _ _ _     \n" +
            "     _ _ _ _ _ _      ", view.toString());


  }


}
