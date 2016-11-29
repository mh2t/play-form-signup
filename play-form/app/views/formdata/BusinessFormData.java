package views.formdata;

import models.DistanceApply;
import models.Duration;
import models.Method;
import models.Major;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Backing class for the Business data form.
 * Requirements:
 * <ul>
 * <li> All fields are public, 
 * <li> All fields are of type String or List[String].
 * <li> A public no-arg constructor.
 * <li> A validate() method that returns null or a List[ValidationError].
 * </ul>
 */
public class BusinessFormData {

  public String name = "";
  public String password = "";
  public List<String> methods = new ArrayList<>();
  public String level = "";
  public String period = "";
  public List<String> majors = new ArrayList<>(); 

  /** Required for form instantiation. */
  public BusinessFormData() {
  }

  /**
   * Creates an initialized form instance. Assumes the passed data is valid. 
   * @param name The name.
   * @param password The password.
   * @param level The level.
   * @param period The duration.
   * @param methods The methods.
   * @param majors The majors. 
   */
  public BusinessFormData(String name, String password, String level, Duration period, List<Method> methods, List<Major> majors) {
    this.name = name;
    this.password = password;
    this.level = level;
    this.period = period.getName();
    for(Method method : methods) {
      this.methods.add(method.getName());
    }
    for(Major major : majors) {
      this.majors.add(major.getName());
    }
  }

  /**
   * Validates Form<BusinessFormData>.
   * Called automatically in the controller by bindFromRequest().
   * 
   * Validation checks include:
   * <ul>
   * <li> Name must be non-empty.
   * <li> Password must be at least five characters.
   * <li> method (plural) are optional, but if specified, must exist in database.
   * <li> Level is required and must exist in database.
   * <li> duration is required and must exist in database.
   * <li> Majors (plural) are optional, but if specified, must exist in database.
   * </ul>
   *
   * @return Null if valid, or a List[ValidationError] if problems found.
   */
  public List<ValidationError> validate() {

    List<ValidationError> errors = new ArrayList<>();

    if (name == null || name.length() == 0) {
      errors.add(new ValidationError("username", "No username was given."));
    }

    if (password == null || password.length() == 0) {
      errors.add(new ValidationError("password", "No password was given."));
    } else if (password.length() < 5) {
      errors.add(new ValidationError("password", "Given password is less than five characters."));
    }

    // method are optional, but if supplied must exist in database.
    if (methods.size() > 0) {
      for (String method : methods) {
        if (Method.findMethod(method) == null) {
          errors.add(new ValidationError("method", "Unknown method: " + method + "."));
        }
      }
    }

    // Level is required and must exist in database.
    if (level == null || level.length() == 0) {
      errors.add(new ValidationError("distance", "No distance level was given."));
    }


    // duration is required and must exist in database.
    if (period == null || period.length() == 0) {
      errors.add(new ValidationError("duration", "No duration was given."));
    } else if (Duration.findDuration(period) == null) {
      errors.add(new ValidationError("duration", "Invalid duration: " + period + "."));
    }

    // Majors are optional, but if supplied must exist in database.
    if (majors.size() > 0) {
      for (String major : majors) {
        if (Major.findMajor(major) == null) {
          errors.add(new ValidationError("categories", "Unknown category: " + major + "."));
        }
      }
    }

    if(errors.size() > 0)
      return errors;

    return null;
  }
}
