package com.davincij.movie.controller;

import com.davincij.movie.Service.MovieService;
import com.davincij.movie.dto.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "영화목록관리")
@AllArgsConstructor
@RequestMapping("/movie")
@RestController
public class MovieController {

    private MovieService movieService;

    @ApiOperation(value = "일별 박스오피스 조회", httpMethod = "GET", notes = "4월 7일, 일별 박스오피스 조회 내역")
    @GetMapping("/movies")
    public ResultDTO searchDailyBoxOfficeList() {
        return movieService.searchDailyBoxOfficeList();
    }
}
