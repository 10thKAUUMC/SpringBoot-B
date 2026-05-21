package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;

import java.util.List;

public class ReviewConverter {
    public static ReviewResDTO.MyReviewDTO toMyReviewDTO(Review review) {
        return new ReviewResDTO.MyReviewDTO(
                review.getReviewId(),
                review.getStore().getName(),
                review.getContent(),
                review.getRating()
        );
    }

    public static ReviewResDTO.MyReviewListDTO toMyReviewListDTO(
            List<Review> reviews,
            Integer size,
            boolean isRatingSort
    ) {
        boolean hasNext = reviews.size() > size;

        List<Review> content = hasNext
                ? reviews.subList(0, size)
                : reviews;

        List<ReviewResDTO.MyReviewDTO> reviewDTOList = content.stream()
                .map(ReviewConverter::toMyReviewDTO)
                .toList();

        Long nextCursorId = null;
        Float nextCursorRating = null;

        if(hasNext && !content.isEmpty()) {
            Review lastReview = content.get(content.size() - 1);
            nextCursorId = lastReview.getReviewId();

            if(isRatingSort) {
                nextCursorRating = lastReview.getRating();
            }
        }

        return new ReviewResDTO.MyReviewListDTO(
                reviewDTOList,
                reviewDTOList.size(),
                hasNext,
                nextCursorId,
                nextCursorRating
        );
    }
}
