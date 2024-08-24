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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.is;

import java.util.List;

import static com.losevskiyfz.reactivestackresearchwork.mock.generator.BookRecordMockGenerator.generateFakeBookRecord;
import static com.losevskiyfz.reactivestackresearchwork.mock.generator.BookRecordMockGenerator.generateFakeBookRecords;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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

    @Test
    void save() throws Exception {
        BookRecord testBook = generateFakeBookRecord();
        String requestJson = objectWriter.writeValueAsString(testBook);
        when(bookRepository.save(any(BookRecord.class))).thenReturn(testBook);
        mockMvc.perform(
                        post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(content().json(requestJson))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", "http://localhost/api/v1/book/" + testBook.id()))
                .andExpect(status().isCreated());
        verify(bookRepository).save(any(BookRecord.class));
    }

    @Test
    void get() throws Exception {
        int numberOfBooks = 41;
        int pageNumber = 1;
        int pageSize = 20;
        List<BookRecord> testBooks = generateFakeBookRecords(numberOfBooks);
        Page<BookRecord> responsePage = new PageImpl<>(testBooks, PageRequest.of(pageNumber, pageSize), numberOfBooks);
        when(bookRepository.getByTextPattern(any(PageRequest.class), any(String.class))).thenReturn(responsePage);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/book")
                )
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.size", is(pageSize)))
                .andExpect(jsonPath("$.page.number", is(pageNumber)))
                .andExpect(jsonPath("$.page.totalElements", is(numberOfBooks)))
                .andExpect(jsonPath("$.page.totalPages", is(numberOfBooks / pageSize + 1)));
        verify(bookRepository).getByTextPattern(any(PageRequest.class), any(String.class));
    }

    @Test
    void delete() throws Exception {
        String idToDelete = "99";
        doNothing().when(bookRepository).deleteById(anyString());
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/book/{id}", idToDelete)
                )
                .andExpect(status().isNoContent());
        verify(bookRepository).deleteById(anyString());
    }

}