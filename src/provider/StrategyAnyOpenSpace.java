package provider;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * This strategy chooses any open space on the Reversi game board as the next move, without
 * applying any specific intent. It is a simple and naive strategy that finds any random valid cell.
 */
public class StrategyAnyOpenSpace extends AbstractStrategy {
  private final Random random;

  /**
   * Constructs a new {@code StrategyAnyOpenSpace} instance with a default random seed.
   */
  public StrategyAnyOpenSpace() {
    random = new Random(42); // hard coded seed for testing purposes
  }

  /**
   * Constructs a new {@code StrategyAnyOpenSpace} instance with a custom seed.
   *
   * @param seed The seed value for initializing the random number generator.
   */
  public StrategyAnyOpenSpace(long seed) {
    random = new Random(seed);
  }

  @Override
  public Optional<Coordinates> chooseMove(ReadOnlyReversiModel model, Player player)
          throws IllegalArgumentException {
    Set<Coordinates> possibleMoves = model.calculateMoves(player.getColor());

    Optional<Coordinates> result = possibleMoves.isEmpty() ?
            Optional.empty() :
            possibleMoves.stream().skip(random.nextInt(possibleMoves.size())).findFirst();

    return result;
  }
}
