package nl.larsdenbakker.app;

/**
 *
 * @author Lars den Bakker<larsdenbakker@gmail.com>
 */
public class InvalidInputException extends UserInputException {

   public InvalidInputException() {
   }

   public InvalidInputException(String message) {
      super(message);
   }

   public InvalidInputException(Throwable cause) {
      super(cause);
   }

   public InvalidInputException(String message, Throwable cause) {
      super(message, cause);
   }

   @Override
   public InvalidInputException addFailedAction(String action) {
      super.addFailedAction(action);
      return this;
   }

}
