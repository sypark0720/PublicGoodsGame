package non.cooperative.games.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by U560821 on 2017.03.12..
 */
@Data
@AllArgsConstructor
public class Graph {
  private int[][] adjacencyMatrix;
  private int[][] edgeList;
  private int[] playerContributionVector;
}
