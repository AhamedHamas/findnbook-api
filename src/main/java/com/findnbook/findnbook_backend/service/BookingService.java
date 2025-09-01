package com.findnbook.findnbook_backend.service;

import com.findnbook.findnbook_backend.dto.AvailableSlotsResponse;
import com.findnbook.findnbook_backend.dto.BookingRequest;
import com.findnbook.findnbook_backend.dto.BookingResponse;
import com.findnbook.findnbook_backend.dto.BusinessDashboardResponse;
import com.findnbook.findnbook_backend.enums.BookingStatus;
import com.findnbook.findnbook_backend.model.Booking;
import com.findnbook.findnbook_backend.model.Business;
import com.findnbook.findnbook_backend.model.BusinessService;
import com.findnbook.findnbook_backend.model.User;
import com.findnbook.findnbook_backend.repository.BookingRepository;
import com.findnbook.findnbook_backend.repository.BusinessRepository;
import com.findnbook.findnbook_backend.repository.BusinessServiceRepository;
import com.findnbook.findnbook_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final BusinessServiceRepository businessServiceRepository;

    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            BusinessRepository businessRepository,
            BusinessServiceRepository businessServiceRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
        this.businessServiceRepository = businessServiceRepository;
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())
                .status(booking.getStatus().name())
                .customerId(booking.getCustomer().getId())
                .customerName(booking.getCustomer().getName())
                .customerEmail(booking.getCustomer().getEmail())
                .customerPhone(booking.getCustomer().getPhoneNumber())
                .businessId(booking.getBusiness().getId())
                .businessName(booking.getBusiness().getName())
                .businessAddress(booking.getBusiness().getAddress())
                .serviceId(booking.getService().getId())
                .serviceName(booking.getService().getName())
                .servicePrice(booking.getService().getPrice())
                .build();
    }


    public BookingResponse createBooking(BookingRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        BusinessService service = businessServiceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Booking booking = Booking.builder()
                .customer(customer)
                .business(business)
                .service(service)
                .bookingDate(request.getBookingDate())
                .bookingTime(request.getBookingTime())
                .status(BookingStatus.BOOKED)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }


    public List<BookingResponse> getOngoingBookings(String customerId) {
        return bookingRepository.findByCustomerIdAndStatus(customerId, BookingStatus.BOOKED)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getPreviousBookings(String customerId) {
        return bookingRepository.findByCustomerIdAndStatusIn(
                        customerId,
                        List.of(BookingStatus.COMPLETED, BookingStatus.CANCELLED)
                )
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != BookingStatus.BOOKED) {
            throw new RuntimeException("Only ongoing bookings can be cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Booking updatedBooking = bookingRepository.save(booking);

        return mapToResponse(updatedBooking);
    }


    public BusinessDashboardResponse getDashboardData(String businessId) {
        long completedCount = bookingRepository.countByBusinessIdAndStatus(businessId, BookingStatus.COMPLETED);
        long upcomingCount = bookingRepository.countByBusinessIdAndStatus(businessId, BookingStatus.BOOKED);


        double totalRevenue = bookingRepository.findByBusinessIdAndStatus(businessId, BookingStatus.COMPLETED)
                .stream()
                .mapToDouble(b -> b.getService().getPrice())
                .sum();

        return new BusinessDashboardResponse(completedCount, upcomingCount, totalRevenue);
    }

    public List<BookingResponse> getTodaysBookings(String businessId) {
        LocalDate today = LocalDate.now();
        List<Booking> bookings = bookingRepository.findByBusinessIdAndBookingDate(businessId, today);

        return bookings.stream()
                .map(this::mapToResponse)
                .toList();
    }


    public List<BookingResponse> getBookedBookingsByBusiness(String businessId) {
        List<Booking> bookings = bookingRepository.findByBusinessIdAndStatus(businessId, BookingStatus.BOOKED);

        return bookings.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AvailableSlotsResponse getAvailableSlots(String businessId, String serviceId, LocalDate bookingDate) {

        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Business not found"));


        int serviceDuration = 30;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime openingTime = LocalTime.parse(business.getOpeningTime(), formatter);
        LocalTime closingTime = LocalTime.parse(business.getClosingTime(), formatter);


        List<Booking> existingBookings = bookingRepository.findByBusinessIdAndBookingDate(businessId, bookingDate)
                .stream()
                .filter(b -> b.getStatus() == BookingStatus.BOOKED && b.getService().getId().equals(serviceId))
                .toList();

        List<LocalTime> availableSlots = new ArrayList<>();
        LocalTime slotTime = openingTime;

        while (!slotTime.plusMinutes(serviceDuration).isAfter(closingTime)) {

            LocalTime currentSlot = slotTime;

            boolean isAvailable = existingBookings.stream().noneMatch(b -> {
                LocalTime bookedStart = b.getBookingTime();
                LocalTime bookedEnd = bookedStart.plusMinutes(serviceDuration);
                LocalTime slotEnd = currentSlot.plusMinutes(serviceDuration);

                return currentSlot.isBefore(bookedEnd) && slotEnd.isAfter(bookedStart);
            });

            if (isAvailable) {
                availableSlots.add(currentSlot);
            }

            slotTime = slotTime.plusMinutes(serviceDuration);
        }

        return new AvailableSlotsResponse(availableSlots);
    }
}