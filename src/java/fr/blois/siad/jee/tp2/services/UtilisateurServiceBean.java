package fr.blois.siad.jee.tp2.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import fr.blois.siad.jee.tp2.dto.*;
import fr.blois.siad.jee.tp2.entities.UtilisateurEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Remote(UtilisateurService.class)
@Stateless(mappedName = "UtilisateurService")
public class UtilisateurServiceBean implements UtilisateurService {

    @PersistenceContext
    EntityManager em;
    
    public static final int PASDETRI = 0;
    public static final int TRIIDASC = 10;
    public static final int TRIIDDESC = 11;
    public static final int TRINOMASC = 20;
    public static final int TRINOMDESC = 21;
    public static final int TRIEMAILASC = 30;
    public static final int TRIEMAILDESC = 31;
    
    public List<Utilisateur> getUtilisateurs(int tri) {
        em.flush();
        String quString = "SELECT u FROM UtilisateurEntity u";
        
        switch(tri) {
            case TRIIDASC :
                quString += " ORDER BY u.id asc";
                break;
            case TRIIDDESC :
                quString += " ORDER BY u.id desc";
                break;
            case TRINOMASC :
                quString += " ORDER BY u.nom asc";
                break;
            case TRINOMDESC :
                quString += " ORDER BY u.nom desc";
                break;
            case TRIEMAILASC :
                quString += " ORDER BY u.email asc";
                break;
            case TRIEMAILDESC :
                quString += " ORDER BY u.email desc";
                break;
            default :
                break;
        }
        
        Query q = em.createQuery(quString);
        List<UtilisateurEntity> entities = q.getResultList();
        List<Utilisateur> utilisateurs  = new ArrayList<>();
        if (entities != null) {
            for(UtilisateurEntity e : entities){
                em.refresh(e);
                utilisateurs.add(e.getDTO());
            }
        }
       
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> listerTous(int tri) {
        return getUtilisateurs(tri);   
    }

    @Override
    public Utilisateur lire(Integer id) {
        try {
            UtilisateurEntity entity = em.find(UtilisateurEntity.class, id);
            return entity.getDTO();
        }
        catch(NoResultException nre){}
        return null;
    }

    @Override
    public void ajouter(Utilisateur u) {
        if (u != null) {
            em.persist(new UtilisateurEntity(u.getEmail(),   u.getMotDePasse(), u.getNom(), new Date())); 
        }
    }

    @Override
    public void changeMDP(Integer id, String nouveauMotDePasse) {
        try {
            UtilisateurEntity entity = em.find(UtilisateurEntity.class, id);
            entity.setMotDePasse(nouveauMotDePasse);
            em.merge(entity);
            em.flush();
        } catch (NoResultException nre){
            
        }
    }
    

    @Override
    public void supprimer(Integer id) {
        try {
            UtilisateurEntity entity = em.find(UtilisateurEntity.class, id);
            em.remove(entity);
        }
        catch(NoResultException nre){}
    }

    @Override
    public void bloquer(Integer id) {
        try {
            UtilisateurEntity entity = em.find(UtilisateurEntity.class, id);
            entity.setBloque(true);
            em.merge(entity);
            em.flush();
        }catch(NoResultException nre) {
            System.out.println("NoResult");
        }
    }
    
    @Override
    public void debloquer(Integer id) {
        try {
            UtilisateurEntity entity = em.find(UtilisateurEntity.class, id);
            entity.setBloque(false);
            em.merge(entity);
        }catch(NoResultException nre) {}
    }
}
