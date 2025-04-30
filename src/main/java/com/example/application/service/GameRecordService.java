package com.example.application.service;

import com.example.application.model.entity.GameRecord;
import com.example.application.repository.GameRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRecordService {

    private final GameRecordRepository repository;

    public GameRecordService(GameRecordRepository repository) {
        this.repository = repository;
    }

    public GameRecord saveRecord(String gameType, String resultData, long elapsedTime, String formattedTime) {
        GameRecord record = new GameRecord(gameType, resultData, elapsedTime, formattedTime);
        return repository.save(record);
    }

    public List<GameRecord> getAllRecords() {
        return repository.findAll();
    }
}
