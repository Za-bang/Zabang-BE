package com.example.zabang_be;

import com.example.zabang_be.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
@RequiredArgsConstructor
public class GetFirstData implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;    // SQL 실행용

    @Override
    public void run(String[] args) throws Exception {
        // rooms 테이블 데이터 개수 확인
        Integer countRoom = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM rooms", Integer.class);
        
        // options 테이블 데이터 개수 확인
        Integer countOption = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM options", Integer.class);

        // 두 테이블에 데이터가 존재하면 값을 삽입하지 않음
        if(countRoom != null && countRoom > 0 && countOption != null && countOption > 0) {
            System.out.println("초기 데이터가 이미 존재합니다.");
            return;
        }
        
        // resource 폴더에 있는 data.sql 파일 불러오기
        var resource = new ClassPathResource("data.sql");
        String sql = Files.readString(resource.getFile().toPath());

        // ;를 기준으로 쿼리 실행
        for(String s : sql.split(";")) {
            String trimed = s.trim();

            if(!trimed.isEmpty()) {
                jdbcTemplate.execute(trimed);
            }
        }
        System.out.println("초기 데이터 삽입 끝");
    }
}
