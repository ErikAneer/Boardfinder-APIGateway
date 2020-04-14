
package Boardfinder.APIgateway.Repository;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Erik
 */
@Repository
public class TokenRepository {
    
    @PersistenceContext
   private EntityManager em;
    
    public Long getActiveTokenIdByToken(String token) {
        System.out.println("Entererd getActiveTokenIdByToken");
        Query jpqlQuery = em.createQuery("SELECT a.id FROM ActiveToken a WHERE a.token = :token");
        jpqlQuery.setParameter("token", token);
        return (Long) jpqlQuery.getSingleResult();
    }
    
    @Transactional // gör om så returnera true om det gått gra i try annars false.
     public void setTimeStampLastUpdatedByToken(LocalDateTime timeNow, Long id){
         System.out.println("Entererd setTimeStampLastUpdatedByToken");
         System.out.println("Time: " + timeNow + " and id " + id);
         em.createNativeQuery("UPDATE active_token SET time_stamp_last_updated =  :timeNow WHERE id = :id")
       .setParameter("timeNow", timeNow)
       .setParameter("id", id)
       .executeUpdate(); 
     }
     
}
