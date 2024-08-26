package com.poly.petfoster.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Adopt;
import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.User;

public interface AdoptRepository extends JpaRepository<Adopt, Integer> {

        @Query("select a from Adopt a where a.status = 'Adopted'")
        public List<Adopt> getAdoptedPets();

        @Query(nativeQuery = true, value = "select * from adopt where pet_id = :petId")
        Adopt existsByPet(@Param("petId") String petId);

        @Query("select a from Adopt a where a.status = 'Adopted' and a.pet = :pet")
        Adopt findByPet(@Param("pet") Pet pet);

        @Query(nativeQuery = true, value = "select * from adopt where user_id = :userId and adopt_id = :adoptId")
        Adopt existsByUser(@Param("userId") String userId, @Param("adoptId") Integer adoptId);

        @Query(value = "select a from Adopt a where a.user = :user and a.status like %:status% order by a.registerAt desc")
        List<Adopt> findByUser(@Param("user") User user, @Param("status") String status);

        @Query(value = "select a from Adopt a where a.user = :user order by a.registerAt desc")
        List<Adopt> findByUser(@Param("user") User user);

        @Query(value = "select * from adopt " +
                        "where pet_id = :petId and [status] = 'Waiting' and user_id != :userId", nativeQuery = true)
        List<Adopt> findByUserIgnoreUserId(@Param("userId") String userId, @Param("petId") String petId);

        // The pet status was waiting by the user, the same user cannot registered adopt
        // the pet again
        @Query(nativeQuery = true, value = "select * from adopt where pet_id = :petId and user_id = :userId and status like 'Waiting'")
        Adopt existsByPetAndUser(@Param("petId") String petId, @Param("userId") String userId);

        // The pet was adopted or registered cannot register adopt
        @Query(nativeQuery = true, value = "select * from adopt where pet_id = :petId and status in ('Adopted', 'Registered')")
        Adopt exsitsAdopted(@Param("petId") String petId);

        @Query("SELECT a FROM Adopt a " +
                        "INNER JOIN a.user u " +
                        "INNER JOIN a.pet p " +
                        "WHERE (:name IS NULL OR u.displayName like %:name%) " +
                        "AND (:petName IS NULL OR p.petName like %:petName%) " +
                        "AND ((:registerStart IS NULL AND :registerEnd IS NULL) OR (convert(date, a.registerAt) BETWEEN :registerStart AND :registerEnd)) "
                        +
                        "AND ((:adoptStart IS NULL AND :adoptEnd IS NULL) OR (convert(date, a.adoptAt) BETWEEN :adoptStart AND :adoptEnd)) "
                        +
                        "AND (:status IS NULL OR a.status LIKE :status) " +
                        "ORDER BY " +
                        "CASE WHEN :sort = 'id-asc' THEN a.adoptId END ASC, " +
                        "CASE WHEN :sort = 'id-desc' THEN a.adoptId END DESC, " +
                        "CASE WHEN :sort = 'pet-asc' THEN p.petId END ASC, " +
                        "CASE WHEN :sort = 'pet-desc' THEN p.petId END DESC, " +
                        "CASE WHEN :sort = 'adopt-asc' THEN a.adoptAt END ASC, " +
                        "CASE WHEN :sort = 'adopt-desc' THEN a.adoptAt END DESC ")
        public List<Adopt> filterAdopts(
                        @Param("name") String name,
                        @Param("petName") String petName,
                        @Param("status") String status,
                        @Param("registerStart") Date registerStart,
                        @Param("registerEnd") Date registerEnd,
                        @Param("adoptStart") Date adoptStart,
                        @Param("adoptEnd") Date adoptEnd,
                        @Param("sort") String sort);

        // a.registerAt is not null and a.pickUpAt is null and a.adoptAt is null

        // days
        @Query("select COUNT(a.id) from Adopt a where " +
                        "a.registerAt is not null and a.pickUpAt is null and a.adoptAt is null and a.cancelReason is null "
                        +
                        "and (:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, a.registerAt) BETWEEN :minDate AND :maxDate)")
        public Double reprotDayRegistrations(
                        @Param("minDate") Date minDate,
                        @Param("maxDate") Date maxDate);

        // months
        @Query(value = "select COUNT(*) from adopt where " +
                        "register_at is not null and pick_up_at is null and adopt_at is null and cancel_reason is null "
                        +
                        "and MONTH(register_at) = MONTH(:date)", nativeQuery = true)
        public Double reprotMonthRegistrations(
                        @Param("date") Date date);

        // years
        @Query(value = "select COUNT(*) from adopt where " +
                        "register_at is not null and pick_up_at is null and adopt_at is null and cancel_reason is null "
                        +
                        "and YEAR(register_at) = YEAR(:date)", nativeQuery = true)
        public Double reprotYearRegistrations(
                        @Param("date") Date date);

        // days
        @Query("select COUNT(a.id) from Adopt a where " +
                        "a.registerAt is not null and a.pickUpAt is not null and a.adoptAt is null and a.cancelReason is null "
                        +
                        "and (:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, a.registerAt) BETWEEN :minDate AND :maxDate)")
        public Double reprotDayWating(
                        @Param("minDate") Date minDate,
                        @Param("maxDate") Date maxDate);

        // months
        @Query(value = "select COUNT(*) from adopt where " +
                        "register_at is not null and pick_up_at is not null and adopt_at is null and cancel_reason is null "
                        +
                        "and MONTH(register_at) = MONTH(:date)", nativeQuery = true)
        public Double reprotMonthWating(
                        @Param("date") Date date);

        // years
        @Query(value = "select COUNT(*) from adopt where " +
                        "register_at is not null and pick_up_at is not null and adopt_at is null and cancel_reason is null "
                        +
                        "and YEAR(register_at) = YEAR(:date)", nativeQuery = true)
        public Double reprotYearWating(
                        @Param("date") Date date);

        // days
        @Query("select COUNT(a.id) from Adopt a where " +
                        "a.registerAt is not null and a.pickUpAt is not null and a.adoptAt is not null and a.cancelReason is null "
                        +
                        "and (:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, a.registerAt) BETWEEN :minDate AND :maxDate)")
        public Double reprotDayAdopted(
                        @Param("minDate") Date minDate,
                        @Param("maxDate") Date maxDate);

        // months
        @Query(value = "select COUNT(*) from adopt where " +
                        "register_at is not null and pick_up_at is not null and adopt_at is not null and cancel_reason is null "
                        +
                        "and MONTH(register_at) = MONTH(:date)", nativeQuery = true)
        public Double reprotMonthAdopted(
                        @Param("date") Date date);

        // years
        @Query(value = "select COUNT(*) from adopt where " +
                        "register_at is not null and pick_up_at is not null and adopt_at is not null and cancel_reason is null "
                        +
                        "and YEAR(register_at) = YEAR(:date)", nativeQuery = true)
        public Double reprotYearAdopted(
                        @Param("date") Date date);

        // days
        @Query("select COUNT(a.id) from Adopt a where " +
                        " a.cancelReason is not null "
                        +
                        "and (:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, a.registerAt) BETWEEN :minDate AND :maxDate)")
        public Double reprotDayDeleted(
                        @Param("minDate") Date minDate,
                        @Param("maxDate") Date maxDate);

        // months
        @Query(value = "select COUNT(*) from adopt where " +
                        "cancel_reason is not null "
                        +
                        "and MONTH(register_at) = MONTH(:date)", nativeQuery = true)
        public Double reprotMonthDeleted(
                        @Param("date") Date date);

        // years
        @Query(value = "select COUNT(*) from adopt where " +
                        "cancel_reason is not null "
                        +
                        "and YEAR(register_at) = YEAR(:date)", nativeQuery = true)
        public Double reprotYearDeleted(
                        @Param("date") Date date);

        @Query(value = "select COUNT(*) from adopt where user_id = :userId and status = :status ", nativeQuery = true)
        public Integer countByStatusAndUserID(
                        @Param("userId") String date, @Param("status") String status);

        @Query(value = "select COUNT(*) from adopt where user_id = :userId", nativeQuery = true)
        public Integer countByStatusAndUserID(
                        @Param("userId") String date);

}