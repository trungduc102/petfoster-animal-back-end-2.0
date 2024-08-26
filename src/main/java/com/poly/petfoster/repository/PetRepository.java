package com.poly.petfoster.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, String> {

        @Query(nativeQuery = true, value = "select * from pet")
        List<Pet> findAllPet();

        // @Query(nativeQuery = true, value = "select top 8 * from pet where (breed_id =
        // :id or age = :size) and pet_id != :petId")
        // List<Pet> findByPetStyleAndIgnorePetId(@Param("id") String id, @Param("size")
        // String size,
        // @Param("petId") String petId);

        // @Query("SELECT p " +
        // "FROM Pet p " +
        // "INNER JOIN p.petBreed pb " +
        // "INNER JOIN pb.petType pt " +
        // "WHERE (:typeName IS NULL OR pt.name LIKE :typeName) " +
        // // "AND p.adoptStatus not like 'Adopted' " +
        // "AND (:name IS NULL OR p.petName like %:name%) " +
        // "AND (:colors IS NULL OR (',' + :colors + ',' LIKE CONCAT('%,', p.petColor,
        // ',%'))) " +
        // "AND (:age IS NULL OR p.age like %:age%) " +
        // "AND (:gender IS NULL OR p.sex = :gender) " +
        // "ORDER BY " +
        // "CASE WHEN :sort = 'oldest' THEN p.fosterAt END ASC, " +
        // "CASE WHEN :sort = 'latest' THEN p.fosterAt END DESC")
        // List<Pet> filterPets(@Param("name") String name, @Param("typeName") String
        // typeName, @Param("colors") String colors,
        // @Param("age") String age, @Param("gender") Boolean gender, @Param("sort")
        // String sort);

        @Query(nativeQuery = true, value = "select top 8 * from pet")
        List<Pet> findAllByActive();

        @Query(nativeQuery = true, value = "select top 8 * from pet where (breed_id = :id or age = :size) and pet_id != :petId")
        List<Pet> findByPetStyleAndIgnorePetId(@Param("id") String id, @Param("size") String size,
                        @Param("petId") String petId);

        @Query("SELECT p " +
                        "FROM Pet p " +
                        "INNER JOIN p.petBreed pb " +
                        "INNER JOIN pb.petType pt " +
                        "WHERE (:typeName IS NULL OR pt.name LIKE :typeName) " +
                        // "AND p.adoptStatus not like 'Adopted' " +
                        "AND (:name IS NULL OR p.petName like %:name%) " +
                        "AND (:colors IS NULL OR (',' + :colors + ',' LIKE CONCAT('%,', p.petColor, ',%'))) " +
                        "AND (:age IS NULL OR p.age like %:age%) " +
                        "AND (:gender IS NULL OR p.sex = :gender) " +
                        "ORDER BY " +
                        "CASE WHEN :sort = 'oldest' THEN p.fosterAt END ASC, " +
                        "CASE WHEN :sort = 'latest' THEN p.fosterAt END DESC")
        List<Pet> filterPets(@Param("name") String name, @Param("typeName") String typeName,
                        @Param("colors") String colors,
                        @Param("age") String age, @Param("gender") Boolean gender, @Param("sort") String sort);

        @Query("SELECT p " +
                        "FROM Pet p " +
                        "INNER JOIN p.petBreed pb " +
                        "INNER JOIN pb.petType pt " +
                        "WHERE (:typeName IS NULL OR pt.name LIKE :typeName) " +
                        "AND (:name IS NULL OR p.petName like %:name%) " +
                        "AND (:colors IS NULL OR (',' + :colors + ',' LIKE CONCAT('%,', p.petColor, ',%'))) " +
                        "AND (:age IS NULL OR p.age like %:age%) " +
                        "AND (:gender IS NULL OR p.sex = :gender) " +
                        "AND (:status IS NULL OR p.adoptStatus like :status) " +
                        "AND ((:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, p.fosterAt) BETWEEN :minDate AND :maxDate)) "
                        +
                        "ORDER BY " +
                        "CASE WHEN :sort = 'oldest' THEN p.fosterAt END ASC, " +
                        "CASE WHEN :sort = 'latest' THEN p.fosterAt END DESC")
        List<Pet> filterAdminPets(@Param("name") String name, @Param("typeName") String typeName,
                        @Param("colors") String colors,
                        @Param("age") String age, @Param("gender") Boolean gender, @Param("status") String status,
                        @Param("minDate") Date minDate, @Param("maxDate") Date maxDate, @Param("sort") String sort);

        @Query(nativeQuery = true, value = "select * from pet p join favorite f on p.pet_id = f.pet_id " + //
                        "where f.[user_id] = :user_id order by id desc")
        List<Pet> getFavorites(@Param("user_id") String user_id);
}
