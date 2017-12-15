package fr.blois.bdma.jee.projet.inscription.services;

import fr.blois.bdma.jee.projet.inscription.dto.User;
import java.util.List;

public interface UserService {
    
    /**
     * List all the users in the database
     * @param sortingType determines the sorting type to apply
     * @return 
     */
    List<User> listAll(int sortingType);
    
    /**
     * Get a sigle user identified by its id
     * @param id 
     * @return 
     */
    User get(Integer id);
    
    /**
     * Add a new user to the database
     * @param u the user to be added
     */
    void add(User u);
    
    /**
     * Remove a sigle user identified by its id 
     * @param id 
     */
    void remove(Integer id);
    
    /**
     * Lock a sigle user identified by its id
     * @param id 
     */
    void lock(Integer id);
    
    /**
     * Unlock a sigle user identified by its id
     * @param id 
     */
    void unlock(Integer id);
    
    /**
     * Change the password of a sigle user identified by its id
     * @param id
     * @param newPassword the new password of the user 
     */
    void changePassword(Integer id, String newPassword);
}
