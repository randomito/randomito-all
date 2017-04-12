package org.randomito.entity;

import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

/**
 * @author Miron Balcerzak, 2017
 */
public class BaseEntity {

    @Id
    private Integer id;

    @Version
    private Date version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }
}