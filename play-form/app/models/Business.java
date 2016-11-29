package models;

import org.mindrot.jbcrypt.BCrypt;
import views.formdata.BusinessFormData;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple model class to represent business.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Methods to facilitate form display (makeBusinessFormData).
 * <li> Some fields and methods to "fake" a database of business, including valid and invalid.
 * </ul> 
 */
public class Business {
  private long id;
  private String name;
  private String password;
  private List<Method> methods = new ArrayList<>(); // Methods are optional.
  private String level;
  private Duration duration;
  private List<Major> majors = new ArrayList<>(); // Majors are optional.

  /** Model entities typically want to have a no-arg constructor. */
  public Business() {
  }

  public Business(long id, String name, String password, String level, Duration duration) {
    this.setId(id);
    this.name = name;
    this.password = password;
    this.level = level;
    this.duration = duration;
  }
  
  /**
   * @return the id
   */
  private long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  private void setId(long id) {
    this.id = id;
  }

  public boolean hasMethod(String methodName) {
    for (Method method : this.methods) {
      if (methodName.equals(method.getName()))
        return true;
    }
    return false;
  }

  public void addMethod(Method method) {
    this.methods.add(method);
  }

  public boolean hasMajor(String majorName) {
    for (Major major : this.getMajors()) {
      if (majorName.equals(major.getName()))
        return true;
    }
    return false;
  }

  public String toString() {
    return String.format("[User name: '%s' Password: '%s' Method: %s Email: %s Duration: %s Business: %s]", this.getName(),
        this.getPassword(), this.methods, this.level, this.duration, this.getMajors());
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the level
   */
  public String getLevel() {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(String level) {
    this.level = level;
  }

  /**
   * @return the period
   */
  public Duration getDuration() {
    return duration;
  }

  /**
   * @param duration the period to set
   */
  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  /**
   * @return the majors
   */
  public List<Major> getMajors() {
    return majors;
  }

  /**
   * @param majors the majors to set
   */
  public void setMajors(List<Major> majors) {
    this.majors = majors;
  }

  public void addMajor(Major major) {
    this.majors.add(major);
  }

  /**
   * Return a BusinessFormData instance constructed from a business instance.
   * @param id The ID of a business instance.
   * @return The BusinessFormData instance, or throws a RuntimeException.
   */
  public static BusinessFormData makeBusinessFormData(long id) {
    for (Business business : allBusinesses) {
      if (business.getId() == id) {
        return new BusinessFormData(business.name, business.password, business.level, business.duration, business.methods, business.majors);
      }
    }
    throw new RuntimeException("Couldn't find user");
  }
  

  /**
   * Returns a Business instance created from the form data.
   * Assumes that the formData has been validated.
   * The ID field is not assigned or managed in this application.
   * @param formData The business form data.
   * @return A business instance.
   */
  public static Business makeInstance(BusinessFormData formData) {
    Business business = new Business();
    business.name = formData.name;
    business.password = BCrypt.hashpw(formData.password, BCrypt.gensalt());
    for (String method : formData.methods) {
      business.methods.add(Method.findMethod(method));
    }
    business.level = formData.level;
    business.duration = Duration.findDuration(formData.period);
    for (String major : formData.majors) {
      business.majors.add(Major.findMajor(major));
    }
    return business;
  }
  
  
  /** Fake a database of business. */
  private static List<Business> allBusinesses = new ArrayList<>();

  /** Populate the fake database with both valid and invalid business, just for tutorial purposes.*/
  static {
    // Valid business. No optional data supplied.
    allBusinesses.add(new Business(1L, "Hossein", "mypassword", "hossein@example.com", Duration.findDuration("4 weeks")));
    // Valid business with optional data.
    allBusinesses.add(new Business(2L, "Valentin", "mypassword", "Val@example.com", Duration.findDuration("4 weeks")));
    getById(2L).addMethod(Method.findMethod("Web"));
    getById(2L).addMethod(Method.findMethod("Email"));
    getById(2L).addMajor(Major.findMajor("Text"));
    getById(2L).addMajor(Major.findMajor("Mail"));
    // Invalid business. Password is too short.
    allBusinesses.add(new Business(3L, "Neda", "pass", "neda@exmple.com", Duration.findDuration("1 week")));
  }
  
  /**
   * Find a business instance given the ID.
   * @param id The id of the business.
   * @return The Business instance, or throws a RuntimeException.
   */
  public static Business getById(long id) {
    for (Business business : allBusinesses) {
      if (business.getId() == id) {
        return business;
      }
    }
    throw new RuntimeException("Couldn't find user");
  }

}
