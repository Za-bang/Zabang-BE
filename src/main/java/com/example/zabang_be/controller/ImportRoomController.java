package com.example.zabang_be.controller;

import com.example.zabang_be.dto.RoomResponseDto;
import com.example.zabang_be.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/import")
public class ImportRoomController {
    private final RoomService roomService;

    //rooms.json 호출
    @PostMapping("/rooms")
    public ResponseEntity<?> importDefault() {
        try {
            List<RoomResponseDto> result = roomService.importFromDefaultJson();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "message", "rooms.json import 실패",
                    "detail", e.getMessage()
            ));
        }
    }

    // 파일명 호출
    @PostMapping("/rooms/{file}")
    public ResponseEntity<?> importByFile(@PathVariable String file) {
        try {
            String filename = file.endsWith(".js") ? file : file + ".js";
            List<RoomResponseDto> result = roomService.importFromJson(filename);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "message", "JSON import 실패",
                    "detail", e.getMessage()
            ));
        }
    }
}

