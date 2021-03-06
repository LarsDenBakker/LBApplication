package nl.larsdenbakker.app;

import java.util.ArrayList;
import java.util.List;

/**
 * An exception to indicate that something has gone wrong and the user can
 * reasonably be expected to understand the error and/or be able to do
 * something about it. This should be thrown to indicate that the user
 * has to fix configuration files, give different command line input,
 * configure external programs differently etc. This should NOT be thrown
 * to indicate any internal hardcoded errors.
 *
 * @author Lars den Bakker <larsdenbakker at gmail.com>
 */
public class UserInputException extends Exception {

   private List<String> failedActions;

   public UserInputException() {
   }

   public UserInputException(String message) {
      super(message);
   }

   public UserInputException(Throwable cause) {
      super(cause);
   }

   public UserInputException(String message, Throwable cause) {
      super(message, cause);
   }

   public UserInputException addFailedAction(String action) {
      if (failedActions == null) {
         failedActions = new ArrayList();
      }
      failedActions.add(action);
      return this;
   }

   public String getUserFriendlyErrorMessage() {
      if (failedActions != null) {
         String message = "An error occurred when ";
         for (int i = 0; i < failedActions.size(); i++) {
            String failedAction = failedActions.get(i);
            if (i + 1 != failedActions.size()) {
               message += failedAction + ", ";
            } else {
               message += failedAction + ": " + ((getMessage() != null) ? getMessage() : "Unknown error.");
            }
         }
         return message;
      } else {
         return getMessage();
      }
   }

}
