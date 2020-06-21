package com.oengal.oengal.likeit;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeItRepository extends JpaRepository<LikeIt, Long> {

  Optional<LikeIt> findByUserIdAndAgendaId(String userId, Long agendaId);

}
