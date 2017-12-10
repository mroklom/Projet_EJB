package fr.blois.siad.jee.tp2.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import fr.blois.siad.jee.tp2.dto.*;
import fr.blois.siad.jee.tp2.entities.UtilisateurEntity;
import java.util.Collections;
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
    
    private boolean trieIDAsc = true;
    
    @PostConstruct
    private void _initMap() {
        //em.persist(new UtilisateurEntity("a.dupont@gmail.com", "dupont1234", "Alexandre DUPONT", new Date()));
        //em.persist(new UtilisateurEntity("b.thierry@gmail.com", "thierry1234", "Béatrice THIERRY", new Date()));
    }
    
    public List<Utilisateur> getUtilisateurs() {
        Query q = em.createQuery("SELECT u FROM UtilisateurEntity u");
        List<UtilisateurEntity> entities = q.getResultList();
        List<Utilisateur> utilisateurs  = new ArrayList<Utilisateur>();
        if (entities != null) {
            for(UtilisateurEntity e : entities) utilisateurs.add(e.getDTO());
        }
        
        Collections.shuffle(utilisateurs);
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> listerTous() {
        return getUtilisateurs();   
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
    public void supprimer(Integer id) {
        try {
            UtilisateurEntity entity = em.find(UtilisateurEntity.class, id);
            em.remove(entity);
        }
        catch(NoResultException nre){}
    }

    @Override
    public List<Utilisateur> listerUtilisateurTrieID() {
        List<Utilisateur> utilisateurs = getUtilisateurs();
        
        if(trieIDAsc) {
            Collections.sort(utilisateurs, Utilisateur.TRIIDASC);
            trieIDAsc = false;
        } else {
            Collections.sort(utilisateurs, Utilisateur.TRIIDDESC);
            trieIDAsc = true;
        }
        
        return utilisateurs;
    }
}
