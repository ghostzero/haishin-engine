package moe.haishin.engine.input;


public interface InputListener {
    public void connected(Input input);

    public void disconnected(Input input);

    public void buttonDown(Input input, ButtonCode buttonCode);

    public void buttonUp(Input input, ButtonCode buttonCode);
}
