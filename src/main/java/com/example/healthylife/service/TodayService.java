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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodayService {

    private final TodayRepository todayRepository;
    private final UserRepository userRepository;
    private final TodayCommentsRepository todayCommentsRepository;
    @Autowired
    private final S3Service s3Service;


    //전체리스트
    public List<TodayEntity> todayList() {
       return todayRepository.findAll();
    }

    public List<TodayEntity> findMyTodayContents(String userId) {
        return todayRepository.findByUserUserId(userId);
    }

    public Optional<TodayEntity> findbytodaysq(long todaysq) { return todayRepository.findByTodaySq(todaysq);}

    public TodayEntity registerToday(TodayEntity todayEntity) {
        return todayRepository.save(todayEntity);
    }

    @Transactional
    public TodayEntity updateEntity(Long todayId, TodayEntity updatedTodayEntity, String username) {
        UserEntity user = userRepository.findByUserId(username)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        TodayEntity existingTodayEntity = todayRepository.findById(todayId)
                .orElseThrow(() -> new RuntimeException("오운완 글이 없습니다."));
        // 권한 체크: 현재 로그인한 사용자가 작성자와 동일한지 확인
        if (!existingTodayEntity.getUser().getUserId().equals(username)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        // 오운완 글의 내용 업데이트
        if (updatedTodayEntity.getTodayContents() != null) {
            existingTodayEntity.setTodayContents(updatedTodayEntity.getTodayContents());
        }
        if (updatedTodayEntity.getTodayContents() != null) {
            existingTodayEntity.setTodayContents(updatedTodayEntity.getTodayContents());
        }
        // 필요한 필드만 업데이트 추가 가능

        return todayRepository.save(existingTodayEntity);
    }

    public void deleteByTodaySq(long todaySq) {
todayRepository.deleteById(todaySq);
    }

    //댓글 단일 조회
    public Optional<TodayEntity> findTodayBySq(Long todaysq) {
        Optional<TodayEntity> today = todayRepository.findByTodaySq(todaysq);
        today.ifPresent(c -> {
            List<TodayCommentsEntity> comments = todayCommentsRepository.findByTodayEntity_todaySq(todaysq);
            c.setComments(comments);
        });
        return today;
    }

    public TodayEntity createTodayPost(String content, MultipartFile file, UserEntity user) {
        String imageUrl = s3Service.uploadFileToS3(file);

        TodayEntity todayEntity = TodayEntity.builder()
                .todayContents(content)
                .todayHearts(0)
                .todayCreated(new Date())
                .user(user)
                .imageurl(imageUrl)
                .build();

        return todayRepository.save(todayEntity);
    }

}
