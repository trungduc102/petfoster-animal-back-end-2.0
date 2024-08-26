package com.poly.petfoster.service.impl.feedback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Feedback;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.FeedbackRepository;
import com.poly.petfoster.request.feedback.FeedBackRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.feedback.FeedBackResponse;
import com.poly.petfoster.response.feedback.ManagementFeedbackPagiantionResponse;
import com.poly.petfoster.response.users.UserProfileResponse;
import com.poly.petfoster.service.feedback.FeedbackService;
import com.poly.petfoster.ultils.MailUtils;

@Service
public class FeedbackServiceImpl implements FeedbackService {
        @Autowired
        MailUtils mailUtils;

        @Autowired
        FeedbackRepository feedbackRepository;

        @Override
        public ApiResponse getFeedback(Optional<Integer> page) {

                List<Feedback> feedbacks = feedbackRepository.findAllReverse();

                if (feedbacks.isEmpty()) {
                        return ApiResponse.builder().message("Page not exist")
                                        .status(400)
                                        .errors(false)
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .build();
                }

                Pageable pageable = PageRequest.of(page.orElse(0), 10);
                int startIndex = (int) pageable.getOffset();
                int endIndex = Math.min(startIndex + pageable.getPageSize(), feedbacks.size());

                if (startIndex >= endIndex) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                List<Feedback> visibleFeedbacks = feedbacks.subList(startIndex, endIndex);

                Page<Feedback> pagination = new PageImpl<Feedback>(visibleFeedbacks, pageable,
                                feedbacks.size());

                return ApiResponse.builder()
                                .message("Successfully!")
                                .errors(false)
                                .status(HttpStatus.OK.value())
                                .data(new ManagementFeedbackPagiantionResponse(visibleFeedbacks,
                                                pagination.getTotalPages(), feedbacks.size()))
                                .build();

        }

        public ApiResponse seen(Integer id) {
                Feedback f = feedbackRepository.findById(id).orElse(null);
                if (f != null) {
                        f.setSeen(Boolean.TRUE);
                        feedbackRepository.save(f);
                        return ApiResponse.builder().message("Successfully!").status(HttpStatus.OK.value())
                                        .errors(Boolean.FALSE)
                                        .data(f).build();
                }
                return ApiResponse.builder().message("Failed!").status(HttpStatus.BAD_REQUEST.value())
                                .errors(Boolean.TRUE)
                                .data(new ArrayList<>()).build();

        }

        @Override
        public ApiResponse feedback(HttpServletRequest request, FeedBackRequest feedBackRequest) {
                try {
                        Map<String, String> map = new HashMap<>();
                        map.put("fullname", feedBackRequest.getFullname());
                        map.put("email", feedBackRequest.getEmail());
                        map.put("phone", feedBackRequest.getPhone());
                        map.put("message", feedBackRequest.getMessage());
                        // map.put("browser", request.getHeader("USER-AGENT"));
                        // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        // save to database
                        feedbackRepository.save(new Feedback(null, feedBackRequest.getFullname(),
                                        feedBackRequest.getPhone(),
                                        feedBackRequest.getEmail(), feedBackRequest.getMessage(), Boolean.FALSE));
                        // mailUtils.sendTemplateEmail("duynqpc04918@fpt.edu.vn",
                        // "Visitor feedback - " + formatter.format(new Date()),
                        // "feedback", map);
                        mailUtils.sendTemplateEmail(feedBackRequest.getEmail(), "Thanks your feedback!", "thanks", map);
                } catch (MessagingException e) {
                        return ApiResponse.builder().message("Failed").status(HttpStatus.BAD_REQUEST.value())
                                        .errors(Boolean.TRUE)
                                        .data(null)
                                        .build();
                }
                return ApiResponse.builder().message("Successfully").status(HttpStatus.OK.value()).errors(Boolean.FALSE)
                                .data(new FeedBackResponse(feedBackRequest.getFullname(),
                                                feedBackRequest.getEmail(), feedBackRequest.getMessage()))
                                .build();
        }
}
