/*

 */
package Boardfinder.APIgateway.Repository;

import Boardfinder.APIgateway.Domain.ActiveToken;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for ActiveToken entity class.
 * @author Erik
 */
@Repository
public interface ActiveTokensRepository  extends JpaRepository<ActiveToken, Long> {

        boolean deleteActiveTokenByToken(String token);
        
        /** Updates the timeStamp for an Active Token to extend it's validity.
      * Returns 0 if the token does not exist and could therefore not be updated. 
     * @param timeNow as LocalDateTime
     * @param token as String
     * @return number of updated tokens as int
     */
    @Transactional 
    @Modifying
    @Query("UPDATE ActiveToken a SET a.timeStampLastUpdated =  :timeNow WHERE a.token = :token")
    int updateTimeStampForTokenIfItExists(@Param("timeNow") LocalDateTime timeNow, @Param("token") String token);
}
