package project.uts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.uts.entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
