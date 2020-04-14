package Boardfinder.APIgateway.Service;

import Boardfinder.APIgateway.Repository.TokenRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Erik
 */
@Service
public class ActiveTokenService {
    
    
        private TokenRepository repo;
        
        @Autowired 
        public ActiveTokenService (TokenRepository repo) {
                this.repo = repo;
        }
        
        public boolean checkIfActiveToken(String token) {
             System.out.println("entered checkIfActiveToken") ;     
             System.out.println("token: " + token) ;      
            //boolean tokenExists = false;
            
            Long activeTokenInDB = null;
            
           activeTokenInDB =  repo.getActiveTokenIdByToken(token);
           System.out.println("ACtive token id: " + activeTokenInDB) ;    
            
           if(activeTokenInDB == null){
                return false;
            } 
            
           repo.setTimeStampLastUpdatedByToken(LocalDateTime.now(), activeTokenInDB);
           return true;
        }
}
