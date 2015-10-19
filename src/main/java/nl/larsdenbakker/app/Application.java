package nl.larsdenbakker.app;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Lars den Bakker<larsdenbakker@gmail.com>
 */
public interface Application {

   public String getName();

   public Console getConsole();

   public void load() throws UserInputException;

   public boolean isLoaded(Class<? extends Module> type);

   public <T extends Module> T loadModule(Class<T> type) throws UserInputException;

   public <T extends Module> T loadModule(Class<T> type, Map<Class<?>, Object> constructorArguments) throws UserInputException;

   public <T extends Module> T getModule(Class<T> type);

   public void unload();

   public boolean canShutdown();

   public void shutdown();

   public void saveToDisk() throws UserInputException;

   public Collection<Module> getLoadedModules();

   public default File getFolder() {
      return new File(getName() + File.separator);
   }

   public default File getModulesFolder() {
      return new File(getFolder() + File.separator + "modules");
   }

}
