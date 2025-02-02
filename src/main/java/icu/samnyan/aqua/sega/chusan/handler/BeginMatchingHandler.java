package icu.samnyan.aqua.sega.chusan.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import icu.samnyan.aqua.sega.general.BaseHandler;
import icu.samnyan.aqua.sega.chusan.model.response.data.MatchingMemberInfo;
import icu.samnyan.aqua.sega.chusan.model.response.data.MatchingWaitState;
import icu.samnyan.aqua.sega.util.jackson.StringMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author samnyan (privateamusement@protonmail.com)
 */
@Component("ChusanBeginMatchingHandler")
public class BeginMatchingHandler implements BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(BeginMatchingHandler.class);

    private final StringMapper mapper;

    @Autowired
    public BeginMatchingHandler(StringMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String handle(Map<String, ?> request) throws JsonProcessingException {
        MatchingMemberInfo matchingMemberInfo = mapper.convert(request.get("matchingMemberInfo"), MatchingMemberInfo.class);

        MatchingWaitState matchingWaitState = new MatchingWaitState();

        matchingWaitState.getMatchingMemberInfoList().add(matchingMemberInfo);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("roomId", 1);
        resultMap.put("matchingWaitState", matchingWaitState);

        String json = mapper.write(resultMap);
        logger.info("Response: " + json);
        return json;
    }

}
