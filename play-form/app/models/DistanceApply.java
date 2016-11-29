package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represent a student distance level.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Some methods to facilitate form display and manipulation (getNameList, etc.).
 * <li> Some fields and methods to "fake" a database of distanceLevels.
 * </ul>
 */
public class DistanceApply {
  private long id;
  private String name;

  /**
   * Create a new distance Level.
   * @param id The id.
   * @param name The name of the distance.
   */
  public DistanceApply(long id, String name) {
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
   * Provide a list of names for use in form display.
   * @return A list of level names in sorted order.
   */
  public static List<String> getNameList() {
    String[] nameArray = {""};
    return Arrays.asList(nameArray);
  }

  /**
   * Return the DistanceApply instance in the database with name 'levelName' or null if not found.
   * @param levelName The Level name.
   * @return The DistanceApply instance, or null if not found.
   */
  public static DistanceApply findLevel(String levelName) {
    for (DistanceApply level : allLevels) {
      if (levelName.equals(level.getName())) {
        return level;
      }
    }
    return null;
  }

  /**
   * Provide a default distance level for use in form display.
   * @return The default distance level.
   */
  public static DistanceApply getDefaultLevel() {
    return findLevel("Email");
  }

  @Override
  public String toString() {
    return String.format("[Email %s]", this.name);
  }

  /** Fake a database of distance Levels. */
  private static List<DistanceApply> allLevels = new ArrayList<>();

  /** Instantiate the fake database. */
  static {
    allLevels.add(new DistanceApply(1L, "Email"));

  }


}
