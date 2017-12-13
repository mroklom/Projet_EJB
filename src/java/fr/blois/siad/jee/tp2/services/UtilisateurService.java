package fr.blois.siad.jee.tp2.services;

import fr.blois.siad.jee.tp2.dto.Utilisateur;
import java.util.List;

public interface UtilisateurService {

    List<Utilisateur> listerTous();

    List<Utilisateur> listerUtilisateurTrieID();
    
    List<Utilisateur> listerUtilisateurTrieNom();
    
    List<Utilisateur> listerUtilisateurTrieEmail();
    
    Utilisateur lire(Integer id);
    
    void ajouter(Utilisateur u);
    
    void supprimer(Integer id);
    
    void bloquer(Integer id);
    
    void debloquer(Integer id);
    
}
