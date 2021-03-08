package com.oengal.oengal.likeit;

import com.oengal.oengal.agenda.AgendaException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@NoArgsConstructor
public class LikeItService {

    @Autowired
    LikeItRepository likeItRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<LikeIt> findByUserIdAndAgendaId(String userId, Long agendaId) {
        if(1==1) {
            throw new RuntimeException();
        }
        return likeItRepository.findByUserIdAndAgendaId(userId, agendaId);
    }

    public LikeIt save(LikeIt likeIt) {
        return this.likeItRepository.save(likeIt);
    }
}
