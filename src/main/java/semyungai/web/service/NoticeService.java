package semyungai.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import semyungai.web.repository.NoticeRepository;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
}