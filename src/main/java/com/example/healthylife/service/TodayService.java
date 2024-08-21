package com.example.healthylife.service;

import com.example.healthylife.entity.*;
import com.example.healthylife.repository.TodayCommentsRepository;
import com.example.healthylife.repository.TodayRepository;
import com.example.healthylife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodayService {

    private final TodayRepository todayRepository;
    private final UserRepository userRepository;
    private final TodayCommentsRepository todayCommentsRepository;
    private final S3Service s3Service;


    //전체리스트
    public List<TodayEntity> todayList() {
       return todayRepository.findAll();
    }

    public List<TodayEntity> findMyTodayContents(String userId) {
        return todayRepository.findByUserUserId(userId);
    }

    public Optional<TodayEntity> findbytodaysq(long todaysq) { return todayRepository.findByTodaySq(todaysq);}

    // 오운완 글 삭제
    public void deleteByTodaySq(long todaySq) {
todayRepository.deleteById(todaySq);
    }

    // 오운완 글 작성
    @Transactional
    public TodayEntity createTodayPost(String content, MultipartFile file, Date created, UserEntity user) {
        String imageUrl = s3Service.uploadFileToS3(file);

        TodayEntity todayEntity = TodayEntity.builder()
                .todayContents(content)
                .todayHearts(0)
                .todayCreated(created)
                .user(user)
                .imageurl(imageUrl)
                .build();

        return todayRepository.save(todayEntity);
    }

    // 오운완 글 수정
    @Transactional
    public TodayEntity updateEntity(Long todayId, String content, MultipartFile file, Date updatedDate, UserEntity user) {
        UserEntity existingUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        TodayEntity existingTodayEntity = todayRepository.findById(todayId)
                .orElseThrow(() -> new RuntimeException("오운완 글이 없습니다."));
        if (!existingTodayEntity.getUser().getUserId().equals(existingUser.getUserId())) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        if (content != null && !content.isEmpty()) {
            existingTodayEntity.setTodayContents(content);
        }
        if (updatedDate != null) {
            existingTodayEntity.setTodayCreated(updatedDate);
        }
        // 파일이 있는 경우 처리
        if (file != null && !file.isEmpty()) {
            // 기존 이미지 URL을 가져와서 S3에서 삭제
            String existingImageUrl = existingTodayEntity.getImageurl();
            if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
                String existingFileName = existingImageUrl.substring(existingImageUrl.lastIndexOf('/') + 1);
                s3Service.deleteFileFromS3(existingFileName);
            }

            // 새로운 이미지 업로드
            String newImageUrl = s3Service.uploadFileToS3(file);
            existingTodayEntity.setImageurl(newImageUrl);
        }

        // 게시글 저장
        return todayRepository.save(existingTodayEntity);
    }





}
