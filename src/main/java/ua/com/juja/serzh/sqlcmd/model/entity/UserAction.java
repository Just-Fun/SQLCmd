package ua.com.juja.serzh.sqlcmd.model.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "user_actions", schema = "public")
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "action")
    private String action;

//ALTER TABLE user_actions ADD COLUMN date text;
    @Column(name = "date")
    private String date;

    @JoinColumn(name = "database_connection_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private DatabaseConnection connection;

    public UserAction() {
        // do nothing
    }

     public UserAction(String action, long currentTime, DatabaseConnection connection) {
        this.action = action;
        this.date = formatDate(currentTime);
        this.connection = connection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DatabaseConnection getConnection() {
        return connection;
    }

    public void setConnection(DatabaseConnection connection) {
        this.connection = connection;
    }

    private String formatDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        return dateFormat.format(date);
    }
}
