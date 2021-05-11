package wooteco.subway.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.subway.domain.line.Line;
import wooteco.subway.repository.LineRepository;
import wooteco.subway.service.dto.LineDto;
import wooteco.subway.service.dto.SectionDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LineServiceTest {

    @InjectMocks
    private LineService lineService;

    @Mock
    private LineRepository lineRepository;

    @Mock
    private SectionService sectionService;

    @DisplayName("노선을 생성한다.")
    @Test
    void createLine() {
        String name = "testLine";
        String color = "black";
        long upStationId = 1;
        long downStationId = 2;
        int distance = 10;
        long lineId = 1L;
        Line line = new Line(name, color);
        Line retrievedLine = new Line(lineId, name, color);
        SectionDto sectionDto = SectionDto.builder()
                .upStationId(upStationId)
                .downStationId(downStationId)
                .distance(distance)
                .build();

        given(lineRepository.save(line)).willReturn(lineId);
        given(sectionService.createSection(sectionDto, lineId)).willReturn(1L);
        given(lineRepository.findById(lineId)).willReturn(retrievedLine);

        LineDto lineDto = LineDto.builder()
                .name(name)
                .color(color)
                .build();
        Line savedLine = lineService.createLine(lineDto, sectionDto);

        assertThat(savedLine).isEqualTo(retrievedLine);
        verify(lineRepository, times(1)).save(line);
        verify(lineRepository, times(1)).findById(lineId);
        verify(sectionService, times(1)).createSection(sectionDto, lineId);
    }

    @DisplayName("노선 조회에 성공한다.")
    @Test
    void findById() {
        long id = 1;
        Line line = new Line("testLine", "black");
        given(lineRepository.findById(id)).willReturn(line);

        Line retrievedLine = lineService.findById(id);

        assertThat(line).isEqualTo(retrievedLine);
        verify(lineRepository, times(1)).findById(id);
    }

    @DisplayName("노선의 이름을 수정한다.")
    @Test
    void editLine() {
        String name = "changedName";
        String color = "black";
        LineDto lineDto = new LineDto(name, color);
        Line line = new Line(1L, name, color);

        lineService.editLine(1L, lineDto);

        verify(lineRepository, times(1)).update(line);
    }
}
