package nl.larsdenbakker.app;

/**
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

   protected void _load() throws UserInputException {
   }

   @Override
   public void unload() {
      if (loaded) {
         _unload();
      }
   }

   protected void _unload() {
   }

   @Override
   public void saveToDisk() throws UserInputException {
      if (loaded) {
         _saveToDisk();
      }
   }

   protected void _saveToDisk() throws UserInputException {
   }

   @Override
   public void shutdown() {
      if (loaded) {
         _shutdown();
      }
   }

   protected void _shutdown() {
   }

}
