package moe.haishin.engine.input;

import java.util.ArrayList;
import java.util.List;

public class InputManager {
    private final List<Input> inputs = new ArrayList<>();
    private final List<InputListener> listeners = new ArrayList<>();

    public void addInput(Input input) {
        this.inputs.add(input);
    }

    public void removeInput(Input input) {
        this.inputs.remove(input);
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public Input getKeyboard() {
        return inputs.get(0);
    }

    public void addInputListeners(InputListener listener) {
        this.listeners.add(listener);
    }

    public void removeInputListeners(InputListener listener) {
        this.listeners.remove(listener);
    }

    public List<InputListener> getInputListeners() {
        return listeners;
    }

    public List<InputListener> getListeners() {
        return listeners;
    }
}
