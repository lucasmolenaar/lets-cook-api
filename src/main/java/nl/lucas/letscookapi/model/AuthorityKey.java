package nl.lucas.letscookapi.model;

import java.io.Serializable;
import java.util.Objects;

public class AuthorityKey implements Serializable {

    private String username;
    private String authority;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AuthorityKey that = (AuthorityKey) obj;

        return username.equals(that.username) && authority.equals(that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }
}
