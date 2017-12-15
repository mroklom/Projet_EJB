package fr.blois.siad.jee.tp2.services;

import fr.blois.siad.jee.tp2.dto.User;
import fr.blois.siad.jee.tp2.entities.UserEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Remote(UserService.class)
@Stateless(mappedName = "UserService")
public class UserServiceBean implements UserService{
    @PersistenceContext
    EntityManager em;
    
    public static final int NOSORTING = 0;
    public static final int IDASCSORT = 10;
    public static final int IDDESCSORT = 11;
    public static final int NAMEASCSORT = 20;
    public static final int NAMEDESCSORT = 21;
    public static final int EMAILASCSORT = 30;
    public static final int EMAILDESCSORT = 31;
    
    public List<User> getUsers(int tri) {
        em.flush();
        String quString = "SELECT u FROM UserEntity u";
        
        switch(tri) {
            case IDASCSORT :
                quString += " ORDER BY u.id asc";
                break;
            case IDDESCSORT :
                quString += " ORDER BY u.id desc";
                break;
            case NAMEASCSORT :
                quString += " ORDER BY u.name asc";
                break;
            case NAMEDESCSORT :
                quString += " ORDER BY u.name desc";
                break;
            case EMAILASCSORT :
                quString += " ORDER BY u.email asc";
                break;
            case EMAILDESCSORT :
                quString += " ORDER BY u.email desc";
                break;
            default :
                break;
        }
        
        Query q = em.createQuery(quString);
        List<UserEntity> entities = q.getResultList();
        List<User> user  = new ArrayList<>();
        if (entities != null) {
            for(UserEntity e : entities){
                em.refresh(e);
                user.add(e.getDTO());
            }
        }
       
        return user;
    }

    @Override
    public List<User> listAll(int sortingType) {
        return getUsers(sortingType);
    }

    @Override
    public User get(Integer id) {
        try {
            UserEntity entity = em.find(UserEntity.class, id);
            return entity.getDTO();            
        } catch (NoResultException nre) {
            System.err.println("cannot find user with id " + id);
        }
        
        return null;
    }

    @Override
    public void add(User u) {
        if (u != null) {
            em.persist(new UserEntity(u.getEmail(), u.getPassword(), u.getName(), new Date(), false));
        }
    }

    @Override
    public void remove(Integer id) {
        try {
            UserEntity entity = em.find(UserEntity.class, id);
            em.remove(entity);
        } catch(NoResultException nre){
            System.err.println("cannot find user with id " + id);
        }
    }

    @Override
    public void lock(Integer id) {
        try {
            UserEntity entity = em.find(UserEntity.class, id);
            entity.setLocked(true);
            em.merge(entity);
            em.flush();
        } catch(NoResultException nre) {
            System.err.println("cannot find user with id " + id);
        }
    }

    @Override
    public void unlock(Integer id) {
        try {
            UserEntity entity = em.find(UserEntity.class, id);
            entity.setLocked(false);
            em.merge(entity);
            em.flush();
        } catch(NoResultException nre) {
            System.err.println("cannot find user with id " + id);
        }
    }

    @Override
    public void changePassword(Integer id, String newPassword) {
        try {
            UserEntity entity = em.find(UserEntity.class, id);
            entity.setPassword(newPassword);
            em.merge(entity);
            em.flush();
        } catch(NoResultException nre) {
            System.err.println("cannot find user with id " + id);
        }
    }
}
