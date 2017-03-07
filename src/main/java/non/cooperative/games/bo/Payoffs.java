package non.cooperative.games.bo;

/**
 * Created by U560821 on 2017.03.07..
 */
public class Payoffs {
  private float contributorPayoff;
  private float defectorPayoff;

  public Payoffs(float contributorPayoff, float defectorPayoff) {
    this.contributorPayoff = contributorPayoff;
    this.defectorPayoff = defectorPayoff;
  }

  public float getContributorPayoff() {
    return contributorPayoff;
  }

  public void setContributorPayoff(float contributorPayoff) {
    this.contributorPayoff = contributorPayoff;
  }

  public float getDefectorPayoff() {
    return defectorPayoff;
  }

  public void setDefectorPayoff(float defectorPayoff) {
    this.defectorPayoff = defectorPayoff;
  }
}
