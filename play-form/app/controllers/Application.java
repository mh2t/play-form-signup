package controllers;

import models.*;
import models.DistanceApply;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.formdata.BusinessFormData;
import views.html.Index;

/**
 * The controller for the single page of this application.
 *
 */
public class Application extends Controller {

  /**
   * Returns the page where the form is filled by the Business whose id is passed, or an empty form
   * if the id is 0.
   * @param id The id of the Business whose data is to be shown.  0 if an empty form is to be shown.
   * @return The page containing the form and data.
   */
  public static Result getIndex(long id) {
    BusinessFormData businessData = (id == 0) ? new BusinessFormData() : Business.makeBusinessFormData(id);
    Form<BusinessFormData> formData = Form.form(BusinessFormData.class).fill(businessData);
    return ok(Index.render(
      formData,
      Method.makeMethodMap(businessData),
      DistanceApply.getNameList(),
      Duration.makeDurationMap(businessData),
      Major.makeMajorMap(businessData)
    ));
  }

  /**
   * Process a form submission.
   * First we bind the HTTP POST data to an instance of BusinessFormData.
   * The binding process will invoke the BusinessFormData.validate() method.
   * If errors are found, re-render the page, displaying the error data. 
   * If errors not found, render the page with the good data. 
   * @return The index page with the results of validation. 
   */
  public static Result postIndex() {

    // Get the submitted form data from the request object, and run validation.
    Form<BusinessFormData> formData = Form.form(BusinessFormData.class).bindFromRequest();

    if (formData.hasErrors()) {
      // Don't call formData.get() when there are errors, pass 'null' to helpers instead. 
      flash("error", "Please correct errors above.");
      return badRequest(Index.render(formData,
        Method.makeMethodMap(null),
        DistanceApply.getNameList(),
        Duration.makeDurationMap(null),
        Major.makeMajorMap(null) 
      ));
    }
    else {
      // Convert the formData into a Business model instance.
      Business business = Business.makeInstance(formData.get());
      flash("success", "Business instance created/edited: " + business);
      return ok(Index.render(formData,
        Method.makeMethodMap(formData.get()),
        DistanceApply.getNameList(),
        Duration.makeDurationMap(formData.get()),
        Major.makeMajorMap(formData.get())
      ));
    }
  }
}
