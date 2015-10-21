package nl.larsdenbakker.app;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nl.larsdenbakker.util.ClassUtils;
import nl.larsdenbakker.util.CollectionUtils;

/**
 * Helper implementation of Application.
 *
 * @author Lars den Bakker <larsdenbakker at gmail.com>
 */
public abstract class AbstractApplication implements Application {

   private final Map<Class<? extends Module>, Module> modules = new HashMap();
   private final Console console;

   public AbstractApplication(Console console) {
      this.console = console;
   }

   public AbstractApplication() {
      this.console = new Console("console");
   }

   @Override
   public Console getConsole() {
      return console;
   }

   @Override
   public void load() throws UserInputException {
      _load();
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
   public <T extends Module> T loadModule(Class<T> type, Map<Class<?>, Object> constructorArguments) throws UserInputException {
      if (modules.get(type) == null) {
         try {
            //Construct instance of module type
            ArrayList<Class> constructorTypes = new ArrayList();
            constructorTypes.add(getClass());
            if (constructorArguments != null) {
               constructorTypes.addAll(constructorArguments.keySet());
            }
            Constructor<T> constr = ClassUtils.getConstructorWithSuperTypes(type, CollectionUtils.asArrayOfType(Class.class, constructorTypes));

            ArrayList<Object> constructorArgumentsValues = new ArrayList();
            constructorArgumentsValues.add(this);
            if (constructorArguments != null) {
               constructorArgumentsValues.addAll(constructorArguments.values());
            }
            T t = constr.newInstance(constructorArgumentsValues.toArray());

            //Load dependencies and return errors if failed
            if (t.getDependencies() != null) {
               for (Class<? extends Module> moduleClass : t.getDependencies()) {
                  loadModule(moduleClass); // throws UserInputException 
               }
            }
            try {
               t.load();
            } catch (UserInputException ex) {
               throw ex.addFailedAction("loading Module " + t.getName());
            }
            modules.put(type, t);
            return t;
         } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IllegalArgumentException("Unable to initialize module: " + type.getSimpleName() + ".", ex);
         }
      } else {
         return getModule(type);
      }
   }

   @Override
   public <T extends Module> T loadModule(Class<T> type) throws UserInputException {
      return loadModule(type, null);
   }

   @Override
   public void unloadModule(Class<? extends Module> type) {
      if (isLoaded(type)) {
         Module module = getModule(type);
         module.unload();
         modules.remove(type);
      }
   }

   @Override
   public <T extends Module> T getModule(Class<T> type) {
      T t = (T) modules.get(type); //safe cast garaunteed
      if (t == null) {
         throw new IllegalArgumentException("No Module of type " + type.getSimpleName() + " loaded.");
      } else if (!t.isLoaded()) {
         throw new IllegalArgumentException("Module " + t.getName() + " is not loaded.");
      } else {
         return t;
      }
   }

   @Override
   public Collection<Module> getLoadedModules() {
      return modules.values();
   }

   @Override
   public void unload() {
      _unload();
   }

   /**
    * Subclass implementation of unload(). Override this instead of unload().
    */
   protected void _unload() {
   }

   protected void unloadModules() {
      for (Module mod : getLoadedModules()) {
         mod.unload();
      }
   }

   @Override
   public boolean isLoaded(Class<? extends Module> type) {
      return modules.containsKey(type);
   }

   @Override
   public boolean canShutdown() {
      boolean canShutDown = _canShutdown();
      return (canShutDown) ? true : canShutdownModules();
   }

   /**
    * Subclass implementation of canShutdown(). Override this instead of canShutdown().
    *
    * @return whether or not this application can shut down.
    */
   protected boolean _canShutdown() {
      return true;
   }

   protected boolean canShutdownModules() {
      boolean canShutDownModules = true;
      for (Module mod : getLoadedModules()) {
         canShutDownModules = mod.canShutdown();
         if (!canShutDownModules) {
            return false;
         }
      }
      return canShutDownModules;
   }

   @Override
   public void shutdown() {
      _shutdown();
      shutdownModules();
   }

   /**
    * Subclass implementation of shutdown(). Override this instead of shutdown().
    */
   protected void _shutdown() {
   }

   protected void shutdownModules() {
      for (Module mod : getLoadedModules()) {
         mod.shutdown();
      }
   }

   @Override
   public void saveToDisk() throws UserInputException {
      _saveToDisk();
      saveModules();
   }

   /**
    * Subclass implementation of saveToDisk(). Override this instead of saveToDisk().
    *
    * @throws UserInputException if something went wrong during saving. Check the UserInputException
    *                            documentation to see when this should be thrown in implementations.
    */
   protected void _saveToDisk() throws UserInputException {
   }

   private void saveModules() throws UserInputException {
      for (Module mod : getLoadedModules()) {
         mod.saveToDisk();
      }
   }

}
