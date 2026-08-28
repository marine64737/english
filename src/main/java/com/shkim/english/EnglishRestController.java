package com.shkim.english;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "https://kshsvr.com/")
@Slf4j
@RestController
public class EnglishRestController {
    @Autowired
    EnglishRepository englishRepository;

    @GetMapping("/api/all")
    List<english> callAll(){
        return englishRepository.findAll();
    }

    @GetMapping("/api/all/shuffled")
    ResponseEntity<?> callShuffledAll(){
        List<english> englishList;
        if (englishRepository.loopWordsNum() >= 90){
            englishList = englishRepository.findLoopShuffled();
        }
        else {
            englishList = englishRepository.findShuffled();
            englishList.forEach(english -> english.setLoop(true));
        }
        englishRepository.saveAll(englishList);
        if (englishRepository.wordsNum() == englishRepository.ankiWordsNum()) englishRepository.ankiInit();
        return ResponseEntity.ok().body(new APIResponse<>(true, "success", englishList));
    }
//    @GetMapping("/api/passnum")
//    ResponseEntity<?> passNum(){
//        return ResponseEntity.ok().body(new APIResponse<>(true, "success", englishRepository.passedWordsNum()));
//    }
    @GetMapping("/api/ankinum")
    ResponseEntity<?> ankiNum(){
        return ResponseEntity.ok().body(new APIResponse<>(true, "success", englishRepository.ankiWordsNum()));
    }
//    @PostMapping("/api/check")
//    ResponseEntity<?> checkWord(@RequestBody english english){
//        boolean isDuplicate;
//
//        isDuplicate = englishRepository.existsByKanjiContaining(english.getEnglish());
//
//        if (isDuplicate) {
//            return ResponseEntity.badRequest().body(new APIResponse<>(false, "이미 등록된 단어입니다.", null));
//        }
//
//        return ResponseEntity.ok().body(new APIResponse<>(false, "등록되지 않은 단어입니다.", null));
//    }

//    @Transactional
//    @PostMapping("/api/save")
//    ResponseEntity<?> saveWord(@RequestBody english english){
//
//        boolean isDuplicate;
//
//        if (english.getKanji() == null || english.getKanji().isEmpty()) {
//            // 한자가 없는 경우 읽기만 체크
//            isDuplicate = englishRepository.existsByKanjiIsNullAndReading(english.getReading());
//        } else {
//            // 한자와 읽기 세트가 있는지 체크
//            isDuplicate = englishRepository.existsByKanjiAndReading(english.getKanji(), english.getReading());
//        }
//
//        if (isDuplicate) {
//            return ResponseEntity.badRequest().body(new APIResponse<>(false, "이미 존재하는 단어입니다.", null));
//        }
//
//        englishRepository.save(english);
//        return ResponseEntity.ok().body(new APIResponse<>(true, "저장 성공", english));
//    }

    @GetMapping("/api/total")
    ResponseEntity<?> total(){
        return ResponseEntity.ok().body(new APIResponse<>(true, "조회 성공", englishRepository.count()));
    }

//    @PostMapping("/api/search")
//    public ResponseEntity<?> search(@RequestBody Map<String, String> payload) {
//        String kanji = payload.get("kanji"); // JSON에서 "kanji" 키의 값만 추출
//        List<english> englishes = englishRepository.findByKanjiContaining(kanji);
//
//        // 리스트가 null이거나 비어있는지 확인
//        if (englishes == null || englishes.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(false, "해당하는 단어가 없습니다.", null));
//        }
//        return ResponseEntity.ok().body(new APIResponse<>(true, "조회 성공", englishes));
//    }

    @Transactional
    @PostMapping("/api/update")
    ResponseEntity<?> update(@RequestBody english english){
        englishRepository.save(english);
        return ResponseEntity.ok().body(new APIResponse<>(true, "수정 성공", english));
    }

    @PostMapping("/api/anki")
    ResponseEntity<?> anki(@RequestBody int id){
        english english = englishRepository.findById(id).orElseThrow();
        english.setAnki(true);
        english.setLoop(false);
        englishRepository.save(english);
        return ResponseEntity.ok().body(new APIResponse<>(true, "암기 성공", english));
    }
    @Transactional
    @PostMapping("/api/init")
    ResponseEntity<?> ankiInit(){
        englishRepository.ankiInit();
        return ResponseEntity.ok().body(new APIResponse<>(true, "암기 초기화 완료", true));
    }
    @Transactional
    @PostMapping("/api/difficult")
    ResponseEntity<?> difficult(@RequestBody int id){
        english english = englishRepository.findById(id).orElseThrow();
        english.setDifficulty(english.getDifficulty()+1);
        english.setLoop(false);
        englishRepository.save(english);
        return ResponseEntity.ok().body(new APIResponse<>(true, "어려움", english));
    }
}
