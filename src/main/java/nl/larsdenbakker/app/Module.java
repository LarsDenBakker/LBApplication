package nl.larsdenbakker.app;

import java.io.File;

/**
 * Modules are objects that can be attached to an application.
 * Just like applications, they can be loaded, unloaded, saved and shut down safely.
 * Modules can also declare other modules as dependencies which must be loaded before
 * the Module itself is loaded. A Module can also respond to unloading of other Modules.
 *
 * @author Lars den Bakker<larsdenbakker@gmail.com>
 */
public interface Module {

   /**
    * @return the Application this Module belongs to. This must be guaranteed to be non-null if if isLoaded()
    *         returns true.
    */
   public Application getParentApplication();

   /**
    * @return the name used to identify this Module.
    */
   public String getName();

   /**
    * Respond to the unloading of another Module. For example to release an held resources by the given
    * Module in this Module.
    *
    * @param module the Module.
    */
   public default void onUnloadOf(Module module) {

   }

   /**
    * Load this Module.
    *
    * @throws UserInputException if something went wrong during loading. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   public void load() throws UserInputException;

   /**
    * Unload this Module.
    */
   public void unload();

   /**
    * Save any data that should be persisted to disk.
    *
    * @throws UserInputException if something went wrong during saving. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   public void saveToDisk() throws UserInputException;

   /**
    * Shut down this Module.
    */
   public void shutdown();

   /**
    * @return whether or not this Module has been loaded yet.
    */
   public boolean isLoaded();

   /**
    * @return whether or not this Module can be shut down.
    */
   public default boolean canShutdown() {
      return true;
   }

   /**
    * @return default location for this Module to store configuration information.
    */
   public default File getModuleConfigFolder() {
      return new File(getParentApplication().getModulesFolder() + File.separator + getName() + File.separator + "configs");
   }

   /**
    * @return default location for this Module to store data.
    */
   public default File getModuleDataFolder() {
      return new File(getParentApplication().getModulesFolder() + File.separator + getName() + File.separator + "data");
   }

   /**
    * @return the Modules that must be loaded in order for this Module to function.
    */
   public default Class<? extends Module>[] getDependencies() {
      return new Class[0];
   }

}
