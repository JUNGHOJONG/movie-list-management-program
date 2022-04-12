package com.davincij.movie.Service;

import com.davincij.movie.dto.ResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class MovieService {

    private final static String SECRET_KEY = "3edbf9a3e0626f950d8b0634c7f05b20";
    private final static String DAILY_BOX_OFFICEL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private final static String TARGET_DATE = "20220407";

    public ResultDTO searchDailyBoxOfficeList() {
        try {
            URL url = new URL(getUrl(SECRET_KEY, DAILY_BOX_OFFICEL, TARGET_DATE));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            BufferedReader br;

            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine = getInputLine(br); // string json

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(inputLine, ResultDTO.class); // json to DTO
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("오류");
        }
    }

    private String getUrl(String secretKey, String dailyBoxOfficel, String keys) {
        StringBuilder sb = new StringBuilder();
        return sb.append(dailyBoxOfficel).append("?").append("key=").append(secretKey).append("&").append("targetDt=").append(keys).toString();
    }

    private String getInputLine(BufferedReader br) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine.trim());
        }

        return response.toString().replaceAll("\\s", "");
    }
}
