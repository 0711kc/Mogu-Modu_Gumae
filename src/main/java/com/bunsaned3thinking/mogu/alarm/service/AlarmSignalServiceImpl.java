package com.bunsaned3thinking.mogu.alarm.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bunsaned3thinking.mogu.alarm.controller.dto.request.FcmMessage;
import com.bunsaned3thinking.mogu.alarm.controller.dto.request.FcmMessageRequest;
import com.bunsaned3thinking.mogu.alarm.controller.dto.response.AlarmSignalResponse;
import com.bunsaned3thinking.mogu.alarm.entity.AlarmSignal;
import com.bunsaned3thinking.mogu.alarm.repository.component.AlarmSignalComponentRepository;
import com.bunsaned3thinking.mogu.ask.entity.Ask;
import com.bunsaned3thinking.mogu.ask.entity.AskState;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmSignalServiceImpl implements AlarmSignalService {
	private final AlarmSignalComponentRepository alarmSignalComponentRepository;

	@Value("${firebase.path}")
	private String firebasePath;

	@Override
	public ResponseEntity<AlarmSignalResponse> createAlarmSignal(String userId, Long postId) {
		User user = alarmSignalComponentRepository.findUserById(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		Ask ask = alarmSignalComponentRepository.findAskByUserUidAndPostId(user.getUid(), postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 신청 정보를 찾을 수 없습니다."));
		AskState askState = ask.getState();
		User toUser = switch (askState) {
			case WAITING -> ask.getPost().getUser();
			case REJECTED, APPROVED -> ask.getUser();
		};
		AlarmSignal alarmSignal = AlarmSignal.of(ask, askState.getAlarmMessage(), toUser);
		AlarmSignal savedAlarmSignal = alarmSignalComponentRepository.saveAlarmSignal(alarmSignal);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(AlarmSignalResponse.from(savedAlarmSignal));
	}

	@Override
	public ResponseEntity<Void> deleteAlarmSignal(Long id) {
		AlarmSignal alarmSignal = alarmSignalComponentRepository.findAlarmSignalById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 알림을 찾을 수 없습니다."));
		alarmSignalComponentRepository.deleteAlarmSignalById(alarmSignal.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<List<AlarmSignalResponse>> findAlarmSignal(String userId) {
		User user = alarmSignalComponentRepository.findUserById(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		List<AlarmSignal> alarmSignals = alarmSignalComponentRepository.findAlarmSignalByUserUid(user.getUid());
		List<AlarmSignalResponse> alarmSignalResponses = alarmSignals.stream()
			.map(AlarmSignalResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(alarmSignalResponses);
	}

	@Override
	public ResponseEntity<Boolean> sendMessageTo(FcmMessage fcmMessage) {
		String message;
		try {
			message = makeMessage(fcmMessage);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("[Error] 알림 생성에 실패하였습니다.");
		}
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters()
			.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			headers.set("Authorization", "Bearer " + getAccessToken());
		} catch (IOException e) {
			throw new IllegalStateException("[Error] FCM 토큰 생성에 실패하였습니다.");
		}

		HttpEntity entity = new HttpEntity<>(message, headers);

		String API_URL = "<https://fcm.googleapis.com/v1/projects/mogu-ed5c6/messages:send>";
		ResponseEntity response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
		return ResponseEntity.ok(response.getStatusCode() == HttpStatus.OK);
	}

	private String getAccessToken() throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials
			.fromStream(new ClassPathResource(firebasePath).getInputStream())
			.createScoped(List.of("<https://www.googleapis.com/auth/cloud-platform>"));
		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}

	// FcmResponse -> Json 메서드
	private String makeMessage(FcmMessage fcmMessage) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		FcmMessageRequest fcmMessageRequest = FcmMessageRequest.builder()
			.message(FcmMessageRequest.Message.builder()
				.token(fcmMessage.getToken())
				.notification(FcmMessageRequest.Notification.builder()
					.title(fcmMessage.getTitle())
					.body(fcmMessage.getBody())
					.image(null)
					.build())
				.build())
			.validateOnly(false).build();
		return mapper.writeValueAsString(fcmMessageRequest);
	}
}
