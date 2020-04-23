package Boardfinder.APIgateway.Service;

import Boardfinder.APIgateway.Repository.CustomTokenRepository;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class to handle tokens in the API Gateway application
 *
 * @author Erik
 */
@Service
public class ActiveTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveTokenService.class);

    private final CustomTokenRepository repo;

    @Autowired
    public ActiveTokenService(CustomTokenRepository repo) {
        this.repo = repo;
    }

    /**
     * Checks if an ActiveToken exists in the database in order to be able to
     * grant or deny access.
     *
     * @param token as String
     * @return boolean
     * @throws AccessDeniedException when an ActiveToken is missing.
     */
    public boolean checkIfActiveToken(String token) throws AccessDeniedException {
        LOGGER.info("ActiveTokenService.checkIfActiveToken called.");
        Long activeTokenInDB = null;

        activeTokenInDB = repo.getActiveTokenIdByToken(token);

        if (activeTokenInDB == null) {
            LOGGER.info("ActiveTokenService.checkIfActiveToken could not find token.");
            return false;
        }

        repo.setTimeStampLastUpdatedByToken(LocalDateTime.now(), activeTokenInDB);
        LOGGER.info("ActiveTokenService.checkIfActiveToken could validate token.");
        return true;
    }

}
