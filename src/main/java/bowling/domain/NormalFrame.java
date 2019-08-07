package bowling.domain;

import bowling.domain.state.InitState;

import java.util.Objects;

/**
 * author       : gwonbyeong-yun <sksggg123>
 * ------------------------------------------
 * | email        : sksggg123               |
 * | github       : github.com/sksggg123    |
 * | blog         : sksggg123.github.io     |
 * ------------------------------------------
 * project      : java-bowling
 * create date  : 2019-07-17 00:53
 */
public class NormalFrame extends Frame {
    private Frame nextFrame;

    public NormalFrame() {
        this.frameNumber = FrameNumber.init();
        this.state = InitState.of();
    }

    public NormalFrame(int frameNumber) {
        this.state = InitState.of();
        this.frameNumber = new FrameNumber(frameNumber);
    }

    @Override
    public Frame bowl(int fallCount) {
        if (isGameOver()) {
            FinalFrame finalFrame = new FinalFrame(frameNumber.next());
            nextFrame = finalFrame;
            return finalFrame.bowl(fallCount);
        }
        if (state.isOver(false)) {
            return nextFrame(fallCount);
        }
        state = state.update(Point.of(fallCount), false);
        return this;
    }

    @Override
    public boolean isGameOver() {
        return frameNumber.isNormalFrameOver() && state.isOver(false);
    }

    @Override
    public Score getScore() {
        return updateScore(state.stateScore());
    }

    @Override
    public Score updateScore(Score source) {
        if (!isCalculable(source)) {
            return source;
        }
        return nextFrame.updateScore(nextFrame.getState().updateScore(source));
    }

    private boolean isCalculable(Score source) {
        if (source.remainCalculate() && nextFrame != null) {
            return true;
        }
        return false;
    }

    private NormalFrame nextFrame(int fallCount) {
        NormalFrame newFrame = new NormalFrame(frameNumber.next());
        nextFrame = newFrame;
        newFrame.bowl(fallCount);

        return newFrame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalFrame that = (NormalFrame) o;
        return Objects.equals(state, that.state) &&
                Objects.equals(frameNumber, that.frameNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, frameNumber);
    }

    @Override
    public String toString() {
        return "NormalFrame{" +
                "state=" + state +
                ", frameNumber=" + frameNumber +
                '}';
    }
}