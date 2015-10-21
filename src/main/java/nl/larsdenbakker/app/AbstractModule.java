package nl.larsdenbakker.app;

/**
 * Helper implementation of Module.
 *
 * @author Lars den Bakker<larsdenbakker@gmail.com>
 */
public abstract class AbstractModule implements Module {

   private final Application app;

   private boolean loaded = false;

   public AbstractModule(Application app) {
      this.app = app;
   }

   @Override
   public Application getParentApplication() {
      return app;
   }

   @Override
   public boolean isLoaded() {
      return loaded;
   }

   @Override
   public void load() throws UserInputException {
      try {
         if (!loaded) {
            _load();
            loaded = true;
         }
      } catch (UserInputException ex) {
         throw ex.addFailedAction("loading module " + getName());
      }
   }

   /**
    * Subclass implementation of load(). Override this instead of load().
    *
    * @throws UserInputException if something went wrong during loading. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   protected void _load() throws UserInputException {
   }

   @Override
   public void unload() {
      if (loaded) {
         _unload();
      }
   }

   /**
    * Subclass implementation of unload().
    */
   protected void _unload() {
   }

   @Override
   public void saveToDisk() throws UserInputException {
      if (loaded) {
         _saveToDisk();
      }
   }

   /**
    * Subclass implementation of saveToDisk(). Override this instead of saveToDisk().
    *
    * @throws UserInputException if something went wrong during saving. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   protected void _saveToDisk() throws UserInputException {
   }

   @Override
   public void shutdown() {
      if (loaded) {
         _shutdown();
      }
   }

   /**
    * Subclass implementation of shutdown(). Override this instead of shutdown().
    */
   protected void _shutdown() {
   }

}
