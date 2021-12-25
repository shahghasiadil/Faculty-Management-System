package com.faculty.model;
import com.faculty.support.DBUtil;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class Users extends ModelBase<Users> {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id")
//    private  Address address;

    public static class UserBuilder extends ModelBuilderBase<Users> {
        private String firstName;
        private String lastName;
        private String phone;
        private String email;
        private String username;
        private String password;

        public UserBuilder() {
            // ...
        }

        public Users build() {
            if (! isValid()) {
                return null;
            }

            Users user = new Users();

            user.setFirstName(firstName);
            user.setLastName(lastName.isEmpty() ? null : lastName);
            user.setPhone(phone.isEmpty() ? null : phone);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);

            return user;
        }

        public boolean isValid() {
            boolean valid = true;

            if (firstName.length() < 3) {
                valid = false;
            }

            if (! lastName.isEmpty() && lastName.length() < 3) {
                valid = false;
            }

            if (! phone.isEmpty() && ! phone.matches("^07\\d{8}")) {
                valid = false;
            }

            try {
                InternetAddress emailAddress = new InternetAddress(email);
                emailAddress.validate();
            } catch (AddressException ex) {
                valid = false;
            }

            if (! username.matches("^\\w{4,20}$")) {
                valid = false;
            }

            if (password.length() < 5) {
                valid = false;
            }

            return valid;
        }

        public UserBuilder withFirstName(String firstName) {
            this.firstName = firstName.trim();

            return this;
        }

        public UserBuilder withLastName(String lastName) {
            this.lastName = lastName.trim();

            return this;
        }

        public UserBuilder withPhone(String phone) {
            this.phone = phone.trim();

            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email.trim();

            return this;
        }

        public UserBuilder withUsername(String username) {
            this.username = username.trim();

            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password.trim();

            return this;
        }
    }

    public Users() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static Users getByUsername(String username) {
        final String hql = "FROM Users where " +
                "username = :username OR " +
                "phone = :username OR " +
                "email = :username";

        return DBUtil.execute(session -> {
            Query<Users> usersQuery = session.createQuery(hql, Users.class);
            usersQuery.setParameter("username", username);
            List<Users> result = usersQuery.list();

            return result.size() > 0 ? result.get(0) : null;
        });
    }

    @Override
    public String toString() {
        return "User{" +

                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
