
package Boardfinder.APIgateway.Repository;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Custom repository class for ActiveToken entity class 
 * to handle repository operations done in the ActiveTokeb repository interface. 
 * @author Erik
 */
@Repository
public class CustomTokenRepository {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomTokenRepository.class);
    
    @PersistenceContext
   private EntityManager em;
    
    /**
     * Gets the id of an active token by the token as String. 
     * Returns the id of the acrtive token or throws an AccessDeniedException if the token does not exist as it mean the token has been revoked. 
     * @param token
     * @return
     * @throws AccessDeniedException 
     */
    public Long getActiveTokenIdByToken(String token) throws AccessDeniedException {
        System.out.println("Entererd getActiveTokenIdByToken");
        try {
        Query jpqlQuery = em.createQuery("SELECT a.id FROM ActiveToken a WHERE a.token = :token");
        jpqlQuery.setParameter("token", token);
        return (Long) jpqlQuery.getSingleResult();
        }
          catch (Exception e) {
             throw new AccessDeniedException("Valid token does not exist");
        }
    }
    
     /**
     * Updates the timeStamp for an Active Token to extend it's validity.
     * @param timeNow as LocalDateTime
     * @param id as Long
     * @return boolean
     */
    @Transactional
    public boolean setTimeStampLastUpdatedByToken(LocalDateTime timeNow, Long id) {
        LOGGER.info("CustomTokenRepository.setTimeStampLastUpdatedByToken called");
        try {
            em.createNativeQuery("UPDATE active_token SET time_stamp_last_updated =  :timeNow WHERE id = :id")
                    .setParameter("timeNow", timeNow)
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
     
}
