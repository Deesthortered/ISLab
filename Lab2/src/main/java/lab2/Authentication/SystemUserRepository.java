package lab2.Authentication;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepository extends JpaRepository<SystemUser, String> {
    //SystemUser findByUserName(String user_name);
}