package nl.larsdenbakker.app;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * Applications are modular objects that can be loaded, unloaded, saved and shut down safely.
 * An Application can load and unload modules on the fly, keeping the application lightweight
 * by loading only what is necessary.
 *
 * @author Lars den Bakker<larsdenbakker@gmail.com>
 */
public interface Application {

   /**
    * @return The name of this application.
    */
   public String getName();

   /**
    * @return the console associated with this Application.
    */
   public Console getConsole();

   /**
    * Load this application.
    *
    * @throws UserInputException if something went wrong during loading. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   public void load() throws UserInputException;

   /**
    * @param type The module type.
    *
    * @return whether or not a Module is loaded.
    */
   public boolean isLoaded(Class<? extends Module> type);

   /**
    *
    * @param <T>  The module type.
    * @param type The module type class.
    *
    * @return the loaded module. Non-null.
    *
    * @throws UserInputException if something went wrong during loading. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   public <T extends Module> T loadModule(Class<T> type) throws UserInputException;

   /**
    *
    * @param <T>                  The module type.
    * @param type                 The module type class.
    * @param constructorArguments Arguments to pass to the constructor.
    *
    * @return the loaded module. Non-null.
    *
    * @throws UserInputException if something went wrong during loading. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   public <T extends Module> T loadModule(Class<T> type, Map<Class<?>, Object> constructorArguments) throws UserInputException;

   /**
    * Unload a module.
    *
    * @param type The module type.
    */
   public void unloadModule(Class<? extends Module> type);

   /**
    * Unload a module.
    *
    * @param module The module. Must be loaded in this application.
    */
   public default void unloadModule(Module module) {
      unloadModule(module.getClass());
   }

   /**
    *
    * @param <T>  The module type.
    * @param type The module type class.
    *
    * @return instance of the module type. Must be loaded in this application. Check isLoaded(Class<? extends Module>) first.
    */
   public <T extends Module> T getModule(Class<T> type);

   /**
    * Unload this application.
    */
   public void unload();

   /**
    * @return Whether or not this application can be shut down.
    */
   public boolean canShutdown();

   /**
    * Shut down this application.
    */
   public void shutdown();

   /**
    * Save any data that should be persisted to disk.
    *
    * @throws UserInputException if something went wrong during saving. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   public void saveToDisk() throws UserInputException;

   /**
    * @return all modules loaded by this application.
    */
   public Collection<Module> getLoadedModules();

   /**
    * @return The default folder for this application.
    */
   public default File getFolder() {
      return new File(getName() + File.separator);
   }

   /**
    * @return The default module folder for this application.
    */
   public default File getModulesFolder() {
      return new File(getFolder() + File.separator + "modules");
   }

}
