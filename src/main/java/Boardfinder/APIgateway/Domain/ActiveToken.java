package Boardfinder.APIgateway.Domain;

import java.io.Serializable;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class representing the data stored about an issued token in the
 * database. Not used directly in the APIGateway but needed here to be able to
 * access the stored tokens table in the database.
 *
 * @author Erik
 */
@Entity
public class ActiveToken implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String token;
    private LocalDateTime timeStampCreated;
    private LocalDateTime timeStampLastUpdated;

    public ActiveToken() {
    }

    public ActiveToken(String token, LocalDateTime timeStampCreated, LocalDateTime timeStampLastUpdated) {
        this.token = token;
        this.timeStampCreated = timeStampCreated;
        this.timeStampLastUpdated = timeStampLastUpdated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTimeStampCreated() {
        return timeStampCreated;
    }

    public void setTimeStampCreated(LocalDateTime timeStampCreated) {
        this.timeStampCreated = timeStampCreated;
    }

    public LocalDateTime getTimeStampLastUpdated() {
        return timeStampLastUpdated;
    }

    public void setTimeStampLastUpdated(LocalDateTime timeStampLastUpdated) {
        this.timeStampLastUpdated = timeStampLastUpdated;
    }
}
