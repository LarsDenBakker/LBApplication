package nl.larsdenbakker.app;

import nl.larsdenbakker.util.AbstractMessageable;

/**
 * @author Lars den Bakker <larsdenbakker at gmail.com>
 */
public class ApplicationUser extends AbstractMessageable {

   private boolean adminAccess;
   private boolean rootAccess;

   public ApplicationUser(String username, boolean adminAccess, boolean rootAccess) {
      super(username);
      this.adminAccess = adminAccess;
      this.rootAccess = rootAccess;
   }

   public ApplicationUser(String username, boolean admin) {
      this(username, admin, false);
   }

   public ApplicationUser(String username) {
      this(username, false, false);
   }

   public boolean hasRootAccess() {
      return rootAccess;
   }

   public boolean hasAdminAccess() {
      return adminAccess;
   }

   @Override
   public void message(Exception exception) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

}
