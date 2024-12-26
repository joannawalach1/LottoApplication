package com.juniorjavaready.domain.resultannouncer;

import com.juniorjavaready.domain.resultannouncer.dto.ResponseDto;
import com.juniorjavaready.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.juniorjavaready.domain.resultchecker.ResultCheckerFacade;
import com.juniorjavaready.domain.resultchecker.dto.ResultDto;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.util.Optional;

@RequiredArgsConstructor
public class ResultAnnouncerFacade {
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResponseRepository responseRepository;
    private final Clock clock;

    public ResultAnnouncerResponseDto checkResults(String hash) {
        if (responseRepository.existsById(hash)) {
            Optional<ResultResponse> resultResponse = responseRepository.findByHash(hash)
                    .orElseThrow(() -> new ResultResponseNotFoundException("Result with id" + hash + "not found"));
            return new ResultAnnouncerResponseDto(ResultMapper.mapToDto(resultResponse), ALREADY_CHECKED.info);
        }
        ResultDto resultDto = resultCheckerFacade.findByHash(hash);

        if (resultDto == null) {
            return new ResultAnnouncerResponseDto(null, HASH_DOES_NOT_EXISTS_MESSAGE.info);
        }
        ResponseDto responseDto = buildResponseDto(resultDto);
        responseRepository.save(buildResponse(responseDto));
        if ( responseRepository.existsById(hash) && !isAfterResultAnnouncementTime(resultDto)) {
            return new ResultAnnouncerResponseDto(responseDto, WAIT_MESSAGE.info);
        }
        if (resultCheckerFacade.findByHash(hash).isWinner()) {
            return new ResultAnnouncerResponseDto(responseDto, WIN_MESSAGE.info);
        }
        return new ResultAnnouncerResponseDto(responseDto, LOOSE_MESSAGE.info);

    }

    private static ResultResponse buildResponse(ResponseDto responseDto) {

    }

    private static ResponseDto buildResponseDto(ResultDto resultDto) {

    }

    private boolean isAfterResultAnnouncementTime(ResultDto resultDto) {

    }
}
