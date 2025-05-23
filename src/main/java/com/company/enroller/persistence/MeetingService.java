package com.company.enroller.persistence;

import com.company.enroller.exception.ObjectNotFoundException;
import com.company.enroller.model.Meeting;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("meetingService")
@RequiredArgsConstructor
public class MeetingService {

    private final Session dbSession;

    public List<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = dbSession.createQuery(hql);
        return query.list();
    }

    public Meeting getById(Long id) {
        return findById(id).orElseThrow(() -> new ObjectNotFoundException("Meeting with id '%d' not found".formatted(id)));
    }

    private Optional<Meeting> findById(long id) {
        String hql = "From Meeting where id = :id";
        Query query = dbSession.createQuery(hql);
        query.setParameter("id", id);
        return query.getResultStream().findFirst();
    }


    public Meeting createMeeting(Meeting meeting) {
        meeting.setId(null);
        Transaction transaction = dbSession.beginTransaction();
        dbSession.save(meeting);
        transaction.commit();
        return meeting;
    }

    public Meeting update(Long id, Meeting meeting) {
        getById(id);
        meeting.setId(id);
        Transaction transaction = dbSession.beginTransaction();
        dbSession.merge(meeting);
        transaction.commit();
        return meeting;
    }

    public void deleteById(Long id) {
        Meeting meeting = getById(id);
        Transaction transaction = dbSession.beginTransaction();
        dbSession.delete(meeting);
        transaction.commit();
    }
}
