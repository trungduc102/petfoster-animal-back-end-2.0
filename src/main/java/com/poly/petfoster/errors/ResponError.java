package com.poly.petfoster.errors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.poly.petfoster.response.ApiResponse;

@Component
@Service
public class ResponError {
    
    // @Autowired
    // private ObjectMapper objectMapper;


    public void handleAuthorisationDenied(HttpServletResponse response, String message) {
        ApiResponse errorResponse = new ApiResponse();
        errorResponse.setErrors(true);
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(message);
        errorResponse.setData(null);


        // response.setContentType("application/json");
        // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    
}