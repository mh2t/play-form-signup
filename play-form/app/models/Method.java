package models;

import views.formdata.BusinessFormData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represent a student's methods.
 * This class includes:
 * <ul>
 * <li> The model structure (fields, plus getters and setters).
 * <li> Some methods to facilitate form display and manipulation (makeMethodMap, etc.).
 * <li> Some fields and methods to "fake" a database of Methods.
 * </ul>
 */
public class Method {
  private long id;
  private String name;

  public Method(long id, String name) {
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
   * Create a map of method name -> boolean including all known methods
   * and setting the boolean to true if a given method is associated with the passed business.
   * @param business A business who may have zero or more methods, or null to create a method list
   * with all unchecked boxes.
   * @return A map of method names to booleans indicating the methods associated with the business.
   */
  public static Map<String, Boolean> makeMethodMap(BusinessFormData business) {
    Map<String, Boolean> methodMap = new HashMap<String, Boolean>();
    for (Method method : allMethods) {
      methodMap.put(method.getName(), (business != null && business.methods.contains(method.getName())));
    }
    return methodMap;
  }

  /**
   * Return the Method instance in the database with name 'methodName' or null if not found.
   * @param methodName The method name.
   * @return The Method instance, or null.
   */
  public static Method findMethod(String methodName) {
    for (Method method : allMethods) {
      if (methodName.equals(method.getName())) {
        return method;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return String.format("[Method %s]", this.name);
  }

  /** Fake a database of methods. */
  private static List<Method> allMethods = new ArrayList<>();

  /** Instantiate the fake database of methods. */
  static {
    allMethods.add(new Method(1L, "Web"));
    allMethods.add(new Method(2L, "Email"));
    allMethods.add(new Method(3L, "Text"));
    allMethods.add(new Method(4L, "Mail"));
  }


}
