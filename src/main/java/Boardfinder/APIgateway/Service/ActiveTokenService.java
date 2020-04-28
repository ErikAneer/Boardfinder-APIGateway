package Boardfinder.APIgateway.Service;

import Boardfinder.APIgateway.Repository.ActiveTokensRepository;

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

    private final ActiveTokensRepository repo;

    @Autowired
    public ActiveTokenService(ActiveTokensRepository repo) {
        this.repo = repo;
    }

    /**
     * Checks if an ActiveToken exists in the database in order to be able to
     * grant or deny access.
     * Updates timesStampLastUpdated if exists.
     * @param token as String
     * @return boolean
     */
    public boolean checkIfActiveToken(String token) {
        LOGGER.info("ActiveTokenService.checkIfActiveToken called.");

        return (repo.updateTimeStampForTokenIfItExists(LocalDateTime.now(), token) == 1);
    }
}
