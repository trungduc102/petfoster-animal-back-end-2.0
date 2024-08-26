package com.poly.petfoster.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Donate;
import com.poly.petfoster.entity.Pet;

public interface DonateRepository extends JpaRepository<Donate, Integer> {

        @Query("select sum(d.donateAmount) from Donate d")
        public Integer getDonation();

        @Query(value = "select d from Donate d ORDER BY d.donateAt desc")
        public List<Donate> findAllReverse();

        @Query(value = "select d from Donate d " +
                        "where (:search IS NULL OR d.donater LIKE %:search% OR d.beneficiaryBank LIKE %:search% OR d.toAccountNumber LIKE %:search%)"
                        +
                        "AND ((:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, d.donateAt) BETWEEN :minDate AND :maxDate)) "
                        +
                        "ORDER BY " +
                        "CASE WHEN :sort = 'oldest' THEN d.donateAt END ASC, " +
                        "CASE WHEN :sort = 'latest' THEN d.donateAt END DESC ")

        List<Donate> filterAdminDonates(@Param("search") Optional<String> search,
                        @Param("minDate") Optional<Date> minDate,
                        @Param("maxDate") Optional<Date> maxDate, @Param("sort") String sort);

        @Query("select sum(d.donateAmount) from Donate d " +
                        "where (:search IS NULL OR d.donater LIKE %:search% OR d.beneficiaryBank LIKE %:search% OR d.toAccountNumber LIKE %:search%)"
                        +
                        "AND ((:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, d.donateAt) BETWEEN :minDate AND :maxDate)) ")
        public Double totalFilterDonation(@Param("search") Optional<String> search,
                        @Param("minDate") Optional<Date> minDate,
                        @Param("maxDate") Optional<Date> maxDate);

        @Query("select sum(d.donateAmount) from Donate d " +
                        "where (:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, d.donateAt) BETWEEN :minDate AND :maxDate)")
        public Double reprotDay(
                        @Param("minDate") Date minDate,
                        @Param("maxDate") Date maxDate);

        @Query(value = "select SUM(donate_amount) from donate where MONTH(donate_at) = MONTH(:date)", nativeQuery = true)
        public Double reprotMonth(
                        @Param("date") Date date);

        @Query(value = "select SUM(donate_amount) from donate where YEAR(donate_at) = YEAR(:date)", nativeQuery = true)
        public Double reprotYear(
                        @Param("date") Date date);
}
