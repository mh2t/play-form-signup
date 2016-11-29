package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import views.formdata.BusinessFormData;

/**
 * Represent a Duration.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Some methods to facilitate form display and manipulation (makeDurationMap, etc.).
 * <li> Some fields and methods to "fake" a database of Duration.
 * </ul>
 */
public class Duration {
  private long id;
  private String name;

  public Duration(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Create a map of Duration name -> boolean where the boolean is true if the Duration corresponds to the business.
   * @param business A business with a Duration.
   * @return A map of Duration to boolean indicating which one is the business's Duration.
   */
  public static Map<String, Boolean> makeDurationMap(BusinessFormData business) {
    Map<String, Boolean> durationMap = new TreeMap<String, Boolean>();
    for (Duration period : allDuration) {
      durationMap.put(period.getName(),  (business == null) ? false : (business.period != null && business.period.equals(period.getName())));
    }
    return durationMap;
  }

  /**
   * @return A list of Duration ranges in sorted order.
   */
  public static List<String> getPeriodList() {
    String[] nameArray = {"4 weeks", "3 weeks", "2 weeks", "1 week"};
    return Arrays.asList(nameArray);
  }

  /**
   * Return the Duration instance in the database with name 'period' or null if not found.
   * @param Duration The period
   * @return The Duration instance, or null.
   */
  public static Duration findDuration(String periodName) {
    for (Duration period : allDuration) {
      if (periodName.equals(period.getName())) {
        return period;
      }
    }
    return null;
  }

  /**
   * Define a default Duration, used for form display.
   * @return The default Duration.
   */
  public static Duration getDefaultPeriod() {
    return findDuration("4 weeks");
  }

  @Override
  public String toString() {
    return String.format("[Duration %s]", this.name);
  }

  /** Fake a database of Duration. */
  private static List<Duration> allDuration = new ArrayList<>();

  /** Instantiate the fake database of Duration. */
  static {
    allDuration.add(new Duration(1L, "4 weeks"));
    allDuration.add(new Duration(2L, "3 weeks"));
    allDuration.add(new Duration(3L, "2 weeks"));
    allDuration.add(new Duration(4L, "1 week"));

  }


}
