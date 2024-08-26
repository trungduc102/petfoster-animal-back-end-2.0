package com.poly.petfoster.service.impl.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Donate;
import com.poly.petfoster.entity.Feedback;
import com.poly.petfoster.repository.DonateRepository;
import com.poly.petfoster.request.transaction.TransactionRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.feedback.ManagementFeedbackPagiantionResponse;
import com.poly.petfoster.response.transaction.TransactionFilterResponse;
import com.poly.petfoster.response.transaction.TransactionReportResponse;
import com.poly.petfoster.response.transaction.TransactionResponse;
import com.poly.petfoster.service.transaction.TransactionService;
import com.poly.petfoster.ultils.FormatUtils;

@Service
public class TransactionServiceImpl implements TransactionService {

        @Autowired
        private FormatUtils formatUtils;

        @Autowired
        private DonateRepository donateRepository;

        public TransactionResponse buildResponse(TransactionRequest transactionRequest) {
                return TransactionResponse.builder()
                                .id(transactionRequest.getId())
                                .amount(transactionRequest.getAmount())
                                .bank_sub_acc_id(transactionRequest.getBank_sub_acc_id())
                                .corresponsiveAccount(transactionRequest.getCorresponsiveAccount())
                                .corresponsiveBankId(transactionRequest.getCorresponsiveBankId())
                                .corresponsiveBankName(transactionRequest.getCorresponsiveBankName())
                                .corresponsiveName(transactionRequest.getCorresponsiveName())
                                .cusum_balance(transactionRequest.getCusum_balance())
                                .description(transactionRequest.getDescription())
                                .tid(transactionRequest.getTid())
                                .virtualAccount(transactionRequest.getVirtualAccount())
                                .virtualAccountName(transactionRequest.getVirtualAccountName())
                                .build();
        }

        public List<TransactionResponse> buildResponses(List<TransactionRequest> transactionRequests) {

                return transactionRequests.stream().map(transactionRequest -> {

                        return this.buildResponse(transactionRequest);

                }).toList();
        }

        public Donate builDonate(TransactionRequest transactionRequest) {
                return Donate.builder()
                                .descriptions(transactionRequest.getDescription())
                                .donateAmount(transactionRequest.getAmount())
                                .donateAt(transactionRequest.getWhen())
                                .donater(transactionRequest.getCorresponsiveAccount() == null
                                                || transactionRequest.getCorresponsiveAccount().isBlank() ? "Incognito"
                                                                : transactionRequest.getCorresponsiveAccount())
                                .beneficiaryBank(transactionRequest.getBankName())
                                .toAccountNumber(transactionRequest.getSubAccId())
                                .idTransactionl(transactionRequest.getId())
                                .build();
        }

        public List<Donate> buildDonates(List<TransactionRequest> transactionRequests) {

                return transactionRequests.stream().map(item -> {
                        return builDonate(item);
                }).toList();
        }

        @Override
        public ApiResponse setTransactionToDB(List<TransactionRequest> transactionRequest) {

                if (transactionRequest == null) {
                        return ApiResponse.builder()
                                        .message("Failure !")
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .errors(true)
                                        .build();

                }

                List<Donate> donates = this.buildDonates(transactionRequest);

                if (donates == null || donates.isEmpty()) {
                        return ApiResponse.builder()
                                        .message("Failure !")
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .errors(true)
                                        .build();
                }

                donateRepository.saveAll(donates);

                System.out.println(donates.toString());

                return ApiResponse.builder()
                                .message("Successfuly !")
                                .status(HttpStatus.OK.value())
                                .errors(false)
                                .data(this.buildResponses(transactionRequest))
                                .build();

        }

        @Override
        public ApiResponse getTransaction(Optional<Integer> page) {

                List<Donate> donates = donateRepository.findAllReverse();

                if (donates.isEmpty()) {
                        return ApiResponse.builder().message("Something wen't wrong")
                                        .status(400)
                                        .errors(false)
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .build();
                }

                Pageable pageable = PageRequest.of(page.orElse(0), 10);
                int startIndex = (int) pageable.getOffset();
                int endIndex = Math.min(startIndex + pageable.getPageSize(), donates.size());

                if (startIndex >= endIndex) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                List<Donate> visibleDonation = donates.subList(startIndex, endIndex);

                if (visibleDonation.isEmpty()) {
                        return ApiResponse.builder().message("Something wen't wrong")
                                        .status(400)
                                        .errors(false)
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .build();
                }

                Page<Donate> pagination = new PageImpl<Donate>(visibleDonation, pageable,
                                donates.size());

                return ApiResponse.builder()
                                .message("Successfully!")
                                .errors(false)
                                .status(HttpStatus.OK.value())
                                .data(PagiantionResponse.builder().data(visibleDonation)
                                                .pages(pagination.getTotalPages()).build())
                                .build();
        }

        @Override
        public ApiResponse filterTransaction(Optional<String> search, Optional<Date> minDate, Optional<Date> maxDate,
                        Optional<String> sort, Optional<Integer> page) {

                String unWrapSort = sort.orElse("latest");

                List<Donate> donates = donateRepository.filterAdminDonates(search, minDate, maxDate, unWrapSort);

                Double total = donateRepository.totalFilterDonation(search, minDate, maxDate);

                if (donates.isEmpty()) {
                        return ApiResponse.builder().message("Something wen't wrong")
                                        .status(400)
                                        .errors(false)
                                        .data(TransactionFilterResponse.builder().data(new ArrayList<>()).pages(0)
                                                        .total(0.0).build())
                                        .build();
                }

                Pageable pageable = PageRequest.of(page.orElse(0), 10);
                int startIndex = (int) pageable.getOffset();
                int endIndex = Math.min(startIndex + pageable.getPageSize(), donates.size());

                if (startIndex >= endIndex) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(TransactionFilterResponse.builder().data(new ArrayList<>()).pages(0)
                                                        .total(0.0).build())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                List<Donate> visibleDonation = donates.subList(startIndex, endIndex);

                if (visibleDonation.isEmpty()) {
                        return ApiResponse.builder().message("Something wen't wrong")
                                        .status(400)
                                        .errors(false)
                                        .data(TransactionFilterResponse.builder().data(new ArrayList<>()).pages(0)
                                                        .total(0.0).build())
                                        .build();
                }

                Page<Donate> pagination = new PageImpl<Donate>(visibleDonation, pageable,
                                donates.size());

                return ApiResponse.builder()
                                .message("Successfully!")
                                .errors(false)
                                .status(HttpStatus.OK.value())
                                .data(TransactionFilterResponse.builder().data(visibleDonation)
                                                .pages(pagination.getTotalPages())
                                                .total(total).build())
                                .build();
        }

        @Override
        public ApiResponse report() {

                Date date = new Date();

                Double day = donateRepository.reprotDay(formatUtils.dateToDateFormat(date, "yyyy-MM-dd"),
                                formatUtils.dateToDateFormat(date, "yyyy-MM-dd"));

                Double month = donateRepository.reprotMonth(formatUtils.dateToDateFormat(date, "yyyy-MM-dd"));

                Double year = donateRepository.reprotYear(formatUtils.dateToDateFormat(date, "yyyy-MM-dd"));

                return ApiResponse.builder()
                                .message("Successfully!")
                                .errors(false)
                                .status(HttpStatus.OK.value())
                                .data(TransactionReportResponse.builder().day(day).month(month).year(year).build())
                                .build();
        }

}
