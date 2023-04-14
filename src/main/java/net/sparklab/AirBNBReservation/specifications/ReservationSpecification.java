package net.sparklab.AirBNBReservation.specifications;

import net.sparklab.AirBNBReservation.dto.FilterDTO;
import net.sparklab.AirBNBReservation.model.Reservation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationSpecification implements Specification<Reservation> {

    private FilterDTO filterDTO;
    private Reservation filter;

    public ReservationSpecification(FilterDTO filterDTO, Reservation filter) {
        super();
        this.filterDTO = filterDTO;
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        //Starting Date and Ending Date Parse
        if (filterDTO.getStart()!=null && filterDTO.getEnd()!=null) {

            //Starting Date and Ending Date Parse
            LocalDate startDateParsed = LocalDate.parse(filterDTO.getStart(), DateTimeFormatter.ofPattern("d/M/uuuu"));
            LocalDate endDateParsed = LocalDate.parse(filterDTO.getEnd(), DateTimeFormatter.ofPattern("d/M/uuuu"));

            if (filterDTO.getBookedDate()==true) {
                predicates.add(cb.between(root.get("bookedDate"), startDateParsed, endDateParsed));
            }
            if (filterDTO.getCreatedDate()==true) {
                predicates.add(cb.between(root.get("createdDate"), startDateParsed, endDateParsed));
            }
            if (filterDTO.getStartDate()==true) {
                predicates.add(cb.between(root.get("startDate"), startDateParsed, endDateParsed));
            }
            if (filterDTO.getEndDate()==true) {
                predicates.add(cb.between(root.get("endDate"), startDateParsed, endDateParsed));
            }
        }

        if (filterDTO.getEarningMin()!=null && filterDTO.getEarningMax()!=null){
            //Earning values from String to decimal
            BigDecimal earningMin = new BigDecimal(filterDTO.getEarningMin());
            BigDecimal earningMax = new BigDecimal(filterDTO.getEarningMax());

            predicates.add(cb.between(root.get("earning"), earningMin, earningMax));
        }

        if (filter.getGuest()!=null) {
            if (filter.getGuest().getFirstName() != null) {
                predicates.add(cb.like(root.join("guest").get("firstName"), "%" + filter.getGuest().getFirstName() + "%"));
            }
            if (filter.getGuest().getLastName() != null) {
                predicates.add(cb.like(root.join("guest").get("lastName"), "%" + filter.getGuest().getLastName() + "%"));
            }
            if (filter.getGuest().getContact() != null && filterDTO.getContact()!=null) {
                predicates.add(cb.like(root.join("guest").get("contact"), "%" + filter.getGuest().getContact() + "%"));
            }
            if (filter.getGuest().getStatus() != null && filterDTO.getStatus()!=null) {
                predicates.add(cb.like(root.join("guest").get("status"), "%" + filter.getGuest().getStatus() + "%"));
            }
        }
        if (filter.getConfirmationCode()!=null){
            predicates.add(cb.like(root.get("confirmationCode"), "%" + filter.getConfirmationCode() + "%"));
        }
        if (filterDTO.getListing()!=null){
            predicates.add(cb.like(root.join("listing").get("listing"), "%" + filterDTO.getListing() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
