package skillbox.javapro11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import skillbox.javapro11.model.entity.Dialog;

import java.util.Date;

/**
 * Created by timurg on 28.04.2021.
 */
@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    Dialog findById(long id);

//    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO dialog (owner_id, is_deleted) VALUES (:owner_id, true) RETURNING id",
            nativeQuery = true)
    long insertDialog(@Param("owner_id") long owner_id);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE dialog g SET g.is_deleted = false WHERE g.id = :id",
            nativeQuery = true)
    void deleteDialog(@Param("id") long id);

}
