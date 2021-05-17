package wooteco.subway.exception;

public class NotRemoveSectionException extends RuntimeException {

    private static final String MESSAGE = "구간을 삭제할 수 없습니다.";

    public NotRemoveSectionException() {
        super(MESSAGE);
    }

}
