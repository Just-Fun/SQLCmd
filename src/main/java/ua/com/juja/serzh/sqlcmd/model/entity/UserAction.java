package ua.com.juja.serzh.sqlcmd.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_actions", schema = "public")
public class UserAction {

    @Column(name = "action")
    private String action;

    @JoinColumn(name = "database_connection_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DatabaseConnection connection;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public UserAction() {
        // do nothing
    }

    public UserAction(String action, DatabaseConnection connection) {
        this.action = action;
        this.connection = connection;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DatabaseConnection getConnection() {
        return connection;
    }

    public void setConnection(DatabaseConnection connection) {
        this.connection = connection;
    }
}
