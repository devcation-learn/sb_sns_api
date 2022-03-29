package com.devcation.sns.controller;

import com.devcation.sns.data.ResponseDTO;
import com.devcation.sns.data.SnsDTO;
import com.devcation.sns.model.SnsEntity;
import com.devcation.sns.service.SnsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("sns")
public class SnsController {

    @Autowired //Autowired라는 어노테이션을 이용해서 의존성을 주입
    private SnsService service;

    @GetMapping("/sample")
    public ResponseEntity<?> sampleSns() {

        String str = service.sampleService();

        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().list(list).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/sample2")
    public ResponseEntity<?> sampleSns2() {
        String str = service.sampleService2(); // 테스트 서비스 사용
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().list(list).build();

        return ResponseEntity.ok().body(response);
    }


    @PostMapping
    public ResponseEntity<?> addSns(@RequestBody SnsDTO dto) {
        try{
            //DTO -> Entity
            SnsEntity snsEntity = SnsDTO.toSnsEntity(dto);
            snsEntity.setSid(null);
            List<SnsEntity> snsEntities = service.addSns(snsEntity);

            //Entity -> DTO
            List<SnsDTO> list = snsEntities.stream().map(SnsDTO::new).collect(Collectors.toList());
            ResponseDTO<SnsDTO> response = ResponseDTO.<SnsDTO>builder()
                    .resultCode("0000")
                    .resultMsg("")
                    .list(list)
                    .build();

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
//

            ResponseDTO<SnsDTO> response = ResponseDTO.<SnsDTO>builder()
                                                    .resultMsg(e.getMessage())
                                                    .resultCode("1111")
                                                    .build();

            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping()
    public ResponseEntity<?> listSns() {

        List<SnsEntity> snsEntities = service.listSns();

        List<SnsDTO> list = snsEntities.stream().map(SnsDTO::new).collect(Collectors.toList());
        ResponseDTO<SnsDTO> response = ResponseDTO.<SnsDTO>builder()
                .resultCode("0000")
                .resultMsg("")
                .list(list)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @PatchMapping()
    public ResponseEntity<?> updateSns(@RequestBody SnsDTO dto) {

            //DTO -> Entity
        SnsEntity snsNewEntity = SnsDTO.toSnsEntity(dto);
        List<SnsEntity> snsEntities = service.updateSns(snsNewEntity);

        List<SnsDTO> list = snsEntities.stream().map(SnsDTO::new).collect(Collectors.toList());
        ResponseDTO<SnsDTO> response = ResponseDTO.<SnsDTO>builder()
                .resultCode("0000")
                .resultMsg("")
                .list(list)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteSns(@RequestBody SnsDTO dto) {

        //DTO -> Entity
        SnsEntity snsNewEntity = SnsDTO.toSnsEntity(dto);
        List<SnsEntity> snsEntities = service.deleteSns(snsNewEntity);

        //Entity -> DTO
        List<SnsDTO> list = snsEntities.stream().map(SnsDTO::new).collect(Collectors.toList());
        ResponseDTO<SnsDTO> response = ResponseDTO.<SnsDTO>builder()
                .resultCode("0000")
                .resultMsg("")
                .list(list)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}