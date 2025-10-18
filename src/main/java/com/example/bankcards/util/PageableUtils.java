package com.example.bankcards.util;

import com.example.bankcards.exception.PageableValidationArgumentException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

    private static final int MAX_PAGE_SIZE = 10;

    private PageableUtils() {
    }

    public static Pageable createPageable(int page, int size, String sort) {

        // ---- Валидация ----
        if (page < 0) {
            throw new PageableValidationArgumentException("Page index cannot be negative");
        }

        if (size <= 0) {
            throw new PageableValidationArgumentException("Page size must be greater than zero");
        }

        if (size > MAX_PAGE_SIZE) {
            throw new PageableValidationArgumentException("Page size cannot exceed " + MAX_PAGE_SIZE);
        }

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = (sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }
}
