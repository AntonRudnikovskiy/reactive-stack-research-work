package com.losevskiyfz.reactivestackresearchwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
import com.losevskiyfz.reactivestackresearchwork.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookRecordControllerIT {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookRepository bookRepository;

    final ObjectMapper objectMapper = new ObjectMapper();
    final ObjectWriter objectWriter = objectMapper.writer();
    BookRecord testBook = new BookRecord(
            "1",
            "book",
            2,
            new String[]{"Alex Hirsh", "Adam Libovski"},
            "Gabella",
            506,
            "Titul",
            2003,
            "Lissabon",
            "104-983",
            "The short description of the book...",
            "2101b"
    );
    String requestJson;

    @Test
    void save() throws Exception {
        requestJson = objectWriter.writeValueAsString(testBook);
        when(bookRepository.save(any(BookRecord.class))).thenReturn(testBook);
        mockMvc.perform(
                        post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(content().json(requestJson))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", "http://localhost/api/v1/book/1"))
                .andExpect(status().isCreated());
        verify(bookRepository).save(any(BookRecord.class));
    }

}