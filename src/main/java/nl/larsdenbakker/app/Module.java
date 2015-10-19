package nl.larsdenbakker.app;

import java.io.File;

/**
 *
 * @author Lars den Bakker<larsdenbakker@gmail.com>
 */
public interface Module {

   public Application getParentApplication();

   public String getName();

   public default void onUnloadOf(Module module) {

   }

   public void load() throws UserInputException;

   public void unload();

   public void saveToDisk() throws UserInputException;

   public void shutdown();

   public boolean isLoaded();

   public default boolean canShutdown() {
      return true;
   }

   public default File getModuleConfigFolder() {
      return new File(getParentApplication().getModulesFolder() + File.separator + getName() + File.separator + "configs");
   }

   public default File getModuleDataFolder() {
      return new File(getParentApplication().getModulesFolder() + File.separator + getName() + File.separator + "data");
   }

   public default Class<? extends Module>[] getDependencies() {
      return new Class[0];
   }

}
