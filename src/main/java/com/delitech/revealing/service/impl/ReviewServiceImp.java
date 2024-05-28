package com.delitech.revealing.service.impl;

import com.delitech.revealing.dto.ClientDto;
import com.delitech.revealing.dto.DishesDto;
import com.delitech.revealing.dto.RestaurantDto;
import com.delitech.revealing.dto.ReviewDto;
import com.delitech.revealing.entity.ClientEntity;
import com.delitech.revealing.entity.DishesEntity;
import com.delitech.revealing.entity.RestaurantEntity;
import com.delitech.revealing.entity.ReviewEntity;
import com.delitech.revealing.exception.ModelNotFoundException;
import com.delitech.revealing.repository.ClientRepository;
import com.delitech.revealing.repository.RestaurantRepository;
import com.delitech.revealing.repository.ReviewRepository;
import com.delitech.revealing.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

import static com.delitech.revealing.commons.Constants.EXCEPTION_MODEL_NOTFOUND;
import static com.delitech.revealing.commons.Constants.EXCEPTION_NOT_MATCH;
import static com.delitech.revealing.commons.Constants.TIME_ZONE;

@Service
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService {

    private final MessageSource messageSource;
    private final ReviewRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final ClientRepository clientRepository;
    private final Function<ReviewEntity, ReviewDto> reviewToDto;
    private final Function<ReviewDto, ReviewEntity> reviewDtoToEntity;
    private final Function<RestaurantEntity, RestaurantDto> restaurantToDto;
    private final Function<ClientEntity, ClientDto> clientToDto;
    private final Function<DishesDto, DishesEntity> dishesDtoToEntity;

    private ReviewDto convert(ReviewEntity entity) {
        return reviewToDto.apply(entity);
    }

    @Override
    @Transactional
    public ReviewDto save(ReviewDto dto, UUID clientId, UUID restaurantId, Locale locale) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        ReviewEntity entity = reviewDtoToEntity.apply(dto);

        entity.setRestaurant(restaurant);
        entity.setClient(client);
        entity.setDateTime(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        return convert(repository.save(entity));
    }

    @Override
    @Transactional
    public ReviewDto update(ReviewDto dto, UUID id, UUID clientId, Locale locale) throws IllegalAccessException {
        ReviewEntity entity = repository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        if (!entity.getClient().getUserId().equals(client.getUserId()))
            throw new IllegalAccessException(messageSource.getMessage(EXCEPTION_NOT_MATCH, null, locale));

        BeanUtils.copyProperties(dto, entity, "id", "restaurant", "client", "dateTime");
        entity.setDateTime(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        return convert(repository.save(entity));
    }

    @Override
    public ReviewDto getById(UUID id, Locale locale) {
        ReviewEntity entity = repository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        RestaurantEntity restaurant = restaurantRepository.findById(entity.getRestaurant().getUserId())
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        ClientEntity client = clientRepository.findById(entity.getClient().getUserId())
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        ReviewDto reviewDto = convert(entity);

        reviewDto.setRestaurant(restaurantToDto.apply(restaurant));
        reviewDto.setClient(clientToDto.apply(client));
        return reviewDto;
    }

    @Override
    public Page<ReviewDto> getAllMyReviews(UUID clientId, Pageable pageable, Locale locale) {
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.findAll(byClient(client), pageable).map(this::convert);
    }

    @Override
    public Page<ReviewDto> getAllByRestaurant(UUID restaurantId, Pageable pageable, Locale locale) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.findAll(byRestaurant(restaurant), pageable).map(this::convert);
    }

    @Override
    public Page<ReviewDto> getAllMyReviewsByRestaurant(UUID clientId, UUID restaurantId, Pageable pageable, Locale locale) {
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ModelNotFoundException(messageSource.getMessage(EXCEPTION_MODEL_NOTFOUND, null, locale)));

        return repository.findAll(byRestaurant(restaurant).and(byClient(client)), pageable).map(this::convert);
    }

    private Specification<ReviewEntity> byRestaurant(RestaurantEntity restaurant) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("restaurant"), restaurant);
    }

    private Specification<ReviewEntity> byClient(ClientEntity client) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("client"), client);
    }
}
